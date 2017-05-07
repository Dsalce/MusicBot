package agentes;

import behaviour.LeerMensajesInternos;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;



public class AgenteUsuario extends Agent {
	
	private static final long serialVersionUID = 1234561L;

	//Gestiona el inicio del agente
	protected void setup() {
		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("AgenteTelegram");
		sd.setName("AgenteUsuario");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		addBehaviour(new LeerMensajesInternos());
	}

	// liberación de recursos del agente 
	protected void takeDown() { 
		try {
			// Intenta darse de baja del resgitro 
			DFService.deregister(this); 
       }
		// Captura la excepción FIPA
       catch (FIPAException fe) {   
    	   // Dirige la excepción al terminal de salida
    	   fe.printStackTrace();       
       }
		// Se despide
		System.out.println("Agent Usuario "+getAID().getName()+" terminating."); 
	}
	
	
	
}
