package behaviour;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utiles.LlamarReglas;
import control.Telegram;
import control.Main;


public class LeerMensajesInternos extends CyclicBehaviour{

	private static final long serialVersionUID = 1L;
	
	private Telegram tele = Main.telegram;
	
	private LlamarReglas reglas = new LlamarReglas();

	public void action() {
		//Se crea un template para poder consultar el tipo de mensaje que es y 
		//si es el que queremos leerlo en caso contrario no sale de la cola de mensajes
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null) {
			//Se ha recibido un mensaje de tipo informacion
			//Se analiza el contenido
			String mensaje = msg.getContent();
			
			//Se guarda como parametro del agente el mensaje recibido
			Object[] parametros = new Object[1];
			
			parametros[0] = mensaje;
			
			//Se introduce los argumentos al agente
			myAgent.setArguments(parametros);
			
			/*
			//Se pasan los argumentos separados por ,
			String[] arrayParametros = mensaje.split(",");
			
			//Se procede a enviar el mensaje de respuesta al usuario 
			//Posicion 0 esta el chatId que es el que nos interesa almacenar
			String chatID = arrayParametros[0];
		*/
			
		}
		else {
			block();
		}
		
	}
	

}
