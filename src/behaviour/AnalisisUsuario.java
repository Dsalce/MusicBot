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
import model.Enumerations.EState;
import model.Enumerations.EtypeMessage;
import utiles.EnviarMensajeTelegram;
import utiles.Lexico;
import utiles.StanfordNPL;

public class AnalisisUsuario extends OneShotBehaviour{

	private static final long serialVersionUID = 1L;
	
	//Variable para la BBDD
	private Manager myManager = new Manager();
	
	//Insatancia del comportamiento para enviar el mensaje de respuesta al usuario
	private EnviarMensajeTelegram mensajeRespuesta = new EnviarMensajeTelegram();
	
	//Obejto serializable con la informacion pasada entre agentes
	private SerializableObject objetoSerializable = null;
	
	//Casos para la afirmacion negacion
	private final int AFIRMATIVO = 0;
	private final int NEGATIVO = 1;
	private final int OTRO = 2;
	
	//Casos para cuando estas ofreciendo una cancion
	private final int mensaje_ofrecer_caso = 0;
	private final int mensaje_ofrecer_cancion_escuchada_caso = 1;
	private final int mensaje_ofrecer_cancion_azar_caso = 2;
	private final int valoracion_caso = 3;
	private final int recordatorio_caso = 4;
	private final int ofrecer_charlar_caso = 5;
	
	//Mensaje para el usuario
	String mensaje_cancion_anterior = "¿Quieres escuchar una cancion que ya te he recomendado?";
	String mensaje_no_cancion_anterior = "¿Quieres que te recomiende alguna cancion?";
	String mensaje_ofrecer = "¿Quieres escuchar esta cancion? \n";
	String recordatorio = "Acuardate de valorarla cuando acabe";
	String seguir_ofrendiendo = "¿Quieres que te recomiende otra para tu estado de animo?";
	String ofrecer_charlar = "¿Quieres seguir hablando conmigo?";
	String recomendada = "";
	
	//Variable globales debido a que se utilizan en varios metodos
	private State estado;
	private List<Music> canciones;
	private int random_cancion;
	private Music cancion;
	//Se consulta en que estado estamos con el usuario
	private User usuario = null;
	
	//Consultar si ha escuchado una cancion anteriormente	
	List<UserMusic> lista_musica_usuario = null;
	
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
		
		//Se analiza si hay un saludo para saludarle lo primero
		if(tag_agrupados.containsKey(EtypeMessage.SALUDO.toString())){
			if(tag_agrupados.size() == 1){
				//Saludamos al usuario ya que no ha escrito nada mas
				saludarUsuario(chatId,true);
			}else{
				//Saludamos al usuario unicamente si estamos saludandole
				saludarUsuario(chatId,false);
			}
		}
		
		//Si hay una despedida para despedirse del usuario
		if(tag_agrupados.containsKey(EtypeMessage.DESPEDIDA.toString())){
			if(tag_agrupados.size() == 1){
				//Saludamos al usuario ya que no ha escrito nada mas
				despedidaUsuario();
			}else{
				//Saludamos al usuario unicamente si estamos saludandole
				despedidaUsuario();
			}
		}

