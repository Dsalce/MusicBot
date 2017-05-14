package bussines;

import dataAccess.RepositoryBase;
import model.Message;
import model.Music;
import model.User;
import model.UserMusic;
import model.State;
import model.TypeMessage;

/**
 * 
 * @author 
 *
 */
public class Manager {
	
	/**
	 * Manejo de las entidades Musics
	 */
	private RepositoryBase<Music> OfMusics;
	/**
	 * Manejo de las entidades Users
	 */
	private RepositoryBase<User> OfUseres;
	/**
	 * Manejo de las entidades User_Musics
	 */
	private RepositoryBase<UserMusic> OfUserMusics;
	/**
	 * Manejo de las entidades Message
	 */	
	private RepositoryBase<Message> ofMessage;
	/**
	 * Manejo de las entidades Message
	 */	
	private RepositoryBase<TypeMessage> ofTypeMessages;
	/**
	 * Manejo de las entidades States
	 */	
	private RepositoryBase<State> OfStates;
	
	public Manager()
	{
		OfMusics = new RepositoryBase<Music>(Music.class);
		OfUseres = new RepositoryBase<User>(User.class);
		OfUserMusics = new RepositoryBase<UserMusic>(UserMusic.class);
		OfStates = new RepositoryBase<State>(State.class);
		ofMessage = new RepositoryBase<Message>(Message.class);
		ofTypeMessages =new RepositoryBase<TypeMessage>(TypeMessage.class);
	}
	
	public RepositoryBase<Music> Musics()
	{
		return OfMusics;
	}
	
	public RepositoryBase<User> Users()
	{
		return OfUseres;
	}
	
	public RepositoryBase<UserMusic> UserMusics()
	{
		return OfUserMusics;
	}
	
	public RepositoryBase<State> States()
	{
		return OfStates;
	}
	
	public RepositoryBase<TypeMessage> TypeMessage()
	{
		return ofTypeMessages;
	}
	
	public void Dispose()
	{
		OfMusics.Dispose();
		OfUseres.Dispose();
		OfStates.Dispose();
		OfUserMusics.Dispose();
	}
}
