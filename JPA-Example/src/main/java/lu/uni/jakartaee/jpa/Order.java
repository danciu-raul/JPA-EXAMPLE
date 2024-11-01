package lu.uni.jakartaee.jpa;

import jakarta.ejb.Stateful;
import jakarta.persistence.*;

import java.io.Serializable;

@Stateful
@Entity(name="ordertbl")
public class Order implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   private String description;
   private Float price;

   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(name="customer_id")
   private Customer customer;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @Override
   public int hashCode() {
      int hash = 0;
      hash += (id != null ? id.hashCode() : 0);
      return hash;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof Order)) {
         return false;
      }
      Order other = (Order) object;
      return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Float getPrice() {
      return price;
   }

   public void setPrice(Float price) {
      this.price = price;
   }

   @Override
   public String toString() {
      return "lu.uni.Order[ id=" + id + " ]" + customer.toString();
   }
}
