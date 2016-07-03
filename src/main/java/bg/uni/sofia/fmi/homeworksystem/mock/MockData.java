package bg.uni.sofia.fmi.homeworksystem.mock;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import bg.uni.sofia.fmi.homeworksystem.cdi.EntityManagerProducer;
import bg.uni.sofia.fmi.homeworksystem.contracts.EntityObject;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;

public class MockData {
	private EntityManagerProducer emp = new EntityManagerProducer();
	private final EntityManager em = emp.getEntityManager();
	
	public void createUsers(@Observes @Initialized(ApplicationScoped.class) Object init) {
		Trainer admin = new Trainer("admin", getHashedPassword("admin"), "PhD", "Admin", "");
		Trainer stoyo = new Trainer("Stoyo", getHashedPassword("stoyo"), "PhD", "Stoyan Vellev", "iliqnvidenov92@gmail.com");
		Trainer ismail = new Trainer("Ismail", getHashedPassword("1"), "PhD" ,"Ismail Alidzhikov", "i.alidjikov@gmail.com");
		Trainee iliyan = new Trainee("Iliyan", getHashedPassword("1"), "Iliyan Videnov", "iliqnvidenov92@gmail.com", "Computer Science");
		Trainee hristo = new Trainee("Hristo", getHashedPassword("1"), "Iliyan Videnov", "hristokirilov7@gmail.com", "Computer Science");
		this.save(admin);
		this.save(stoyo);
		this.save(ismail);
		this.save(iliyan);
		this.save(hristo);
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
	
    private boolean save(final EntityObject entity) {
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
}
