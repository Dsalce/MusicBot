package behaviour;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Serializable;
import model.Message;
import model.SerializableObject;
import model.User;
import utiles.Lexico;
import utiles.LlamarReglas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;

import bussines.Manager;
import control.Telegram;
import control.Main;
import utiles.StanfordNPL;

public class LeerMensajeTelegram extends CyclicBehaviour{
	
	private static final long serialVersionUID = 1L;
	
	//Variable con la instancia telegram
	private Telegram tele = Main.telegram;
	
	//Variable para identificar si es usuario(1) o administrador(2)
	private int opcion = 0;  
	
	//Instancia para las reglas
	private LlamarReglas reglas = new LlamarReglas();
	
	//Variable para lista de palabras
	List<Lexico> listaPalabras;
	
	
	public void action() {
		//Se lee todos los mensajes recibidos por telegram
		SendMessage mensaje = tele.leerMensaje(); 
		
		//variable local para la lista de agentes
		List<AID> listaAgentes = new ArrayList<AID>();
		
		//Variable para los parametros del agente
		Object[] parametros = myAgent.getArguments();
				
		//Provisionala hasta que salcedo ponga lo suyo
		if(mensaje != null){
			opcion = analizarPorStandfor(mensaje.getText(),mensaje.getChatId());
		}
		
		//Dependiendo del tipo de mensaje se lo pasamos a un agente u otro
		switch(opcion){
			//Caso de ser usuario
			case 1:
				//Se cogen los agente disponibles que se encuentra dentro de los argumentos
				if(parametros != null){
					listaAgentes = (List<AID>)parametros[0];
				}
				
				//Se recorre para buscar el agente usuario
				for(int i=0; i<listaAgentes.size(); i++){
					AID agente = (AID)listaAgentes.get(i);
					if(agente.getLocalName().contains("Usuario")){
						//Enviar el mensaje al agente usuario
						ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
						msg.addReceiver(agente);
						String chatID = mensaje.getChatId();
						String texto = mensaje.getText();
						if((chatID != null) && (texto != null)){
							try {
								//Creamos objeto serializable 
								SerializableObject object = new SerializableObject(chatID,listaPalabras,texto);
								msg.setContentObject(object);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							myAgent.send(msg);
						}
						opcion = 0;
						
						break;
					}
				}
				break;
				
			//Caso de ser administrador
			case 2:
				//Enviar el mensaje al administrador
				//Se cogen los agente disponibles que se encuentra dentro de los argumentos
				if(parametros != null){
					listaAgentes = (List<AID>)parametros[0];
				}
				
				//Se recorre para buscar el agente administrador
				for(int i=0; i<listaAgentes.size(); i++){
					AID agente = (AID)listaAgentes.get(i);
					if(agente.getLocalName().contains("Administrador")){
						//Enviar el mensaje al agente usuario
						ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
						msg.addReceiver(agente);
						String chatID = mensaje.getChatId();
						String texto = mensaje.getText();
						if((chatID != null) && (texto != null)){
							try {
								//Creamos objeto serializable 
								SerializableObject object = new SerializableObject(chatID,listaPalabras,texto);
								msg.setContentObject(object);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							myAgent.send(msg);
						}
						opcion = 0;
						break;
					}
				}
				break;
				
		}
		
	}
	
	
	//Se encarga de gestionar el analisis del mensaje por standfor
	private int analizarPorStandfor(String mensaje, String chatId){
		int iterador = 0;
		Lexico palabra = null;
		String tag = "";
		User usuario = null;
		Manager myManager = new Manager();
		usuario = myManager.Users().FindById(Integer.parseInt(chatId));
		//Guarda y analiza la lisa de palabras analizadas
		listaPalabras = StanfordNPL.parser(mensaje);
		//Se recorre 
		for (iterador=0;iterador<listaPalabras.size();iterador++){
			//Obtener el objeto de la posicion iterador
			palabra = listaPalabras.get(iterador);
			
			//Se analiza la palabra mediante drools para intentar identificar su tag
			tag = analizarPorReglas(palabra);
			
			if(!tag.equals("")){
				//Se actualiza el tag de la palabra
				listaPalabras.get(iterador).getTag().add(tag);
			}
			
			if((palabra.getWord().equals("ADMINISTRADOR")) || (palabra.getWord().equals("ADMIN"))){
				//Introducimos al usuario en la base de datos
				if(usuario == null){
					//Se introduce al usuario en la bbdd
					usuario = new User(Integer.parseInt(chatId),"Saludo","",0,0,0,"",0,0);
					usuario.setAdmin(1);
					myManager.Users().Add(usuario);
				}
				//Se registra al cliente como un administrador
				usuario.setAdmin(1);
				myManager.Users().Update(Integer.parseInt(chatId),usuario);
				myManager.Dispose();
			}
				
		}

		//En caso de ser un saludo
		if(usuario != null){
			if(usuario.getAdmin() == 1){
				//Se pasa el mensaje al agente administrador para busque la respuesta al mensaje
				return 2;
			}else{
				//Si no encuentra ningun tag de aministrador entonces es un usuario estandar
				return 1;
			}
		}else{
			return 1;
		}
		
	}
	
	//Se encarga de gestionar el analisis del mensaje mediante las reglas
	private String analizarPorReglas(Lexico palabra){
		//Se devuelve el tag de la palabra analizada por drools
		return reglas.parseWord(palabra.getWord());
	}
}
