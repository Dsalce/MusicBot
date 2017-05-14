package behaviour;

import java.util.ArrayList;
import java.util.List;

import bussines.Manager;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import model.Enumerations.EtypeMessage;
import model.Message;
import model.TypeMessage;
import model.User;
import utiles.EnviarMensajeTelegram;
import utiles.Lexico;
import utiles.LlamarReglas;
import utiles.StanfordNPL;

public class AnalisisMensaje extends OneShotBehaviour{

	private static final long serialVersionUID = 1L;
	
	//Instancia para Standfor
	private StanfordNPL standfor = new StanfordNPL();
	
	//Instancia para las reglas
	private LlamarReglas reglas = new LlamarReglas();
	
	//Variable para la BBDD
	private Manager myManager = new Manager();
	
	//Insatancia del comportamiento para enviar el mensaje de respuesta al usuario
	private EnviarMensajeTelegram mensajeRespuesta;
	
	//Se almacenan los parametros del agente
	Object[] parametros;
	
	//Introducir usuario en la BBDD
	public void action() {
		//Se coge el mensaje del usuario de los argumentos del agente
		//Instancia la clase objeto para recoger los parametros del agente
		parametros = myAgent.getArguments();
		
		String mensaje = (String)parametros[0];
		
		//Se pasan los argumentos separados por ,
		String[] arrayParametros = mensaje.split(",");
		
		analizarPorStandfor(arrayParametros[0],arrayParametros[1]);
	}
	
	//Se encarga de gestionar el analisis del mensaje por standfor
	private void analizarPorStandfor(String mensaje, String chatId){
		int iterador = 0;
		Lexico palabra = null;
		String tag = "";
		//Guarda y analiza la lisa de palabras analizadas
		List<Lexico> listaPalabras = standfor.parser(mensaje);
		//Se recorre 
		for (iterador=0;iterador<listaPalabras.size();iterador++){
			//Obtener el objeto de la posicion iterador
			palabra = listaPalabras.get(iterador);
			tag = palabra.getTag();
			if(tag == ""){
				//Se analiza la palabra mediante drools para intentar identificar su tag
				tag = analizarPorReglas(palabra);
				//Se actualiza el tag de la palabra con el resultado obtenido
				listaPalabras.get(iterador).setTipo(tag);
			}
			
			//En caso de ser un saludo
			if(tag == "Saludo"){
				//Se llama a la funcion encargada de saludar al usuario
				saludarUsuario(chatId);
			}
			
			//En caso de ser una despedida
			else if(tag == "Bye"){
				//Se llama a la funcion encargada de despedirse del usuario
				despedidaUsuario(chatId);
			}
		}
		
		//Se introducen en el agente la lista de palabras con su tag
		parametros[1] = listaPalabras;
		myAgent.setArguments(parametros);
		
		//Mantener conversacion con el usuario dependindo de su tipo (user o admin)
		String nombreAgente = myAgent.getAID().getName();
		if(nombreAgente == "Usuario"){
			//Analizamos la frase segun dependiendo del agente
		}else if (nombreAgente == "Admin"){
			//Analizamos la frase segun dependiendo del agente
		}
	}
	
	//Se encarga de gestionar el analisis del mensaje mediante las reglas
	private String analizarPorReglas(Lexico palabra){
		//Se devuelve el tag de la palabra analizada por drools
		return reglas.parseWord(palabra.getWord());
	}
	
	//Gestiona el envio del saludo al usuario 
	private void saludarUsuario(String chatId){
		//Se consulta en que estado estamos con el usuario
		User usuario = myManager.Users().FindById(Integer.parseInt(chatId));
		
		if(usuario.getState() ==  "Saludo"){
			//Se consulta el mensaje de saludo para el usuario
			TypeMessage saludos = myManager.TypeMessage().FindById(EtypeMessage.SALUDO.ordinal());
			List<Message> mensajesSaludos = saludos.getMessages();
			int random = 0 + (int)(Math.random() * ((mensajesSaludos.size() - 0) + 1));
			mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()), mensajesSaludos.get(random).getMessage());
		}else{
			
		}
	}
	
	//Gestion el envio de despedido al usuario
	private void despedidaUsuario(String chatId){
		//Se consulta en que estado estamos con el usuario
		User usuario = myManager.Users().FindById(Integer.parseInt(chatId));
		
		//Se consulta el mensaje de saludo para el usuario
		TypeMessage saludos = myManager.TypeMessage().FindById(EtypeMessage.DESPEDIDA.ordinal());
		List<Message> mensajesSaludos = saludos.getMessages();
		int random = 0 + (int)(Math.random() * ((mensajesSaludos.size() - 0) + 1));
		mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()), mensajesSaludos.get(random).getMessage());
	}

}
