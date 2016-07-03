package bg.uni.sofia.fmi.homeworksystem.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

public abstract class AbstractDAO<T> {
	
	@Inject
    protected EntityManager em;

    /**
     * saves an entity to the database.
     * @param entity which has to be saved to the database
     * @return if the current entity is added to the database.
     */
    public boolean save(final T entity) {
    	EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(entity);
        try {
            transaction.commit();
        } catch (final RollbackException re) {
        	if (transaction.isActive()) {
        		transaction.rollback();
        	}
        	
            return false;
        }
        
        return true;
    }

    /**
     * updates an entity which exists in the database.
     * @param entity which has to be updated.
     * @return if the current entity is updated.
     */
    public boolean update(final T entity) {
    	EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(entity);
        try {
            transaction.commit();
        } catch (final RollbackException re) {
        	if (transaction.isActive()) {
        		transaction.rollback();
        	}
        	
            return false;
        }
        
        return true;
    }

    /**
     * deletes an entity from the database.
     * @param entity which has to be deleted from the database
     * @return if the current entity is deleted from the database.
     */
    public boolean delete(final T entity) {
    	EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(entity);
        try {
            em.getTransaction().commit();
        } catch (final RollbackException re) {
        	if (transaction.isActive()) {
        		transaction.rollback();
        	}
        	
            return false;
        }
        
        return true;
    }

    /**
     * gets an entity from the database by its ID.
     * @param entity Id
     * @return Found entity.
     */
    public T getById(Class<T> entityClass, final long id) {
        return em.find(entityClass, id);
    }
}