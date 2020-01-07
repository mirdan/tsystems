package sk.tsystems.gamestudio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.Player;

@Component
@Transactional
public class PlayerServiceJPA {
	@PersistenceContext
	private EntityManager entityManager;

	public void addUser(Player player) {
		entityManager.persist(player);
	}

	@SuppressWarnings("unchecked")

	public List<Player> listAllUsers() {

		return (List<Player>) entityManager.createQuery("select p from Player p").getResultList();
	}

	public Player findUserByName(String name) {
		try {
			return (Player) entityManager.createQuery("select p from Player p where p.name= :name").setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
}
