package lu.uni.jakartaee.jpa;

import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.*;
import jakarta.transaction.*;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.*;
import org.apache.logging.log4j.*;

@Named("customer")
@SessionScoped
@Entity(name = "customer")
@Transactional
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "customername")
    private String name;
    private String email;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List <Order> placedOrders;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "visits_customer_store",
            joinColumns = @JoinColumn(name = "id_customer", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_store", referencedColumnName = "id"))
    private Set <Store> visitedStores;

    @PersistenceContext(unitName = "JPA-Example")
    @Transient private transient EntityManager em;

    @Transient private static final Logger logger = LogManager.getLogger ( Customer.class );

    public String add ( ) {
        try {
            em.merge ( new Customer(id, name, email));
        } catch (Exception e) {
            logger.error ("Insert of customer failed: %s", e.getMessage() );
        }
        return "";
    }

    public String findById ( ) {
        try {
            Customer c = em.find ( Customer.class, this.id );
            if (c != null) {
                this.name = c.name;
                this.email = c.email;
                this.placedOrders = c.placedOrders;
                this.visitedStores = c.visitedStores;
                this.visitedStores.size ();          // trigger loading of list
            } else {
                this.name = this.email = "";
                this.placedOrders = new LinkedList <> ();
                this.visitedStores = new HashSet <> ();
            }
        } catch (Exception e) {
            logger.error ( "Search for Customer object crashed with exception - did you already load the dump file into DB?" );
        }

        return "";
    }

    public Customer ( ) {
        this.name = "";
        this.email = "";
        this.placedOrders = null;
    }

    public Customer ( Long id, String name, String email ) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.placedOrders = null;
    }

    public void init ( ) {
        this.id = null;
        this.name = "";
        this.email = "";
        this.placedOrders = null;
    }

    public Long getId ( ) {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    @Override
    public int hashCode ( ) {
        int hash = 0;
        hash += (id != null ? id.hashCode () : 0);
        return hash;
    }

    @Override
    public boolean equals (Object object) {
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        return (this.id != null || other.id == null)
                && (this.id == null || this.id.equals ( other.id ));
    }

    public String getName ( ) {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Set <Store> getVisitedStores ( ) {
        return visitedStores;
    }

    public String getEmail ( ) {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public List <Order> getPlacedOrders ( ) {
        return placedOrders;
    }

    public void setPlacedOrders (List <Order> placedOrders) {
        this.placedOrders = placedOrders;
    }

    @Override
    public String toString ( ) {
        return "lu.uni.Customer[ id=" + id + " ]";
    }
}
