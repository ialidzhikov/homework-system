package bg.uni.sofia.fmi.homeworksystem.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import bg.uni.sofia.fmi.homeworksystem.cdi.EntityManagerProducer;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;

public class MakeDefaultUsers<T> {
	protected EntityManagerProducer emp = new EntityManagerProducer();
	private final EntityManager em = emp.getEntityManager();
	
	public void createUsers(@Observes @Initialized(ApplicationScoped.class) Object init) {
	  this.save(new Trainer("admin", getHashedPassword("admin"), "PhD", "Admin", ""));
	}
	
    private boolean save(final Trainer entity) {
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
    
	private String getHashedPassword(String password) {
		MessageDigest mda;
		try {
			mda = MessageDigest.getInstance("SHA-512");
			password = new String(mda.digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password;
	}
}
