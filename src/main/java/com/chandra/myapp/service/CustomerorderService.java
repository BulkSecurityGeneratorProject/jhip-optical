package com.chandra.myapp.service;

import com.chandra.myapp.domain.Customerorder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Customerorder.
 */
public interface CustomerorderService {

    /**
     * Save a customerorder.
     *
     * @param customerorder the entity to save
     * @return the persisted entity
     */
    Customerorder save(Customerorder customerorder);

    /**
     * Get all the customerorders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Customerorder> findAll(Pageable pageable);

    /**
     * Get the "id" customerorder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Customerorder findOne(Long id);

    /**
     * Delete the "id" customerorder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the customerorder corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Customerorder> search(String query, Pageable pageable);
}
