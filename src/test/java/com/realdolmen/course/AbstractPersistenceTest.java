package com.realdolmen.course;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public abstract class AbstractPersistenceTest {
    protected static EntityManagerFactory emf;

    protected EntityManager em;

    @BeforeClass
    public static void initializeEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("CandyShopPersistenceUnit");
    }

    @Before
    public void initializeEntityManagerWithTransaction() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @After
    public void rollbackTransactionAndCloseEntityManager() {
        if(em != null) {
            em.getTransaction().rollback();
            em.close();
        }
    }

    @AfterClass
    public static void destroyEntityManagerFactory() {
        if(emf != null) {
            emf.close();
        }
    }

    protected void flushAndClear() {
        em.flush();
        em.clear();
    }

    protected <T> long count(Class<T> entityClass) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(entityClass)));
        return em.createQuery(query).getSingleResult();
    }
}
