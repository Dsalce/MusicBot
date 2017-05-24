package behaviour;

import java.util.HashMap;
import java.util.List;

import bussines.Manager;
import jade.core.behaviours.OneShotBehaviour;
import model.Message;
import model.Music;
import model.SerializableObject;
import model.State;
import model.TypeMessage;
import model.User;
import model.UserMusic;
import model.Enumerations.EtypeMessage;
import utiles.EnviarMensajeTelegram;
import utiles.Lexico;
import utiles.StanfordNPL;

public class AnalisisAdmin extends OneShotBehaviour{

	private static final long serialVersionUID = 1L;
	
	//Variable para la BBDD
	private Manager myManager = new Manager();
	
	//Insatancia del comportamiento para enviar el mensaje de respuesta al usuario
	private EnviarMensajeTelegram mensajeRespuesta = new EnviarMensajeTelegram();
	
	//Obejto serializable con la informacion pasada entre agentes
	private SerializableObject objetoSerializable = null;
	
	//Variable globales debido a que se utilizan en varios metodos
	private State estado;
	private List<Music> canciones;
	private int random_cancion;
	private Music cancion;
	//Se consulta en que estado estamos con el usuario
	private User usuario = null;
	
	//Consultar si ha escuchado una cancion anteriormente
	UserMusic musica_usuario = null;
	
	//Casos para la afirmacion negacion
	private final int PREGUNTAR = 0;
	private final int ANADIR = 1;
	private final int BUSCAR = 2;
	private final int ELIMINAR = 3;

	@Override
	public void action() {
		//Variable para los parametros del agente
		Object[] parametros = myAgent.getArguments();
		objetoSerializable = (SerializableObject)parametros[0];
		
		//En la posicion 0 esta el chatId
		String chatId = objetoSerializable.getChatId();
		//Se guarda el usuario con el que se va a trabajar
		usuario = myManager.Users().FindById(Integer.parseInt(chatId));
				
		//En la posicion 1 esta la lista de palabras
		List<Lexico> listaPalabras = objetoSerializable.getLista();
		
		//Se crea un hashmap para agrupar los tags
		HashMap<String,Integer> tag_agrupados  = new HashMap<String,Integer>();
		
		int iterador = 0;
		Lexico palabra = null;
		
		List<String> lista_tag = null;
				
		//Se recorre 
		for (iterador=0;iterador<listaPalabras.size();iterador++){
			//Obtener el objeto de la posicion iterador
			palabra = listaPalabras.get(iterador);
			lista_tag = palabra.getTag();
			//Se recorren todos los tag de la palabra
			for(String tag: lista_tag ){
				//Se suma uno al tag
				if(tag_agrupados.containsKey(tag)){
					tag_agrupados.put(tag.toUpperCase(), tag_agrupados.get(tag)+1);	
				}else{
					tag_agrupados.put(tag.toUpperCase(), 1);	
				}
			}
		}
		
		//Una vez los tag agrupados se analiza el numero de veces repetido el
		//Se definen variables para recorrer el hashmap de tags
		int cantidad_tema_elegido = 0;
		int cont_tema = 0;
		String tema = null;
		//Se recorren los tags para saber el tema de conversacion
		for(String tag : tag_agrupados.keySet()){
			//if((!tag.equals(EtypeMessage.DESPEDIDA.toString())) && (!tag.equals(EtypeMessage.SALUDO.toString()))){
				cont_tema = tag_agrupados.get(tag);
				if(cont_tema>cantidad_tema_elegido){
					tema = tag;
					cantidad_tema_elegido = cont_tema;
				}
			//}
		}
		
		if(tag_agrupados.containsKey("Añadir")){
			updateUserCase(1);
		}else if(tag_agrupados.containsKey("Buscar")){
			updateUserCase(2);
		}else if(tag_agrupados.containsKey("Eliminar")){
			updateUserCase(3);
		}
		
		analizarPreguntasAlUsuario();
		
	}
	
	private void analizarPreguntasAlUsuario() {
		//Se consulta el caso para el usuario 
		musica_usuario = myManager.UserMusics().FindById(usuario.getChatId());
		int caso = usuario.getCaso();
		switch(caso){
			case PREGUNTAR:
				//Se le pregunta al usuario que gestion quiere hacer
				enviarMensajeTelegra("Cuentame que quieres hacer como Administrador");
				break;
			case ANADIR:
				/*Se analiza la conversacion para añadir una cancion*/
				anadirCancion();
				break;
				
			case BUSCAR:
				buscarCancion();
				break;
			
			case ELIMINAR:
				/*Se manda un recordatorio para que evalue despues de escuchar la cancion*/
				eliminarCancion();
				break;
				
		}
		
	}
	
	//Metodo encargado de enviar mensajes a telegram
	private void enviarMensajeTelegra(String message) {
		mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()),message);
	}
	
	//Metodo encargado de actualizar al usuario
	private void updateUserCase(int caso){
		//Se introduce el ultimo mensaje enviado al usuario
		usuario.setCaso(caso);
		myManager.Users().Update(usuario.getChatId(), usuario);
	}

	//Gestiona añadir una cancion 
	private void anadirCancion(){	
		//Por primera vez se le pregunta que cancion quiere añadir
		if(!usuario.getLastMessage().equals("Digame que cancion desea añadir")){
			enviarMensajeTelegra("Digame que cancion desea añadir");
			usuario.setLastMessage("Digame que cancion desea añadir");
			myManager.Users().Update(usuario.getChatId(), usuario);
		}else{
			//Una vez preguntado al usuario se introduce la cancion que quiere
			
		}
		
	}

	//Gestiona la busqueda de una cancion
	private void buscarCancion(){
		//Por primera vez se le pregunta que cancion quiere buscar
		if(!usuario.getLastMessage().equals("¿Que cancion quieres buscar?")){
			enviarMensajeTelegra("¿Que cancion quieres buscar?");
			usuario.setLastMessage("¿Que cancion quieres buscar?");
			myManager.Users().Update(usuario.getChatId(), usuario);
		}else{
			//Se le respondesi existe o no
			
		}
	}
	
	//Gestiona la eliminacion de canciones
	private void eliminarCancion(){
		//Por primera vez se le pregunta que cancion quiere eliminar
		if(!usuario.getLastMessage().equals("¿Que cancion quieres eliminar digame su nombre?")){
			enviarMensajeTelegra("¿Que cancion quieres eliminar digame su nombre?");
			usuario.setLastMessage("¿Que cancion quieres eliminar digame su nombre?");
			myManager.Users().Update(usuario.getChatId(), usuario);
		}else{
			//Se busca la cancion y se elimna
			if(){
				//Se ha borrado correctamente
				enviarMensajeTelegra("Se ha borrado de forma exitosa");
			}else{
				//No se ha podido borrar
				enviarMensajeTelegra("No se ha podido borrar correctamente, ¿quiere realizar otra operacion?");
			}
			
		}
	}
}
