package dbfacades;

import entity.Customer;
import entity.OrderO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Facade {

    EntityManagerFactory emf;

    public Facade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Customer addCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
            return customer;
        } finally {
            em.close();
        }
    }

    public Customer findCustomerByMail(String mail) {
        EntityManager em = emf.createEntityManager();
        try {
            return (Customer) em.createQuery("SELECT c FROM Customer c WHERE c.email = :email").setParameter("email", mail).getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Customer> getAllCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Customer c").getResultList();
        } finally {
            em.close();
        }
    }

    public OrderO createOrder(OrderO order) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();
            return order;
        } finally {
            em.close();
        }
    }

    public OrderO addOrderToCustomer(Customer c, OrderO o) {
        EntityManager em = emf.createEntityManager();
        o.setCustomer(c);
        try {
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
            return o;
        } finally {
            em.close();
        }
    }

    public List<OrderO> getAllOrders() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT o FROM OrderO o").getResultList();
        } finally {
            em.close();
        }
    }

    public List<OrderO> findOrdersForCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT o FROM OrderO o WHERE o.customer.id = :id").setParameter("id", customer.getId()).getResultList();
        } finally {
            em.close();
        }
    }
}
