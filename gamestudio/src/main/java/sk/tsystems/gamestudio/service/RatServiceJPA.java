package sk.tsystems.gamestudio.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.Rat;

@Component
@Transactional
public class RatServiceJPA implements RatService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void setRat(Rat rat) {
		try {
			Rat dbRat = (Rat) entityManager
					.createQuery("select r from Rat r where r.username =:username and r.game=:game")
					.setParameter("username", rat.getUsername()).setParameter("game", rat.getGame()).getSingleResult();
			dbRat.setValue(rat.getValue());
		} catch (NoResultException e) {
			entityManager.persist(rat);
		}
	}

	@Override
	public double getAverageRat(String game) {

		return 0;
	}

}
