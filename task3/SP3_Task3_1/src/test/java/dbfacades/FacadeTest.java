package dbfacades;

import dbfacades.Facade;
import entity.Customer;
import entity.OrderO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * UNIT TEST example that mocks away the database with an in-memory database See
 * Persistence unit in persistence.xml in the test packages
 *
 * Use this in your own project by: - Delete everything inside the setUp method,
 * but first, observe how test data is created - Delete the single test method,
 * and replace with your own tests
 *
 */
public class FacadeTest {

//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-test", null);
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu", null);

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
            em.createQuery("delete from OrderO").executeUpdate();
            em.createQuery("delete from Customer").executeUpdate();
            //Add our test data
            Customer c1 = new Customer("Kurt Hansen", "kurt@hansen.dk");
            Customer c2 = new Customer("Hans Jensen", "hans@jensen.dk");
            Customer c3 = new Customer("Bjarne Olsen", "bjarne@olsen.dk");
            OrderO o1 = new OrderO();
            OrderO o2 = new OrderO();
            OrderO o3 = new OrderO();
            o2.setCustomer(c3);
            o3.setCustomer(c3);
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(o1);
            em.persist(o2);
            em.persist(o3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetAllOrders() {
        List<OrderO> orders = facade.getAllOrders();
        Assert.assertEquals(3, orders.size());
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = facade.getAllCustomers();
        Assert.assertEquals(3, customers.size());
    }

    @Test
    public void createOrder() {
        facade.createOrder(new OrderO());
        Assert.assertEquals(4, facade.getAllOrders().size());
    }

    @Test
    public void testAddCustomer() {
        facade.addCustomer(new Customer("Helle", "Andersen"));
        long count = facade.getAllCustomers().size();
        Assert.assertEquals(4, count);
    }

    @Test
    public void testFindCustomerByMail() {
        Customer c = facade.findCustomerByMail("hans@jensen.dk");
        Assert.assertEquals("Hans Jensen", c.getName());
    }

    @Test
    public void testAddOrderToCustomer() {
        Customer c = facade.findCustomerByMail("bjarne@olsen.dk");
        OrderO o = facade.addOrderToCustomer(c, new OrderO());
        Assert.assertEquals(c.getId(), o.getCustomer().getId());
    }

    @Test
    public void testFindOrdersForCustomer() {
        List<OrderO> orders = facade.findOrdersForCustomer(facade.findCustomerByMail("bjarne@olsen.dk"));
        Assert.assertEquals(2, orders.size());
    }
    
}
