package behaviour;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import model.User;
import utiles.Lexico;
import utiles.LlamarReglas;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;

import bussines.Manager;
import control.Telegram;
import control.Main;
import utiles.StanfordNPL;

import utiles.AnalizarMensaje;

public class LeerMensajeTelegram extends CyclicBehaviour{

	private Manager myManager = new Manager();
	
	private static final long serialVersionUID = 1L;
	
	//Variable con la instancia telegram
	private Telegram tele = Main.telegram;
	
	//Variable para identificar si es usuario(1) o administrador(2)
	private int opcion = 0;  
	
	//Instancia para Standfor
	private StanfordNPL standfor = new StanfordNPL();
	
	//Instancia para las reglas
	private LlamarReglas reglas = new LlamarReglas();
	
	//Variable para lista de agentes
	
	
	public void action() {
		//Se lee todos los mensajes recibidos por telegram
		SendMessage mensaje = tele.leerMensaje(); 
		
		//variable local para la lista de agentes
		Object[] listaAgentes = null;
		
		//Instancia para analizar mensajes
		AnalizarMensaje analizador = new AnalizarMensaje();
				
		//Provisionala hasta que salcedo ponga lo suyo
		if(mensaje != null){
			analizador.analizarPorStandfor(mensaje.getText());
			opcion = 1;
		}
		
		//Dependiendo del tipo de mensaje se lo pasamos a un agente u otro
		switch(opcion){
			//Caso de ser usuario
			case 1:
				//Se cogen los agente disponibles que se encuentra dentro de los argumentos
				listaAgentes = myAgent.getArguments();
				
				//Se recorre para buscar el agente usuario
				for(int i=0; i<listaAgentes.length; i++){
					AID agente = (AID)listaAgentes[i];
					if(agente.getLocalName().contains("Usuario")){
						//Enviar el mensaje al agente usuario
						ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
						msg.addReceiver(agente);
						String chatID = mensaje.getChatId();
						String texto = mensaje.getText();
						if((chatID != null) && (texto != null)){
							msg.setContent(chatID + "," + texto);
							myAgent.send(msg);
						}
						
						break;
					}
				}
				break;
				
			//Caso de ser administrador
			case 2:
				//Enviar el mensaje al administrador
				//Se cogen los agente disponibles que se encuentra dentro de los argumentos
				listaAgentes = myAgent.getArguments();
				
				//Se recorre para buscar el agente administrador
				for(int i=0; i<listaAgentes.length; i++){
					AID agente = (AID)listaAgentes[i];
					if(agente.getLocalName().contains("Administrador")){
						//Enviar el mensaje al agente usuario
						ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
						msg.addReceiver(agente);
						msg.setContent(mensaje.getChatId() + ";" + mensaje.getText());
						myAgent.send(msg);
					}
				}
				break;
				
		}
		
	}
}
