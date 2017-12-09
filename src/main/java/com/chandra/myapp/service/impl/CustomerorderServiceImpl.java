package com.chandra.myapp.service.impl;

import com.chandra.myapp.service.CustomerorderService;
import com.chandra.myapp.domain.Customerorder;
import com.chandra.myapp.repository.CustomerorderRepository;
import com.chandra.myapp.repository.search.CustomerorderSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Customerorder.
 */
@Service
@Transactional
public class CustomerorderServiceImpl implements CustomerorderService{

    private final Logger log = LoggerFactory.getLogger(CustomerorderServiceImpl.class);

    private final CustomerorderRepository customerorderRepository;

    private final CustomerorderSearchRepository customerorderSearchRepository;

    public CustomerorderServiceImpl(CustomerorderRepository customerorderRepository, CustomerorderSearchRepository customerorderSearchRepository) {
        this.customerorderRepository = customerorderRepository;
        this.customerorderSearchRepository = customerorderSearchRepository;
    }

    /**
     * Save a customerorder.
     *
     * @param customerorder the entity to save
     * @return the persisted entity
     */
    @Override
    public Customerorder save(Customerorder customerorder) {
        log.debug("Request to save Customerorder : {}", customerorder);
        Customerorder result = customerorderRepository.save(customerorder);
        customerorderSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the customerorders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Customerorder> findAll(Pageable pageable) {
        log.debug("Request to get all Customerorders");
        return customerorderRepository.findAll(pageable);
    }

    /**
     * Get one customerorder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Customerorder findOne(Long id) {
        log.debug("Request to get Customerorder : {}", id);
        return customerorderRepository.findOne(id);
    }

    /**
     * Delete the customerorder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customerorder : {}", id);
        customerorderRepository.delete(id);
        customerorderSearchRepository.delete(id);
    }

    /**
     * Search for the customerorder corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Customerorder> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Customerorders for query {}", query);
        Page<Customerorder> result = customerorderSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
