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

	private int correct;

	//bi-directional many-to-one association to Music
	@ManyToOne
	@JoinColumn(name="IdMusic")
	private Music music;

	//bi-directional many-to-one association to User
	@Id
	@ManyToOne
	@JoinColumn(name="chatId")
	private User user;

	public UserMusic() {
	}

	public int getCorrect() {
		return this.correct;
	}

	public void setCorrect(int i) {
		this.correct = i;
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