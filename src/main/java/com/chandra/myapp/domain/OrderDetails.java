package com.chandra.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A OrderDetails.
 */
@Entity
@Table(name = "order_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderdetails")
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "unitprice")
    private Long unitprice;

    @ManyToOne
    private Customerorder customerorder;

    @ManyToOne
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public OrderDetails quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getUnitprice() {
        return unitprice;
    }

    public OrderDetails unitprice(Long unitprice) {
        this.unitprice = unitprice;
        return this;
    }

    public void setUnitprice(Long unitprice) {
        this.unitprice = unitprice;
    }

    public Customerorder getCustomerorder() {
        return customerorder;
    }

    public OrderDetails customerorder(Customerorder customerorder) {
        this.customerorder = customerorder;
        return this;
    }

    public void setCustomerorder(Customerorder customerorder) {
        this.customerorder = customerorder;
    }

    public Product getProduct() {
        return product;
    }

    public OrderDetails product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        OrderDetails orderDetails = (OrderDetails) o;
        if (orderDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unitprice=" + getUnitprice() +
            "}";
    }
}
