package com.chandra.myapp.service;

import com.chandra.myapp.domain.Product;
import java.util.List;

/**
 * Service Interface for managing Product.
 */
public interface ProductService {

    /**
     * Save a product.
     *
     * @param product the entity to save
     * @return the persisted entity
     */
    Product save(Product product);

    /**
     * Get all the products.
     *
     * @return the list of entities
     */
    List<Product> findAll();

    /**
     * Get the "id" product.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Product findOne(Long id);

    /**
     * Delete the "id" product.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the product corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Product> search(String query);
}
