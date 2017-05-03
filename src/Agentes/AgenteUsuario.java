package Agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;

public class AgenteUsuario extends Agent {
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
	
	
	
	//Constructor
	public AgenteUsuario(){
		//Se crea el identificador del agente
		
		setNombre("Agente1");
		//Se crea el identificador
		AID identificador = new AID(getNombre(),AID.ISGUID);
		//Se guarda el identificador
		setAID(identificador);
		//Se registra el agente en el AMS
		setup();		
	}
		
	//Devuelve el nombre del agente
	private String getNombre() {
		return nombre;
	}
	
	//Establece el nombre del agente
	private void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	//Gestiona el inicio del agente
	protected void setup() {
		// inicialización de MiAgente
		
		//Se registra el agente en el DF
		DFAgentDescription descripcionAgente = new DFAgentDescription();
		descripcionAgente.setName(getAID());
		
	}

	// liberación de recursos del agente 
	protected void takeDown() { 
	}
	
	//Establecer ID agente
	private void setAID(AID aID) {
		IdentificadorAgente = aID;
	}
	//Devuelve el identificador del agente
	private AID setAID(){
		return IdentificadorAgente;
	}
	
}
