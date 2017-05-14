package behaviour;

import java.util.List;

import bussines.Manager;
import jade.core.behaviours.OneShotBehaviour;
import model.Message;
import model.SerializableObject;
import model.TypeMessage;
import model.User;
import model.Enumerations.EtypeMessage;
import utiles.EnviarMensajeTelegram;
import utiles.Lexico;

public class AnalisisUsuario extends OneShotBehaviour{

	private static final long serialVersionUID = 1L;
	
	//Variable para la BBDD
	private Manager myManager = new Manager();
	
	//Insatancia del comportamiento para enviar el mensaje de respuesta al usuario
	private EnviarMensajeTelegram mensajeRespuesta = new EnviarMensajeTelegram();
	
	@Override
	public void action() {
		//Variable para los parametros del agente
		Object[] parametros = myAgent.getArguments();
		SerializableObject objetoSerializable = (SerializableObject)parametros[0];
		
		//En la posicion 0 esta el chatId
		String chatId = objetoSerializable.getChatId();
		//En la posicion 1 esta la lista de palabras
		List<Lexico> listaPalabras = objetoSerializable.getLista();
		
		int iterador = 0;
		Lexico palabra = null;
		String tag = "";
		
		//Se recorre 
		for (iterador=0;iterador<listaPalabras.size();iterador++){
			//Obtener el objeto de la posicion iterador
			palabra = listaPalabras.get(iterador);
			tag = palabra.getTipo();
			
			//En caso de ser un saludo
			if(tag.equals("Saludo")){
				//Se llama a la funcion encargada de saludar al usuario
				saludarUsuario(chatId);
			}
			
			//En caso de ser una despedida
			else if(tag.equals("Bye")){
				//Se llama a la funcion encargada de despedirse del usuario
				despedidaUsuario(chatId);
			}
		}
	}
	
	//Gestiona el envio del saludo al usuario 
	private void saludarUsuario(String chatId){
		//Se consulta en que estado estamos con el usuario
		User usuario = myManager.Users().FindById(Integer.parseInt(chatId));
		
		//if(usuario.getState() ==  "Saludo"){
			//Se consulta el mensaje de saludo para el usuario
			TypeMessage saludos = myManager.TypeMessage().FindById(EtypeMessage.SALUDO.ordinal());
			List<Message> mensajesSaludos = saludos.getMessages();
			int random = 0 + (int)(Math.random() * ((mensajesSaludos.size()-1 - 0) + 1));
			int tamano = mensajesSaludos.size();
			String chat = Integer.toString(usuario.getChatId());
			Message mensaje = mensajesSaludos.get(random);
			String cadena = mensaje.getMessage();
			mensajeRespuesta.enviar(chat, cadena);
		//}else{
			
		//}
	}
	
	//Gestion el envio de despedido al usuario
	private void despedidaUsuario(String chatId){
		//Se consulta en que estado estamos con el usuario
		User usuario = myManager.Users().FindById(Integer.parseInt(chatId));
		
		//Se consulta el mensaje de saludo para el usuario
		TypeMessage saludos = myManager.TypeMessage().FindById(EtypeMessage.DESPEDIDA.ordinal());
		List<Message> mensajesSaludos = saludos.getMessages();
		int random = 0 + (int)(Math.random() * ((mensajesSaludos.size()-1 - 0) + 1));
		mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()), mensajesSaludos.get(random).getMessage());
	}

}
