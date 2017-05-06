package agentes;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgenteAdministrador extends Agent {
	protected void setup() {
		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("AgenteTelegram");
		sd.setName("AgenteAdministrador");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

	protected void takeDown() { // liberaci�n de recursos del agente 
		try {
			// Intenta darse de baja del resgitro 
			DFService.deregister(this); 
       }
		// Captura la excepci�n FIPA
       catch (FIPAException fe) {   
    	   // Dirige la excepci�n al terminal de salida
    	   fe.printStackTrace();       
       }
		// Se despide
		System.out.println("Agent Usuario "+getAID().getName()+" terminating."); 
	}
}
