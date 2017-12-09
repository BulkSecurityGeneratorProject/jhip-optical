package com.chandra.myapp.service;

import com.chandra.myapp.domain.Customer;
import java.util.List;

/**
 * Service Interface for managing Customer.
 */
public interface CustomerService {

    /**
     * Save a customer.
     *
     * @param customer the entity to save
     * @return the persisted entity
     */
    Customer save(Customer customer);

    /**
     * Get all the customers.
     *
     * @return the list of entities
     */
    List<Customer> findAll();

    /**
     * Get the "id" customer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Customer findOne(Long id);

    /**
     * Delete the "id" customer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the customer corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Customer> search(String query);
}
