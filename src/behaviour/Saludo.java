package behaviour;

import jade.core.behaviours.OneShotBehaviour;

public class Saludo extends OneShotBehaviour{

	private static final long serialVersionUID = 1L;

	public void action() {
		System.out.println("Hola, soy el agente" + myAgent.getName());
	}

}
