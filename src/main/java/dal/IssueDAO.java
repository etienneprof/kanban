package dal;

import java.util.List;

import bo.Issue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class IssueDAO {
private EntityManagerFactory emf;
	
	public IssueDAO() {
		emf = Persistence.createEntityManagerFactory("user");
	}
	
	public List<Issue> select() {
		EntityManager em = emf.createEntityManager();
		List<Issue> result = em.createQuery("from Issue", Issue.class).getResultList();
		em.close();
		return result;
	}

	public Issue selectById(int id) {
		EntityManager em = emf.createEntityManager();
		Issue result = em.find(Issue.class, id);
		em.close();
		return result;
	}

	public void insert(Issue issue) {
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		try {
			em.persist(issue);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
	}

	public void update(Issue issue) {
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		try {
			em.merge(issue);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
	}
	
	public void updateColumn(int idIssue, int idColumn) {
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		try {
			em.createNativeQuery("UPDATE issues SET column_id = :column WHERE id = :id")
				.setParameter("column", idColumn)
				.setParameter("id", idIssue)
				.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
	}

	public void delete(Issue issue) {
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		try {
			em.remove(em.merge(issue));
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
	}
}
