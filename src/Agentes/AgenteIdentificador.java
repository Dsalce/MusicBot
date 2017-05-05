package Agentes;
import jade.core.Agent;

import java.util.Iterator;

import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgenteIdentificador extends Agent {
	
	private static final String tipoServicio = "AgenteTelegram";
	
	protected void setup() {
		// inicialización de MiAgente 
		try {
	  		// Define el tipo de servicio que se quiere buscar
	  		DFAgentDescription templateAD = new DFAgentDescription();
	  		ServiceDescription templateSD = new ServiceDescription();
	  		templateSD.setType(tipoServicio);	
	  		templateAD.addServices(templateSD);
	  		
	  		/*
	  		// Se pueden añadir restricciones a la búsqueda (max-depth, max-results, o search-id)
	  		SearchConstraints sc = new SearchConstraints();
	  		sc.setMaxResults(new Long(10));
	  		*/
	  		
	  		//Se almacenan los agentes encontrados
	  		DFAgentDescription[] resultados = DFService.search(this, templateAD);
	  		
	  		if (resultados.length > 0) {
	  			System.out.println("El agente "+getLocalName()+" encontró los siguientes agentes de tipo "+tipoServicio+":");
	  			for (int i = 0; i < resultados.length; ++i) {
	  				DFAgentDescription dfd = resultados[i];
	  				AID provider = dfd.getName();
	  				// Un agente puede ofrecer varios servicios, hay que buscar solo el tipoServicio especificado
	  				Iterator it = dfd.getAllServices();
	  				while (it.hasNext()) {
	  					ServiceDescription sd = (ServiceDescription) it.next();
	  					if (sd.getType().equals(tipoServicio)) {
	  						System.out.println(provider.getName());
	  					}
	  				}
	  			}
	  		}	
	  		else {
	  			System.out.println("El agente "+getLocalName()+" no encontró ningún agente "+tipoServicio+".");
	  		}
	  	}
	  	catch (FIPAException fe) {
	  		fe.printStackTrace();
	  	}
	}

	protected void takeDown() { // liberación de recursos del agente }
	}
}
