package Agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;



public class AgenteUsuario extends Agent {
	/*
	private static final long serialVersionUID = 1234561L;

	//Variable identificador agente
	private AID IdentificadorAgente;
	
	//Variable nombre del agente
	private String nombre;
	
	// Almacena la lista de los agentes conocidos que son vendedores de libro
	private AID[] agentesConocidos;  
	
	//Variable estado del agente
	private int estado;
	
	//Definicion de los diferentes estados
	private final int INICIADO = 0;
	private final int ACTIVO = 1;
	private final int SUSPENDIDO = 2;
	private final int ENESPERA = 3;
	private final int DESCONOCIDO = 3;
	private final int TRANSITO = 3;
	
	*/
	
	//Constructor
	public AgenteUsuario(){
		
		//setup();		
	}
	
	//Gestiona el inicio del agente
	protected void setup() {
		
		//Se crea el identificador
		AID prueba = getAID();
		boolean quepasa = AID.ISGUID;
		AID identificador = new AID(getAID().getName(),AID.ISGUID);
		
		/*
		
		/**************************
		 El agente debe suscribirse para que los demas agentes le vean 
		 y para que el pueda ver a los demas
		 **************************/
		//Se crea el facilitador de directorio para encontrar a los agentes de alrededor
		/*DFAgentDescription descripcion = new DFAgentDescription();
		descripcion.setName(getAID());
		
		//Se crea la descripcion del sercio del agente
		ServiceDescription servicio = new ServiceDescription();
		servicio.setType("Usuario");
		servicio.setName("JADE-Usuario");
		
		//Se añade el servicio al facilitador de directorio
		descripcion.addServices(servicio);
		
		//Se registra el agente
		try {
			DFService.register(this, descripcion);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
				
		
		DFAgentDescription descripcionAgente = new DFAgentDescription();
		descripcionAgente.setName(getAID());
		
		
		//Se  establece el estado del agente a iniciado
		estado = INICIADO;
		*/
	}

	// liberación de recursos del agente 
	protected void takeDown() { 
	}
	
	
	
}