		//Se analiza el sentimiento de la frase introducidda por el usuario
		if(analizarSentimiento(chatId,StanfordNPL.sentiment(objetoSerializable.getTexto()))){
			//Se realiza las preguntas para ofrecerle una cancion
			analizarPreguntasAlUsuario();
			return;
		}else{
			//Si se ha detectado algun tema de conversacion
			if(!tema.equals(EtypeMessage.SALUDO.toString()) && !tema.equals(EtypeMessage.DESPEDIDA.toString())){
				//Se habla al usuario respondiendo al tema escogido
				hablarTemaUsuario(tema,chatId);
			}
		}
		
		
	}
	
	private void analizarPreguntasAlUsuario() {
		//Se consulta el caso para el usuario 
		lista_musica_usuario = myManager.UserMusics().findByUserMusicChatid(usuario.getChatId());
		int caso = usuario.getCaso();
		switch(caso){
			case mensaje_ofrecer_caso:
				/*Se ofrece una cancion cancion (que ha escuchado 
				anteriormente o sino una de la lista de canciones respecto a su estado de animmo)*/
				ofrecerCancion();
				break;
			case mensaje_ofrecer_cancion_escuchada_caso:
				/*Se analiza la contestacion al caso anterior y se ofrece cancion segun su respuesta*/
				analizarRespuestaOfrecimientoRecomendada();
				break;
				
			case mensaje_ofrecer_cancion_azar_caso:
				analizarRespuestaOfrecimientoAzar();
			
			case recordatorio_caso:
				/*Se manda un recordatorio para que evalue despues de escuchar la cancion*/
				analizarRespuestaCancionOfrecida();
				break;
				
			case valoracion_caso:
				analizarValoracion();
				break;
				
			case ofrecer_charlar_caso: 
				ofrecerCharlar();
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

	private void ofrecerCharlar() {
		// TODO Auto-generated method stub
		
	}

	private void analizarValoracion() {
		int valoracion = frasePositivaOnegativa();
		
		//Analizamos la respuesta del usuario
		switch (valoracion){
			
			case AFIRMATIVO:
				enviarMensajeTelegra("Me alegro que te haya gustado, lo guardo para la proxima \n ¿Quieres seguir hablando conmigo?");
				//musica_usuario.setCorrect(2);
				//myManager.UserMusics().Update(usuario.getChatId(), musica_usuario);
				usuario.setState("Conversacion0");
				usuario.setSentimientoNegativo(0);
				usuario.setSentimientoNeutral(0);
				usuario.setSentimientoPositivo(0);
				myManager.Users().Update(usuario.getChatId(), usuario);
				break;
				
			case NEGATIVO:
				//musica_usuario.setCorrect(0);
				//myManager.UserMusics().Update(usuario.getChatId(), musica_usuario);
				//Como no le ha gustado volvemos a preguntarle
				//Ofrecer si quiere una cancion escuchada
				enviarMensajeTelegra("Lo siento, " );
				ofrecerCancion();
				break;
				
			case OTRO:
			default:
				enviarMensajeTelegra("Se mas preciso, no te entiendo");
				break;
		}
	}

	private void analizarRespuestaCancionOfrecida() {
		int valoracion = frasePositivaOnegativa();
		//Analizamos la respuesta del usuario
		switch (valoracion){
			
			case AFIRMATIVO:
				enviarMensajeTelegra("Adelante, selecciona el enlace \n" + recordatorio );
				updateUserCase(valoracion_caso);
				break;
				
			case NEGATIVO:
				//Como no le ha gustado volvemos a preguntarle
				//Ofrecer si quiere una cancio escuchada
				enviarMensajeTelegra("Lo siento, ");
				ofrecerCancion();
				break;
				
			case OTRO:
				enviarMensajeTelegra("Se mas preciso, no te entiendo");
				break;
		}
	}
	
	private void analizarRespuestaOfrecimientoAzar() {
		List<Music> lista_musica = null;
		List<Object[]> lista_especial = null;
		Music cancion_gusto =null;
		
		int valoracion = frasePositivaOnegativa();
		
		int random_musica;
		
		//Analizamos la respuesta del usuario
		switch (valoracion){
			
			case AFIRMATIVO:				
				//Se ofrece una cancion escuchada
				cancionAzar();
				updateUserCase(recordatorio_caso);
				break;
				
			case NEGATIVO:
				usuario.setState("Conversacion0");
				usuario.setSentimientoNegativo(0);
				usuario.setSentimientoNeutral(0);
				usuario.setSentimientoPositivo(0);
				myManager.Users().Update(usuario.getChatId(), usuario);
				break;
				
			case OTRO:
				enviarMensajeTelegra("Se mas preciso, no te entiendo");
				return;
		}
	}
	
	private void analizarRespuestaOfrecimientoRecomendada() {
		int valoracion = frasePositivaOnegativa();
		
		//Analizamos la respuesta del usuario
		switch (valoracion){
			
			case AFIRMATIVO:
				//Se ofrece una cancion escuchada
				if(!cancionEscuchada()){
					updateUserCase(mensaje_ofrecer_cancion_azar_caso);
					ofrecerCancionAzar();
					enviarMensajeTelegra("Lo siento, no tengo canciones de tu estado de animo que haya guardado \n");
				}
				
				updateUserCase(recordatorio_caso);
				break;
				
			case NEGATIVO:
				ofrecerCancionAzar();
				break;
				
			case OTRO:
				enviarMensajeTelegra("Se mas preciso, no te entiendo");
				return;
		}
	}

	private void ofrecerCancion() {
		if(lista_musica_usuario!=null){
			ofrecerCancionEscuchada();
			updateUserCase(mensaje_ofrecer_cancion_escuchada_caso);
		}else{
			ofrecerCancionAzar();
			updateUserCase(mensaje_ofrecer_cancion_azar_caso);
		}
		
	}
	
	private void ofrecerCancionEscuchada(){
		//Ofrecer si quiere una cancio escuchada
		enviarMensajeTelegra(mensaje_cancion_anterior);
		updateUserCase(mensaje_ofrecer_cancion_escuchada_caso);
	}
	
	private void ofrecerCancionAzar(){
		//No tiene guardado ninguna cancion anteriormente escuchada
		enviarMensajeTelegra(mensaje_no_cancion_anterior);
		updateUserCase(mensaje_ofrecer_cancion_azar_caso);
	}
	
	private boolean cancionEscuchada(){
		List<Object[]> lista_especial = null;
		
		if(usuario.getSentimientoPositivo()>1){
			//Se consulta las canciones escuchadas y ademas que le hayan gustado
			lista_especial = myManager.Musics().findByGusto(2);
		}else{
			//Se consulta las canciones escuchadas y ademas que le hayan gustado
			lista_especial = myManager.Musics().findByGusto(0);
		}
		if(lista_especial.size()>0){
			int random_musica = 0 + (int)(Math.random() * ((lista_especial.size()-1 - 0) + 1));
			//En caso de que la lista sea especial se coge el objeto y se parsea
			Object[] cancion_random = lista_especial.get(random_musica);
			//Creamos el objeto cancion
			Music cancion_gusto = new Music(cancion_random[1].toString(),cancion_random[2].toString(),null);
			recomendada = "esta es una de las que te gusto y te recomende \n";
			introducirCancionRecomendadBBDD(cancion_gusto);
			return true;
		}else{
			return false;
		}
		
		
	}
	
	private void cancionAzar(){
		List<Music> lista_especial = null;
		if(usuario.getSentimientoPositivo()>1){
			//Se consulta las canciones y ademas que le hayan gustado
			lista_especial = myManager.Musics().findByState(2);
		}else{
			//Se consulta las canciones escuchadas y ademas que le hayan gustado
			lista_especial = myManager.Musics().findByState(0);
		}
		if(lista_especial.size()>0){
			//Cogemos una cancion que le haya gustado al azar
			int random_musica = 0 + (int)(Math.random() * ((lista_especial.size()-1 - 0) + 1));
			//No ha escuchado una cancion anteriormente, se consultan las canciones positivas
			State estados_musica = myManager.States().FindById(EState.ALEGRE.ordinal()); 	
			List<Music> lista_musica = estados_musica.getMusics();
			//Crear objeto musica
			Music cancion_gusto = lista_musica.get(random_musica);
			//Cancion aleatoria de la bbdd
			recomendada = " pienso que esta es la que mas se ajusta a tu estado de animo \n" + EState.ALEGRE.toString();
			introducirCancionRecomendadBBDD(cancion_gusto);
		}
	}

	private void introducirCancionRecomendadBBDD(Music cancion_gusto){
		//Se envia por telegram
		enviarMensajeTelegra(mensaje_ofrecer + recomendada + cancion_gusto.getName() + ": " + cancion_gusto.getUrl() );
		mensaje_ofrecer = mensaje_ofrecer + "-" + cancion_gusto.getIdMusic();
		//Se introduce como neutral la opinion del usuario
		//UserMusic musica = myManager.UserMusics().findByUserMusicChatid(usuario.getChatId(), cancion_gusto.getIdMusic());
		//if(musica != null){
			/*musica.setCorrect(1);
			myManager.UserMusics().Update(usuario.getChatId(), musica_usuario);*/
		//}else{
			//No tiene almacenada la cancion, se inserta
			/*musica_usuario = new UserMusic(1,cancion_gusto,usuario);
			myManager.UserMusics().Add(musica_usuario);	*/		
		//}
	}
	private boolean analizarSentimiento(String chatId,String sentiment) {
		
		int suma = 0;
		//Caso de ser positivo
		if(sentiment.equals("Positive")){
			suma = usuario.getSentimientoPositivo()+1;
			//Se actualiza el numero de sentimientos positivos del usuario
			usuario.setSentimientoPositivo(suma);
			myManager.Users().Update(Integer.parseInt(chatId), usuario);
		}else if(sentiment.equals("Negative")){
			suma = usuario.getSentimientoNegativo()+1;
			//Se actualiza el numero de sentimientos negativos del usuario
			usuario.setSentimientoNegativo(suma);
			myManager.Users().Update(Integer.parseInt(chatId), usuario);
		}else{
			suma = usuario.getSentimientoNeutral()+1;
			//Se actualiza el numero de sentimientos neutros del usuario
			usuario.setSentimientoNeutral(suma);
			myManager.Users().Update(Integer.parseInt(chatId), usuario);
		}
		
		//Si hemos encontrado el sentimiento
		if((usuario.getSentimientoPositivo()>1) || (usuario.getSentimientoNegativo()>1) ){
			return true;
		}
		
		if(usuario.getSentimientoNeutral()>10){
			//No tiene guardado ninguna cancion anteriormente escuchada
			enviarMensajeTelegra("No consigo detectar tu estado de animo, se mas concreto");
			usuario.setSentimientoNeutral(0);
			myManager.Users().Update(usuario.getChatId(), usuario);
			return true;
		}
		
		return false;
	}
	
	private int frasePositivaOnegativa(){
		
		//Se mira en la frase introducida por el usuario si ha sido afirmativo o negativo
		List<Lexico> lista_palabra = objetoSerializable.getLista();
		for (Lexico palabra : lista_palabra){
			List<String> lista_tags = palabra.getTag();
			for(String tag : lista_tags){
				//Obtener el objeto de la posicion iterador
				if(tag.equals("Afirmativo")){
					return AFIRMATIVO;
				}else if(tag.equals("Negativo")){
					return NEGATIVO;
				}
			}
		}
		//Si no se encuentra ninguna etiqueta afirmativa o negativa 
		return OTRO;
	}

	private void hablarTemaUsuario(String tema,String chatId) {
		//Se consulta en que estado estamos con el usuario
		User usuario = myManager.Users().FindById(Integer.parseInt(chatId));
		
		//Se recorre los temas para saber la posicion del tema en la bbdd
		for(EtypeMessage tag : EtypeMessage.values()){
			if(tag.toString().equals(tema)){
				List<String> temas = null;
				int random_tema = 0;
				if(usuario.getState().contains("0")){
					//Se consulta una pregunta sobre el tema a tratar con el usuario
					temas = myManager.TypeMessage().findByNivel(tag.ordinal(), 0);
					random_tema = 0 + (int)(Math.random() * ((temas.size()-1 - 0) + 1));
					usuario.setState("Conversacion1");
					myManager.Users().Update(usuario.getChatId(), usuario);
				}else if(usuario.getState().contains("1")){
					//Se consulta una pregunta sobre el tema a tratar con el usuario
					temas = myManager.TypeMessage().findByNivel(tag.ordinal(), 1);
					random_tema = 0 + (int)(Math.random() * ((temas.size()-1 - 0) + 1));
				}
				if(temas.size()>0){
					//Se envia la respuesta al usuario
					String mensaje = temas.get(random_tema);				
					//Se envia la respuesta al usuario
					enviarMensajeTelegra(mensaje);
				}else{
					enviarMensajeTelegra("No te entiendo, se mas preciso");
				}
			}
		}
	}

	//Gestiona el envio del saludo al usuario 
	private void saludarUsuario(String chatId,boolean saludar){
		//Se consulta en que estado estamos con el usuario
		User usuario = myManager.Users().FindById(Integer.parseInt(chatId));
		
		//Se consulta el mensaje de saludo para el usuario
		TypeMessage saludos = myManager.TypeMessage().FindById(EtypeMessage.SALUDO.ordinal());
		List<Message> mensajesSaludos = saludos.getMessages();
		int random_saludo = 0 + (int)(Math.random() * ((mensajesSaludos.size()-1 - 0) + 1));			
		String chat = Integer.toString(usuario.getChatId());
		
		//Se envia la respuesta al usuario
		if(usuario.getState().equals("Saludo")){
			enviarMensajeTelegra(mensajesSaludos.get(random_saludo).getMessage());
			usuario.setState("Conversacion0");
			myManager.Users().Update(usuario.getChatId(), usuario);
			//Se consulta el mensaje de general para el usuario
			TypeMessage pregunta_general = myManager.TypeMessage().FindById(EtypeMessage.GENERAL.ordinal());
			List<Message> mensajesGeneral = pregunta_general.getMessages();
			int random_general = 0 + (int)(Math.random() * ((mensajesGeneral.size()-1 - 0) + 1));
			//Se envia la respuesta al usuario
			enviarMensajeTelegra( mensajesGeneral.get(random_general).getMessage());
		}else{
			if(saludar){
				//Se consulta el mensaje de general para el usuario
				TypeMessage pregunta_general = myManager.TypeMessage().FindById(EtypeMessage.GENERAL.ordinal());
				List<Message> mensajesGeneral = pregunta_general.getMessages();
				int random_general = 0 + (int)(Math.random() * ((mensajesGeneral.size()-1 - 0) + 1));
				//Se envia la respuesta al usuario
				enviarMensajeTelegra(mensajesSaludos.get(random_saludo).getMessage() + " otra vez \n" + mensajesGeneral.get(random_general).getMessage());
			}
		}
	}
	
	//Gestion el envio de despedido al usuario
	private void despedidaUsuario(){
		
		//Se consulta el mensaje de saludo para el usuario
		TypeMessage despedida = myManager.TypeMessage().FindById(EtypeMessage.DESPEDIDA.ordinal());
		List<Message> mensajesDespedida = despedida.getMessages();
		int random = 0 + (int)(Math.random() * ((mensajesDespedida.size()-1 - 0) + 1));
		enviarMensajeTelegra(mensajesDespedida.get(random).getMessage());
		
		//myManager.Users().Remove(usuario);
		
		usuario.setCaso(0);
		usuario.setLastMessage("");
		myManager.Users().Update(usuario);
	}

}
