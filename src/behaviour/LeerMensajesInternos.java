package behaviour;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.SerializableObject;
import behaviour.InsertarUsuarioBBDD;


public class LeerMensajesInternos extends CyclicBehaviour{

	private static final long serialVersionUID = 1L;
		

	public void action() {
		//Se crea un template para poder consultar el tipo de mensaje que es y 
		//si es el que queremos leerlo en caso contrario no sale de la cola de mensajes
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null) {
			//Se ha recibido un mensaje de tipo informacion
			//Se analiza el contenido
			SerializableObject mensaje = null;
			try {
				mensaje = (SerializableObject)msg.getContentObject();
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Se guarda como parametro del agente el mensaje recibido
			Object[] parametros = new Object[1];
			
			parametros[0] = mensaje;
			
			//Se introduce los argumentos al agente
			myAgent.setArguments(parametros);
			
			//Se  lanza el comportamiento para introducir al usuario en la BBDD
			InsertarUsuarioBBDD usuario = new InsertarUsuarioBBDD();
			myAgent.addBehaviour(usuario);
			
		}
		else {
			block();
		}
		
	}
	

}
