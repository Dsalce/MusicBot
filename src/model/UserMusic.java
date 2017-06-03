package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_music database table.
 * 
 */
@Entity
@Table(name="user_music")
@NamedQuery(name="UserMusic.findAll", query="SELECT u FROM UserMusic u")
public class UserMusic implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserMusicPK id;

	private int correct;

	//bi-directional many-to-one association to Music
	@ManyToOne
	@JoinColumn(name="IdMusic")
	private Music music;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="chatId")
	private User user;

	public UserMusic() {
	}
	
	public UserMusic (int correct,Music music, User user){
		this.correct = correct;
		this.music = music;
		this.user = user;
	}

	public UserMusicPK getId() {
		return this.id;
	}

	public void setId(UserMusicPK id) {
		this.id = id;
	}

	public int getCorrect() {
		return this.correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public Music getMusic() {
		return this.music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}