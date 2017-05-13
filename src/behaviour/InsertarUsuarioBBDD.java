package behaviour;

import bussines.Manager;
import jade.core.behaviours.OneShotBehaviour;
import model.User;

public class InsertarUsuarioBBDD extends OneShotBehaviour{
	
	static final long serialVersionUID = 1L;

	//Introducir usuario en la BBDD
	public void action() {
		//Gestiona la base de datos
		Manager myManager = new Manager();
		
		//Instancia la clase objeto para recoger los parametros del agente
		Object[] parametros;
		parametros = myAgent.getArguments();
		
		String mensaje = (String)parametros[0];
		
		//Se pasan los argumentos separados por ,
		String[] arrayParametros = mensaje.split(",");
		
		//Se procede a enviar el mensaje de respuesta al usuario 
		//Posicion 0 esta el chatId que es el que nos interesa almacenar
		int chatID = Integer.parseInt(arrayParametros[1]);
		
		//Comprobamos si el usuario esta registrado en la BBDD
		User usuario = new User(chatID,"","",false);
		myManager.Users().Add(usuario);
		
		//Se analiza el mensaje dependiendo del tipo de agente que esta analizando
		if(myAgent.getName().contains("Usuario")){
			
			//Se lanza el comportamiento analisis del mensaje del usuario
			AnalisisMensaje analisis = new AnalisisMensaje();
			myAgent.addBehaviour(analisis);
			
		}else if(myAgent.getLocalName().contains("Administrador")){
			
			//Se lanza el comportamiento analisis del mensaje del usuario
			AnalisisAdmin analisis = new AnalisisAdmin();
			myAgent.addBehaviour(analisis);
			
		}
	}
	
}
