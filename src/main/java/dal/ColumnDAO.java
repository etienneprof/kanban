package dal;

import java.util.List;

import bo.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ColumnDAO {
private EntityManagerFactory emf;
	
	public ColumnDAO() {
		emf = Persistence.createEntityManagerFactory("user");
	}
	
	public List<Column> select() {
		EntityManager em = emf.createEntityManager();
		List<Column> result = em.createQuery("from Column", Column.class).getResultList();
		em.close();
		return result;
	}

	public Column selectById(int id) {
		EntityManager em = emf.createEntityManager();
		Column result = em.find(Column.class, id);
		em.close();
		return result;
	}

	public void insert(Column column) {
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		try {
			em.persist(column);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
	}

	public void update(Column column) {
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		try {
			em.merge(column);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
	}

	public void delete(Column column) {
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		try {
			em.remove(em.merge(column));
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
	}
}
