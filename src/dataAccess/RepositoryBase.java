package dataAccess;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.NamedQuery;
import javax.persistence.Query;

import model.*;

public class RepositoryBase<T> {

	private EntityManagerFactory Context;
	
	private Class<T> Type;
	
	private String Query;
	
	private EntityManager Manager;
	
	public RepositoryBase(Class<T> _Type)
	{
		Context = Persistence.createEntityManagerFactory("DataAccess");
		Manager = Context.createEntityManager();
		Type = _Type;
		
	    NamedQuery q = Type.getAnnotation(NamedQuery.class);
	    
	    Query = q.query();
	}
	
	/**
	 * Petición para obtener todos las entidades del tipo T
	 * @return 
	 */
	public List<T> GetAll()
	{
		Manager.getTransaction().begin();
		
		List<T> result = Manager.createQuery(Query, Type).getResultList();
		
		Manager.getTransaction().commit();
		
		return result;
	}
	
	/**
	 * Busca una entidad por su identificador
	 * @param Id
	 * @return
	 */
	public T FindById(int Id)
	{
		Manager.getTransaction().begin();
		
		T result = Manager.find(Type, Id);
		
		Manager.getTransaction().commit();
		
		return result;
	}
	/*
	/**
	 * Busca una entidad por su identificador
	 * @param Id
	 * @return
	 */
	
	/***
	 * 
	 * @param TypeMessage
	 * @param nivel
	 * @return
	 */
	public List<String> findByNivel(int TypeMessage, int nivel)
	{
		Manager.getTransaction().begin();
				
		Query query = Manager.createNativeQuery(" SELECT `Message` FROM  `messages` WHERE `IdTypeMessage` = '" + TypeMessage +"' AND `Nivel` = '" + nivel + "'");
		
		Manager.getTransaction().commit();
		
		return (List<String>)query.getResultList( );
	}
	
	/***
	 * 
	 * @param gusto
	 * @return
	 */
	public List<Object[]> findByGusto(int gusto)
	{
		
		Manager.getTransaction().begin();
				
		Query query = Manager.createNativeQuery(" SELECT `musics`.`IdMusic`,`musics`.`Name`,`musics`.`URL` FROM user_music,musics WHERE `IdState`= 1 AND `user_music`.`Correct`= 1 OR `user_music`.`Correct`= " + gusto);
		
		Manager.getTransaction().commit();
		
		return (List<Object[]>)query.getResultList();
	}
	
	/***
	 * 
	 * @param chatId
	 * @param idMusic
	 * @return
	 */
	public List<Music> findByUserMusicChatid(int chatId, int idMusic)
	{
		
		Manager.getTransaction().begin();
				
		Query query = Manager.createNativeQuery("SELECT `Correct`, `chatId`, `IdMusic` FROM `user_music` WHERE `chatId` = " + chatId + " AND `IdMusic` = " + idMusic);
		
		Manager.getTransaction().commit();
		
		return (List<Music>)query.getResultList();
	}
	
	/***
	 * 
	 * @param name
	 * @return
	 */
	public Music findMusicByName(String name)
	{
		Music _music = null;
		
		Manager.getTransaction().begin();
		
		Query query = Manager.createNativeQuery("select * from musics where Name = '" + name + "'", Music.class);
		
		_music =  (Music)query.getSingleResult();
		
		Manager.getTransaction().commit();
		
		return _music;
	}
	
	/***
	 * 
	 * @param URL
	 * @return
	 */
	public Music findMusicByURL(String URL)
	{
		Music _music = null;
		
		Manager.getTransaction().begin();
		
		Query query = Manager.createNativeQuery("select * from musics where URL = '" + URL + "'", Music.class);
		
		_music =  (Music)query.getSingleResult();
		
		Manager.getTransaction().commit();
		
		return _music;
	}
	
	/**
	 * Actualiza una entidad con identificador Id en base a updEntity 
	 * @param Id
	 * @param updEntity
	 * @return
	 */
	public T Update(int Id, T updEntity)
	{
		Manager.getTransaction().begin();
		
		T entity = Manager.find(Type, Id);
		
		Manager.detach(updEntity);
		
		Manager.merge(entity);
		
		Manager.getTransaction().commit();
		
		return entity;
	}
	
	/***
	 * No se modifican los atributos referenciales
	 * @param updEntity
	 * @return
	 */
	public Message Update(Message updEntity)
	{
		Manager.getTransaction().begin();
		
		Message entity = Manager.find(Message.class, updEntity.getIdMessage());
		entity.setMessage(updEntity.getMessage());
		entity.setNivel(updEntity.getNivel());
		entity.setTypeMessage(updEntity.getTypeMessage());
		
		Manager.getTransaction().commit();
		
		return entity;
	}
	
	/***
	 * 
	 * @param updEntity
	 * @return
	 */
	public Music Update(Music updEntity)
	{
		Manager.getTransaction().begin();
		
		Music entity = Manager.find(Music.class, updEntity.getIdMusic());
		entity.setName(updEntity.getName());
		entity.setUrl(updEntity.getUrl());
		entity.setState(updEntity.getState());
		
		Manager.getTransaction().commit();
		
		return entity;
	}
	
	/***
	 * 
	 * @param updEntity
	 * @return
	 */
	public User Update(User updEntity)
	{
		Manager.getTransaction().begin();
		
		User entity = Manager.find(User.class, updEntity.getChatId());
		entity.setAdmin(updEntity.getAdmin());
		entity.setCaso(updEntity.getCaso());
		entity.setLastMessage(updEntity.getLastMessage());
		entity.setSentimientoNegativo(updEntity.getSentimientoNegativo());
		entity.setSentimientoPositivo(updEntity.getSentimientoPositivo());
		entity.setSentimientoNeutral(updEntity.getSentimientoNeutral());
		entity.setState(updEntity.getState());
		
		Manager.getTransaction().commit();
		
		return entity;
	}
	
	/***
	 * 
	 * @param updEntity
	 * @param chatId
	 */
	public void remove(T updEntity, int chatId)
	{
		Manager.getTransaction().begin();
		
		Query query = Manager.createNativeQuery("DELETE FROM users ");
		
		Manager.getTransaction().commit();
		
	}
	
	/***
	 * 
	 * @param entity
	 */
	public void Remove(T entity)
	{
		Manager.getTransaction().begin();
		
		Manager.remove(entity);
		
		Manager.getTransaction().commit();
	}
	
	/***
	 * 
	 * @param Id
	 */
	public void Remove(int Id)
	{
		Manager.getTransaction().begin();
		
		T entity = Manager.find(Type, Id);
		
		Manager.remove(entity);
		
		Manager.getTransaction().commit();
	}
	
	/**
	 * Agrega una nueva entidad
	 * @param entity
	 * @return
	 */
	public T Add(T entity)
	{
		Manager.getTransaction().begin();
		
		Manager.persist(entity);
		
		Manager.getTransaction().commit();
		
		return entity;
	}
	
	/**
	 * Cierre de la conexión
	 */
	public void Dispose()
	{
		if (Manager != null) Manager.close();
	}
}
