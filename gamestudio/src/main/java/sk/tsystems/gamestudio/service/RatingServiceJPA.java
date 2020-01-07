package sk.tsystems.gamestudio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.entity.Rat;
import sk.tsystems.gamestudio.entity.Rating;

@Component
@Transactional
public class RatingServiceJPA {

	@PersistenceContext
	private EntityManager entityManager;

	
	public void setRating(Rating rating) {
		try {
			Rat dbRat = (Rat) entityManager
					.createQuery("select r from Rat r where r.username =:username and r.game=:game")
					.setParameter("username", rating.getUsername()).setParameter("game", rating.getGame()).getSingleResult();
			dbRat.setValue(rating.getValue());
		} catch (NoResultException e) {
			entityManager.persist(rating);
		}
	}
	
	
	
	
	
	public void addRating(Rating rating) {
		entityManager.persist(rating);
	}

	public Rating findUserByNameAndGame(String name, String game) {
		try {
			return (Rating) entityManager
					.createQuery("select r from Rating r where r.username= :name and r.game = :game")
					.setParameter("name", name).setParameter("game", game).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void updateUsersRating(String name, String game, int value) {
		entityManager.createQuery("update Rating r SET r.value = :value  WHERE r.username = :name and r.game =:game")
				.setParameter("value", value).setParameter("name", name).setParameter("game", game).executeUpdate();
	}

	public String findRatingByGame(String game) {
		try {
			return String.valueOf(Math.round((double) (entityManager
					.createQuery("select avg(value) from Rating r where r.game= :game group by(game)")
					.setParameter("game", game).getSingleResult())));
		} catch (NoResultException e) {
			return "not rated yet";
		}
	}

}
