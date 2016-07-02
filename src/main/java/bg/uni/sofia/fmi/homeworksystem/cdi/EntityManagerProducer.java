package bg.uni.sofia.fmi.homeworksystem.cdi;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates and closes entity managers.
 */
@ApplicationScoped
public class EntityManagerProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityManagerProducer.class);
    private static EntityManagerFactory emf;
    
    @Produces
    @RequestScoped
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public EntityManager getEntityManager() {
        if (emf == null) {
            try {
                final InitialContext initialContext = new InitialContext();
                final DataSource ds = (DataSource) initialContext
                        .lookup("java:comp/env/jdbc/DefaultDB");

                LOGGER.debug("The Entity Manager Factory is null.");
                final Map properties = new HashMap();
                properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
                emf = Persistence.createEntityManagerFactory("homeworksystem", properties);

            } catch (final Exception e) {
                LOGGER.error("Message: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return emf.createEntityManager();
    }
    
	public void closeEntityManager(@Disposes EntityManager em) {
		if(em.isOpen()) {
			em.close();
		}
	}
}
