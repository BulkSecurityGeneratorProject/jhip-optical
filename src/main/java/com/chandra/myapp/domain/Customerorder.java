package com.chandra.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.chandra.myapp.domain.enumeration.PaymentType;

import com.chandra.myapp.domain.enumeration.OrderStatus;


/**
 * A Customerorder.
 */
@Entity
@Table(name = "customerorder")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customerorder")
public class Customerorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "orderdate")
    private Instant orderdate;

    @Column(name = "orderfullfilled")
    private Instant orderfullfilled;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentype")
    private PaymentType paymentype;

    @Column(name = "paymentdate")
    private Instant paymentdate;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "customerorder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderDetails> orderdetails = new HashSet<>();

    @ManyToOne
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Customerorder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getOrderdate() {
        return orderdate;
    }

    public Customerorder orderdate(Instant orderdate) {
        this.orderdate = orderdate;
        return this;
    }

    public void setOrderdate(Instant orderdate) {
        this.orderdate = orderdate;
    }

    public Instant getOrderfullfilled() {
        return orderfullfilled;
    }

    public Customerorder orderfullfilled(Instant orderfullfilled) {
        this.orderfullfilled = orderfullfilled;
        return this;
    }

    public void setOrderfullfilled(Instant orderfullfilled) {
        this.orderfullfilled = orderfullfilled;
    }

    public PaymentType getPaymentype() {
        return paymentype;
    }

    public Customerorder paymentype(PaymentType paymentype) {
        this.paymentype = paymentype;
        return this;
    }

    public void setPaymentype(PaymentType paymentype) {
        this.paymentype = paymentype;
    }

    public Instant getPaymentdate() {
        return paymentdate;
    }

    public Customerorder paymentdate(Instant paymentdate) {
        this.paymentdate = paymentdate;
        return this;
    }

    public void setPaymentdate(Instant paymentdate) {
        this.paymentdate = paymentdate;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public Customerorder totalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Customerorder orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Set<OrderDetails> getOrderdetails() {
        return orderdetails;
    }

    public Customerorder orderdetails(Set<OrderDetails> orderDetails) {
        this.orderdetails = orderDetails;
        return this;
    }

    public Customerorder addOrderdetails(OrderDetails orderDetails) {
        this.orderdetails.add(orderDetails);
        orderDetails.setCustomerorder(this);
        return this;
    }

    public Customerorder removeOrderdetails(OrderDetails orderDetails) {
        this.orderdetails.remove(orderDetails);
        orderDetails.setCustomerorder(null);
        return this;
    }

    public void setOrderdetails(Set<OrderDetails> orderDetails) {
        this.orderdetails = orderDetails;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Customerorder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        Customerorder customerorder = (Customerorder) o;
        if (customerorder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerorder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customerorder{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", orderdate='" + getOrderdate() + "'" +
            ", orderfullfilled='" + getOrderfullfilled() + "'" +
            ", paymentype='" + getPaymentype() + "'" +
            ", paymentdate='" + getPaymentdate() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", orderStatus='" + getOrderStatus() + "'" +
            "}";
    }
}
