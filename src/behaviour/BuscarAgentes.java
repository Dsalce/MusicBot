package behaviour;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import utiles.Lexico;

public class BuscarAgentes extends TickerBehaviour{
	
	private static final String tipoServicio = "AgenteTelegram";
	
	private List<AID> listaAgentes = new ArrayList<AID>();
	
	private Object[] parametros = new Object[2];

	public BuscarAgentes(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub
	}
	
	/******
	 * FALTAAAAAAAAAAAAAAAAAAAAAAAAAAA CAMBIAR EL TIPO DE BEHAVIOUR PARA QUE NO ESTE TODO EL RATO BUSCANDO
	 * 
	 * FALTAAAAAAAAAAAAAAAAAAAAAAAAAAA CAMBIAR EL TIPO DE BEHAVIOUR PARA QUE NO ESTE TODO EL RATO BUSCANDO
	 * 
	 * 
	 * FALTAAAAAAAAAAAAAAAAAAAAAAAAAAA CAMBIAR EL TIPO DE BEHAVIOUR PARA QUE NO ESTE TODO EL RATO BUSCANDO
	 * 
	 * FALTAAAAAAAAAAAAAAAAAAAAAAAAAAA CAMBIAR EL TIPO DE BEHAVIOUR PARA QUE NO ESTE TODO EL RATO BUSCANDO
	 * 
	 * 
	 * 
	 * FALTAAAAAAAAAAAAAAAAAAAAAAAAAAA CAMBIAR EL TIPO DE BEHAVIOUR PARA QUE NO ESTE TODO EL RATO BUSCANDO
	 * FALTAAAAAAAAAAAAAAAAAAAAAAAAAAA CAMBIAR EL TIPO DE BEHAVIOUR PARA QUE NO ESTE TODO EL RATO BUSCANDO
	 * 
	 * 
	 * 
	 * 
	 * FALTAAAAAAAAAAAAAAAAAAAAAAAAAAA CAMBIAR EL TIPO DE BEHAVIOUR PARA QUE NO ESTE TODO EL RATO BUSCANDO
	 * 
	 * 
	 * 
	 * 
	 * 
	 * FALTAAAAAAAAAAAAAAAAAAAAAAAAAAA CAMBIAR EL TIPO DE BEHAVIOUR PARA QUE NO ESTE TODO EL RATO BUSCANDO
	 * 
	 * 
	 * 
	 * FALTAAAAAAAAAAAAAAAAAAAAAAAAAAA CAMBIAR EL TIPO DE BEHAVIOUR PARA QUE NO ESTE TODO EL RATO BUSCANDO
	 * 
	 * 
	 * 
	 * 
	 * 
	 * FALTAAAAAAAAAAAAAAAAAAAAAAAAAAA CAMBIAR EL TIPO DE BEHAVIOUR PARA QUE NO ESTE TODO EL RATO BUSCANDO
	 * 
	 * 
	 * 
	 * 
	 * FALTAAAAAAAAAAAAAAAAAAAAAAAAAAA CAMBIAR EL TIPO DE BEHAVIOUR PARA QUE NO ESTE TODO EL RATO BUSCANDO
	 * 
	 * 
	 */

	
	

	protected void onTick() {
		try {
	  		// Define el tipo de servicio que se quiere buscar
	  		DFAgentDescription templateAD = new DFAgentDescription();
	  		ServiceDescription templateSD = new ServiceDescription();
	  		templateSD.setType(tipoServicio);	
	  		templateAD.addServices(templateSD);
	  		
	  		//Se almacenan los agentes encontrados
	  		DFAgentDescription[] resultados = DFService.search(myAgent, templateAD);
	  		
	  		//PROVISIONALLLLLL
				/*
				 * 
				 * 
				 * 
				 * 
				 */
			listaAgentes = new ArrayList<AID>();
			
			
	  		if (resultados.length > 0) {
	  			System.out.println("El agente "+myAgent.getLocalName()+" encontró los siguientes agentes de tipo "+tipoServicio+":");
	  			for (int i = 0; i < resultados.length; ++i) {
	  				DFAgentDescription dfd = resultados[i];
	  				AID provider = dfd.getName();
	  				listaAgentes.add(provider);
	  				// Un agente puede ofrecer varios servicios, hay que buscar solo el tipoServicio especificado
	  				Iterator it = dfd.getAllServices();
	  				while (it.hasNext()) {
	  					ServiceDescription sd = (ServiceDescription) it.next();
	  					if (sd.getType().equals(tipoServicio)) {
	  						System.out.println(provider.getName());
	  					}
	  				}
	  			}
	  			parametros[0]=listaAgentes;
	  			myAgent.setArguments(parametros);
	  		}	
	  		else {
	  			System.out.println("El agente "+myAgent.getLocalName()+" no encontró ningún agente "+tipoServicio+".");
	  		}
	  	}
	  	catch (FIPAException fe) {
	  		fe.printStackTrace();
	  	}
		
	}

}
