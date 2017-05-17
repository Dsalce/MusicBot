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
	
	@Override
	public void action() {
		//Variable para los parametros del agente
		Object[] parametros = myAgent.getArguments();
		objetoSerializable = (SerializableObject)parametros[0];
		
		//En la posicion 0 esta el chatId
		String chatId = objetoSerializable.getChatId();
		//En la posicion 1 esta la lista de palabras
		List<Lexico> listaPalabras = objetoSerializable.getLista();
		
		//Se crea un hashmap para agrupar los tags
		HashMap<String,Integer> tag_agrupados  = new HashMap<String,Integer>();
		
		int iterador = 0;
		Lexico palabra = null;
		
		List<String> lista_tag = null;
		
		//Se analiza el sentimiento de la frase introducidda por el usuario
		if(analizarSentimiento(chatId,StanfordNPL.sentiment(objetoSerializable.getTexto()))){
			return;
		}
		
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
		
		//Se definen variables para recorrer el hashmap de tags
		int cantidad_tema_elegido = 0;
		int cont_tema = 0;
		String tema = null;
		//Se recorren los tags para saber el tema de conversacion
		for(String tag : tag_agrupados.keySet()){
			if((!tag.equals(EtypeMessage.DESPEDIDA.toString())) && (!tag.equals(EtypeMessage.SALUDO.toString()))){
				cont_tema = tag_agrupados.get(tag);
				if(cont_tema>cantidad_tema_elegido){
					tema = tag;
					cantidad_tema_elegido = cont_tema;
				}
			}
		}
		
		//Si se ha detectado algun tema de conversacion
		if(tema!=null){
			//Se habla al usuario respondiendo al tema escogido
			hablarTemaUsuario(tema,chatId);
		}
		
		
		//Si hay una despedida para despedirse del usuario
		if(tag_agrupados.containsKey(EtypeMessage.DESPEDIDA.toString())){
			if(tag_agrupados.size() == 1){
				//Saludamos al usuario ya que no ha escrito nada mas
				despedidaUsuario(chatId);
			}else{
				//Saludamos al usuario unicamente si estamos saludandole
				despedidaUsuario(chatId);
			}
		}
	}
	
	private boolean analizarSentimiento(String chatId,String sentiment) {
		State estado;
		List<Music> canciones;
		int random_cancion;
		Music cancion;
		boolean afirmativo = false;
		int suma = 0;
		//Se consulta en que estado estamos con el usuario
		User usuario = myManager.Users().FindById(Integer.parseInt(chatId));
		
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
			//Consultar si ha escuchado una cancion anteriormente
			UserMusic musica_usuario = myManager.UserMusics().FindById(Integer.parseInt(chatId));
			//En caso de que la lista de musica escuchada por el usuario no este vacia
			
			String mensaje_cancion_anterior = "¿Quieres escuchar una cancion que ya te he recomendado?";
			String mensaje_no_cancion_anterior = "No has escuchado nunca una cancion conmigo, ¿Quieres que te recomiende alguna?";
			String mensaje_ofrecer = "¿Quieres escuchar esta cancion?";
			String recordatorio = "Acuardate de valorarla cuando acabe";
			//Se mira en la frase introducida por el usuario si ha sido afirmativo o negativo
			List<Lexico> lista_palabra = objetoSerializable.getLista();
			for (Lexico palabra : lista_palabra){
				List<String> lista_tags = palabra.getTag();
				for(String tag : lista_tags){
					//Obtener el objeto de la posicion iterador
					if(tag.equals("Afirmativo")){
						afirmativo = true;
						break;
					}
				}
				
			}
		
			//En caso de que el ultimo mensaje se ofrecimiento de mensaje
			if((usuario.getLastMessage().equals(mensaje_cancion_anterior)) || (usuario.getLastMessage().equals(mensaje_no_cancion_anterior))){
				List<Music> lista_musica = null;
				Music cancion_gusto =null;
				if(afirmativo){
					if(usuario.getSentimientoPositivo()>1){
						//Se consulta las canciones escuchadas y ademas que le hayan gustado
						lista_musica = myManager.Musics().findByGusto(1);
						if(lista_musica.size()==0){
							//No ha escuchado una cancion anteriormente, se consultan las canciones positivas
							State estados_musica = myManager.States().FindById(EState.ALEGRE.ordinal()); 	
							lista_musica = estados_musica.getMusics();
						}
					}else{
						//Se consulta las canciones escuchadas y ademas que le hayan gustado
						lista_musica = myManager.Musics().findByGusto(1);
						if(lista_musica.size()==0){
							//No ha escuchado una cancion anteriormente, se consultan las canciones positivas
							State estados_musica = myManager.States().FindById(EState.TRISTE.ordinal()); 	
							lista_musica = estados_musica.getMusics();
						}
					}
					//Cogemos una cancion que le haya gustado al azar
					int random_musica = 0 + (int)(Math.random() * ((lista_musica.size()-1 - 0) + 1));
					//Crear objeto musica
					cancion_gusto = lista_musica.get(random_musica);
				}else{
					State lista_positivas = null;
					//En caso de que el usurio haya escrito mas de una frase positiva 
					if(usuario.getSentimientoPositivo()>1){
						//Se cogen todas las canciones alegres
						lista_positivas = myManager.States().FindById(EState.ALEGRE.ordinal());
					}else if(usuario.getSentimientoNegativo()>1){
						//Se cogen todas las canciones alegres
						lista_positivas = myManager.States().FindById(EState.TRISTE.ordinal());
					}
					lista_musica = lista_positivas.getMusics();
					//Cogemos una cancion que le haya gustado al azar
					int random_musica = 0 + (int)(Math.random() * ((lista_musica.size()-1 - 0) + 1));
					cancion_gusto = lista_musica.get(random_musica);
					
				}
				//Se envia por telegram
				mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()), mensaje_ofrecer + cancion_gusto.getName() + ": " + cancion_gusto.getUrl() );
				mensaje_ofrecer = mensaje_ofrecer + "-" + cancion_gusto.getIdMusic();
				//Se introduce como neutral la opinion del usuario
				List<Music> musica = myManager.UserMusics().findByUserMusicChatid(Integer.parseInt(chatId), cancion_gusto.getIdMusic());
				if(musica.size()!=0){
					//Tiene la cancion guardada por lo que lo actualizamos
					musica_usuario.setCorrect(1);
					myManager.UserMusics().Update(Integer.parseInt(chatId), musica_usuario);
				}else{
					//No tiene almacenada la cancion, se inserta
					musica_usuario = new UserMusic(1,cancion_gusto,usuario);
					myManager.UserMusics().Add(musica_usuario);			
				}
			}else if (usuario.getLastMessage().equals(mensaje_ofrecer)){
				//Si le ha gustado lo alamacenamos
				if(afirmativo){
					mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()),"Adelante, selecciona el enlace" + recordatorio );
				}else{
					//Como no le ha gustado volvemos a preguntarle
					//Ofrecer si quiere una cancio escuchada
					mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()),"Lo siento, " + mensaje_cancion_anterior);
					usuario.setLastMessage(mensaje_cancion_anterior);
					myManager.Users().Update(Integer.parseInt(chatId), usuario);
				}
			}else if((usuario.getLastMessage().equals(recordatorio)) || (usuario.getLastMessage().equals(mensaje_ofrecer))){
				//Si le ha gustado lo alamacenamos
				if(afirmativo){
					mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()),"Me alegro que te haya gustado, lo guardo para la proxima" );
					musica_usuario.setCorrect(2);
					myManager.UserMusics().Update(Integer.parseInt(chatId), musica_usuario);
				}else{
					musica_usuario.setCorrect(0);
					myManager.UserMusics().Update(Integer.parseInt(chatId), musica_usuario);
					//Como no le ha gustado volvemos a preguntarle
					//Ofrecer si quiere una cancion escuchada
					if(musica_usuario.getMusic()!=null){
						mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()),"Lo siento, " + mensaje_cancion_anterior);
						usuario.setLastMessage(mensaje_cancion_anterior);
						myManager.Users().Update(Integer.parseInt(chatId), usuario);
					}else{
						//No tiene guardado ninguna cancion anteriormente escuchada
						mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()),"Lo siento, " + mensaje_no_cancion_anterior);
						usuario.setLastMessage(mensaje_no_cancion_anterior);
						myManager.Users().Update(Integer.parseInt(chatId), usuario);
					}
					
				}
			}else if(musica_usuario!=null){
				//Ofrecer si quiere una cancio escuchada
				mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()),mensaje_cancion_anterior);
				usuario.setLastMessage(mensaje_cancion_anterior);
				myManager.Users().Update(Integer.parseInt(chatId), usuario);
			}else{
				//No tiene guardado ninguna cancion anteriormente escuchada
				mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()),mensaje_no_cancion_anterior);
				usuario.setLastMessage(mensaje_no_cancion_anterior);
				myManager.Users().Update(Integer.parseInt(chatId), usuario);
			}
			return true;
		}
		
		if(usuario.getSentimientoNeutral()>10){
			//No tiene guardado ninguna cancion anteriormente escuchada
			mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()),"No consigo detectar tu estado de animo, se mas concreto");
			return true;
		}
		return false;
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
				//Se envia la respuesta al usuario
				String mensaje = temas.get(random_tema);				
				//Se envia la respuesta al usuario
				mensajeRespuesta.enviar(chatId, mensaje);
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
			mensajeRespuesta.enviar(chat, mensajesSaludos.get(random_saludo).getMessage());
			usuario.setState("Conversacion0");
			myManager.Users().Update(usuario.getChatId(), usuario);
		}else{
			if(saludar){
				//Se consulta el mensaje de general para el usuario
				TypeMessage pregunta_general = myManager.TypeMessage().FindById(EtypeMessage.GENERAL.ordinal());
				List<Message> mensajesGeneral = pregunta_general.getMessages();
				int random_general = 0 + (int)(Math.random() * ((mensajesGeneral.size()-1 - 0) + 1));
				//Se envia la respuesta al usuario
				mensajeRespuesta.enviar(chat, mensajesSaludos.get(random_saludo).getMessage() + " otra vez \n" + mensajesGeneral.get(random_general).getMessage());
			}
		}
	}
	
	//Gestion el envio de despedido al usuario
	private void despedidaUsuario(String chatId){
		//Se consulta en que estado estamos con el usuario
		User usuario = myManager.Users().FindById(Integer.parseInt(chatId));
		
		//Se consulta el mensaje de saludo para el usuario
		TypeMessage despedida = myManager.TypeMessage().FindById(EtypeMessage.DESPEDIDA.ordinal());
		List<Message> mensajesDespedida = despedida.getMessages();
		int random = 0 + (int)(Math.random() * ((mensajesDespedida.size()-1 - 0) + 1));
		mensajeRespuesta.enviar(Integer.toString(usuario.getChatId()), mensajesDespedida.get(random).getMessage());
		usuario.setState("Saludo");
		myManager.Users().Update(usuario.getChatId(), usuario);
	}

}
