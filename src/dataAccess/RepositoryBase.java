package dataAccess;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import model.Message;
import model.Music;
import model.UserMusic;

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
	
	public List<String> findByNivel(int TypeMessage, int nivel)
	{
		
		Manager.getTransaction().begin();
				
		Query query = Manager.createNativeQuery(" SELECT `Message` FROM  `messages` WHERE `IdTypeMessage` = '" + TypeMessage +"' AND `Nivel` = '" + nivel + "'");
		
		Manager.getTransaction().commit();
		
		return (List<String>)query.getResultList( );
	}
	
	public List<Object[]> findByGusto(int gusto)
	{
		
		Manager.getTransaction().begin();
				
		Query query = Manager.createNativeQuery(" SELECT `musics`.`IdMusic`,`musics`.`Name`,`musics`.`URL` FROM user_music,musics WHERE `IdState`= 1 AND `user_music`.`Correct`= 1 OR `user_music`.`Correct`= " + gusto);
		
		Manager.getTransaction().commit();
		
		return (List<Object[]>)query.getResultList();
	}
	
	public List<Music> findByUserMusicChatid(int chatId, int idMusic)
	{
		
		Manager.getTransaction().begin();
				
		Query query = Manager.createNativeQuery("SELECT `Correct`, `chatId`, `IdMusic` FROM `user_music` WHERE `chatId` = " + chatId + " AND `IdMusic` = " + idMusic);
		
		Manager.getTransaction().commit();
		
		return (List<Music>)query.getResultList();
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
		
		return updEntity;
	}
	
	public void remove(T updEntity, int chatId)
	{
		/*Manager.getTransaction().begin();
		
		T entity = Manager.find(Type, chatId);
		
		Manager.remove(entity);
		
	Manager.getTransaction().commit();*/
		Manager.getTransaction().begin();
		
		Query query = Manager.createNativeQuery("DELETE FROM users ");
		
		Manager.getTransaction().commit();
		
	}
	
	public void Remove(T entity)
	{
		Manager.getTransaction().begin();
		
		Manager.remove(entity);
		
		Manager.getTransaction().commit();
	}
	
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
