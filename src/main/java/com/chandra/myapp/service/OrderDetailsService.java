package com.chandra.myapp.service;

import com.chandra.myapp.domain.OrderDetails;
import java.util.List;

/**
 * Service Interface for managing OrderDetails.
 */
public interface OrderDetailsService {

    /**
     * Save a orderDetails.
     *
     * @param orderDetails the entity to save
     * @return the persisted entity
     */
    OrderDetails save(OrderDetails orderDetails);

    /**
     * Get all the orderDetails.
     *
     * @return the list of entities
     */
    List<OrderDetails> findAll();

    /**
     * Get the "id" orderDetails.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OrderDetails findOne(Long id);

    /**
     * Delete the "id" orderDetails.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orderDetails corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<OrderDetails> search(String query);
}
