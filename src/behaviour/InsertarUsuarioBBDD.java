package behaviour;

import bussines.Manager;
import jade.core.behaviours.OneShotBehaviour;
import model.SerializableObject;
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
		
		SerializableObject mensaje = (SerializableObject)parametros[0];
		
		//Se procede a enviar el mensaje de respuesta al usuario 
		//Posicion 0 esta el chatId que es el que nos interesa almacenar
		int chatID = Integer.parseInt(mensaje.getChatId());
				
		User usuario_bbdd = myManager.Users().FindById(chatID);
		
		/**
		 * 
		 * 
		 * 
		 * 
		 * 
		 * actualizar usuario en la bbdd si existe
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		//Si existe el usuario no lo introducimos en la bbdd
		if(usuario_bbdd == null){
			//Se introduce al usuario en la bbdd
			User usuario = new User(chatID,"","",false);
			myManager.Users().Add(usuario);
		}
		
		//Se analiza el mensaje dependiendo del tipo de agente que esta analizando
		if(myAgent.getName().contains("Usuario")){
			
			//Se lanza el comportamiento analisis del mensaje del usuario
			AnalisisUsuario analisis = new AnalisisUsuario();
			myAgent.addBehaviour(analisis);
			
		}else if(myAgent.getLocalName().contains("Administrador")){
			
			//Se lanza el comportamiento analisis del mensaje del usuario
			AnalisisAdmin analisis = new AnalisisAdmin();
			myAgent.addBehaviour(analisis);
			
		}
	}
	
}
