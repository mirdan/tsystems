package sk.tsystems.gamestudio.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import sk.tsystems.gamestudio.service.PlayerServiceJPA;

@Entity
public class Player {
	
	
	@Id
	@GeneratedValue
	private int ident;
	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}

	private String name;
	private String passwd;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	

	private boolean userExists(String name) {
		PlayerServiceJPA registerService = new PlayerServiceJPA();
		boolean userExists = false;
		for (Player n : registerService .listAllUsers()) {
			if(n.equals(getName())) 
				userExists = true;
		}
		return userExists;
	}
	
	
	private void signinValidator(String name,String passwd1,String passwd2 ) {
	}
	
	
	public Player(String name, String passwd) {
		super();
		this.name = name;
		this.passwd = passwd;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public String getName() {
		return name;
	}
}
