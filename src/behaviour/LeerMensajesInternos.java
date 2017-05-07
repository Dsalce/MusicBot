package behaviour;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import control.Telegram;
import control.Main;


public class LeerMensajesInternos extends CyclicBehaviour{

	private static final long serialVersionUID = 1L;
	
	private Telegram tele = Main.telegram;

	public void action() {
		//Se crea un template para poder consultar el tipo de mensaje que es y 
		//si es el que queremos leerlo en caso contrario no sale de la cola de mensajes
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null) {
			//Se ha recibido un mensaje de tipo informacion
			//Se analiza el contenido
			String mensaje = msg.getContent();
			
			//Se pasan los argumentos separados por ;
			String[] arrayParametros = mensaje.split(",");
			
			//Se procede a enviar el mensaje de respuesta al usuario 
			//Posicion 0 esta el chatId que es el que nos interesa almacenar
			String chatID = arrayParametros[0];
			
			
			//Prueba para ver que funciona
			tele.enviarMensaje(arrayParametros[1],arrayParametros[0]);
			
			
			
/*
			Integer price = (Integer) catalogue.get(title);
			if (price != null) {
				// The requested book is available for sale. Reply with the price
				reply.setPerformative(ACLMessage.PROPOSE);
				reply.setContent(String.valueOf(price.intValue()));
			}
			else {
				// The requested book is NOT available for sale.
				reply.setPerformative(ACLMessage.REFUSE);
				reply.setContent("not-available");
			}
			//myAgent.send(reply);*/
		}
		else {
			block();
		}
		
	}
	

}
