package agentes;

import behaviour.BuscarAgentes;
import behaviour.DescubrirTipoUsusuario;
import behaviour.LeerMensajeTelegram;
import behaviour.Saludo;

import jade.core.Agent;

import java.util.Iterator;

import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgenteIdentificador extends Agent {
	
	protected void setup() {
		
		//El agente saluda para decir que ya esta activo
		addBehaviour(new Saludo());
		
		//Se añade el comportamiento para busque agentes
		BuscarAgentes buscarAgentes = new BuscarAgentes(this,10);
		addBehaviour(buscarAgentes);
		
		//Se añade el comportamiento para que este buscando si hay mensaje en el cola de telegram
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
