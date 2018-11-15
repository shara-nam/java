package com.csdm.newsfeed;

import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Service
public class DatabaseSessionManager {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    /**
     * Binds a EntityManager Session to the current Thread
     *
     * @return true on success
     *
     */
    public boolean bindSession() {
        if (!TransactionSynchronizationManager.hasResource(entityManagerFactory)) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            TransactionSynchronizationManager.bindResource(entityManagerFactory,
                    new EntityManagerHolder(entityManager));
            return true;
        }
        return false;
    }

    /**
     * Unbinds a EntityManager Session from the current Thread
     *
     * @return true on success
     */
    public void unbindSession() {
        EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager
                .unbindResource(entityManagerFactory);
        EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
    }
}