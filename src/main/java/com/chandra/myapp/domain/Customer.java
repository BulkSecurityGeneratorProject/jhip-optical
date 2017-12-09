package com.chandra.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "age")
    private Integer age;

    @Column(name = "cylindrical")
    private String cylindrical;

    @Column(name = "spherical")
    private String spherical;

    @Column(name = "power")
    private String power;

    @Column(name = "longsight")
    private String longsight;

    @Column(name = "shortsight")
    private String shortsight;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Customerorder> customerorders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public Customer phonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return this;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Integer getAge() {
        return age;
    }

    public Customer age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCylindrical() {
        return cylindrical;
    }

    public Customer cylindrical(String cylindrical) {
        this.cylindrical = cylindrical;
        return this;
    }

    public void setCylindrical(String cylindrical) {
        this.cylindrical = cylindrical;
    }

    public String getSpherical() {
        return spherical;
    }

    public Customer spherical(String spherical) {
        this.spherical = spherical;
        return this;
    }

    public void setSpherical(String spherical) {
        this.spherical = spherical;
    }

    public String getPower() {
        return power;
    }

    public Customer power(String power) {
        this.power = power;
        return this;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getLongsight() {
        return longsight;
    }

    public Customer longsight(String longsight) {
        this.longsight = longsight;
        return this;
    }

    public void setLongsight(String longsight) {
        this.longsight = longsight;
    }

    public String getShortsight() {
        return shortsight;
    }

    public Customer shortsight(String shortsight) {
        this.shortsight = shortsight;
        return this;
    }

    public void setShortsight(String shortsight) {
        this.shortsight = shortsight;
    }

    public String getAddress() {
        return address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Customerorder> getCustomerorders() {
        return customerorders;
    }

    public Customer customerorders(Set<Customerorder> customerorders) {
        this.customerorders = customerorders;
        return this;
    }

    public Customer addCustomerorder(Customerorder customerorder) {
        this.customerorders.add(customerorder);
        customerorder.setCustomer(this);
        return this;
    }

    public Customer removeCustomerorder(Customerorder customerorder) {
        this.customerorders.remove(customerorder);
        customerorder.setCustomer(null);
        return this;
    }

    public void setCustomerorders(Set<Customerorder> customerorders) {
        this.customerorders = customerorders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        if (customer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phonenumber='" + getPhonenumber() + "'" +
            ", age=" + getAge() +
            ", cylindrical='" + getCylindrical() + "'" +
            ", spherical='" + getSpherical() + "'" +
            ", power='" + getPower() + "'" +
            ", longsight='" + getLongsight() + "'" +
            ", shortsight='" + getShortsight() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
