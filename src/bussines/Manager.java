package bussines;

import dataAccess.RepositoryBase;
import model.Music;
import model.State;
import model.User;
import model.UserMusic;
import model.Message;
import model.TypeMessage;

public class Manager 
{
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
	 * Manejo de las entidades States
	 */
	private RepositoryBase<State> OfStates;
	/**
	 * Manejo de las entidades States
	 */
	private RepositoryBase<TypeMessage> OfTypeMessage;
	/**
	 * Manejo de las entidades States
	 */
	private RepositoryBase<Message> OfMessage;
	
	public Manager()
	{
		OfMusics = new RepositoryBase<Music>(Music.class);
		OfUseres = new RepositoryBase<User>(User.class);
		OfUserMusics = new RepositoryBase<UserMusic>(UserMusic.class);
		OfStates = new RepositoryBase<State>(State.class);
		OfTypeMessage = new RepositoryBase<>(TypeMessage.class);
		OfMessage = new RepositoryBase<>(Message.class);
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
	
	public RepositoryBase<TypeMessage> TypeMessages()
	{
		return OfTypeMessage;
	}
	
	public RepositoryBase<Message> Messages()
	{
		return OfMessage;
	}
	
	public void Dispose()
	{
		OfMusics.Dispose();
		OfUseres.Dispose();
		OfStates.Dispose();
		OfUserMusics.Dispose();
		OfTypeMessage.Dispose();
		OfMessage.Dispose();
	}
}