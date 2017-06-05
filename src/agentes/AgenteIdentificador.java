package agentes;

import behaviour.BuscarAgentes;
import behaviour.LeerMensajeTelegram;
import behaviour.Saludo;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class AgenteIdentificador extends Agent {

	private static final long serialVersionUID = 1L;

	protected void setup() {
		
		//El agente saluda para decir que ya esta activo
		addBehaviour(new Saludo());
		
		//Se añade el comportamiento para busque agentes
		BuscarAgentes buscarAgentes = new BuscarAgentes(this,100);
		addBehaviour(buscarAgentes);
		
		addBehaviour(new LeerMensajeTelegram());
			  	
	}

	protected void takeDown() { // liberación de recursos del agente 
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
