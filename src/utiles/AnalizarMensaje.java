package utiles;

import java.util.ArrayList;
import java.util.List;

import bussines.Manager;
import jade.core.AID;
import model.User;

public class AnalizarMensaje{

	private static final long serialVersionUID = 1L;
	
	//Instancia para Standfor
	private StanfordNPL standfor = new StanfordNPL();
	
	//Instancia para las reglas
	private LlamarReglas reglas = new LlamarReglas();
	
	//Variable para la BBDD
	private Manager myManager = new Manager();
	
	//Se encarga de gestionar el analisis del mensaje por standfor
	public void analizarPorStandfor(String mensaje){
		int iterador = 0;
		Lexico palabra = null;
		String tag = "";
		//Guarda y analiza la lisa de palabras analizadas
		List<Lexico> listaPalabras = new ArrayList<Lexico>();
		//Se recorre 
		for (iterador=0;iterador<listaPalabras.size();iterador++){
			//Obtener el objeto de la posicion iterador
			palabra = listaPalabras.get(iterador);
			tag = palabra.getTag();
			if(tag == ""){
				//Se analiza la palabra mediante drools para intentar identificar su tag
				tag = analizarPorReglas(palabra);
			}
			
			//En caso de ser un saludo
			if(tag == "Saludo"){
				//Se llama a la funcion encargada de saludar al usuario
				
			}
			//En caso de ser una despedida
			else if(tag == "Bye"){
				//Se llama a la funcion encargada de despedirse del usuario
				
			}
		}
		standfor.parser(mensaje);
	}
	
	//Se encarga de gestionar el analisis del mensaje mediante las reglas
	private String analizarPorReglas(Lexico palabra){
		//Se devuelve el tag de la palabra analizada por drools
		return reglas.parseWord(palabra.getWord());
	}
	
	//Gestiona el envio del saludo al usuario 
	private void saludarUsuario(){
		boolean saludado = false;
		//Se consulta en que estado estamos con el usuario
		User usuario = myManager.Users().FindById(5);
		
		if(usuario.getState() ==  "Saludo"){
			//Saludamos al usuario
			
		}else{
			
		}
	}
	
	//Gestion el envio de despedido al usuario
	private void despedidaUsuario(){
		
	}

}
