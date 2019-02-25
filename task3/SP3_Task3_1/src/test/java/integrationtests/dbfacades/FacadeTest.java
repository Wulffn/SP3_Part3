package integrationtests.dbfacades;


import dbfacades.Facade;
import entity.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/* 
  This is added to demonstrate how you could do integration tests.
  Right now its just a copy of the UNIT tests, which makes sense if you repeat the tests with
  another (real) database.
  Setting up with a real database is considered a RED topic this semester
 */
public class FacadeTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-test", null);
//EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu", null);

    Facade facade = new Facade(emf);

    /**
     * Setup test data in the database to a known state BEFORE Each test
     */
    @Before
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete all, since some future test cases might add/change data
            em.createQuery("delete from Customer").executeUpdate();
            //Add our test data
            Customer c1 = new Customer("Kurt", "Hansen");
            Customer c2 = new Customer("Hans", "Jensen");
            Customer c3 = new Customer("Bjarne", "Olsen");
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Test the single method in the Facade
    @Test
    public void testAddCustomer() {
        EntityManager em = emf.createEntityManager();
        try {
            facade.addCustomer(new Customer("Helle", "Andersen"));
            long count = em.createQuery("select c from Customer c").getResultList().size();
            Assert.assertEquals(4, count);
        } finally {
            em.close();
        }
    }
}
