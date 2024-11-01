package lu.uni.jakartaee.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity(name="store")
public class Store implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private Long id;
  private String location;

  
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

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
    if (!(object instanceof Store)) {
      return false;
    }
    Store other = (Store) object;
    return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
  }

  @Override
  public String toString() {
    return "lu.uni.Store[ id=" + id + " ]";
  }
}
