package sk.tsystems.gamestudio.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.Comment;

@Component
@Transactional
public class CommentServiceJPA implements CommentService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addComment(Comment comment) {
		entityManager.persist(comment);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getComments(String game) {
		try {
			return (List<Comment>) entityManager
					.createQuery("select c from Comment c where c.game = :game order by c.ident desc")
					.setParameter("game", game).setMaxResults(10).getResultList();
		} catch (NoResultException e) {
			return null;
		}

//		return (List<Comment>)entityManager.createQuery("select c from comment c").setMaxResults(10).getResultList();
	}
}
