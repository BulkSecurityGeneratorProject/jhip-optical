package com.chandra.myapp.service.impl;

import com.chandra.myapp.service.OrderDetailsService;
import com.chandra.myapp.domain.OrderDetails;
import com.chandra.myapp.repository.OrderDetailsRepository;
import com.chandra.myapp.repository.search.OrderDetailsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OrderDetails.
 */
@Service
@Transactional
public class OrderDetailsServiceImpl implements OrderDetailsService{

    private final Logger log = LoggerFactory.getLogger(OrderDetailsServiceImpl.class);

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderDetailsSearchRepository orderDetailsSearchRepository;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository, OrderDetailsSearchRepository orderDetailsSearchRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderDetailsSearchRepository = orderDetailsSearchRepository;
    }

    /**
     * Save a orderDetails.
     *
     * @param orderDetails the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        log.debug("Request to save OrderDetails : {}", orderDetails);
        OrderDetails result = orderDetailsRepository.save(orderDetails);
        orderDetailsSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the orderDetails.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderDetails> findAll() {
        log.debug("Request to get all OrderDetails");
        return orderDetailsRepository.findAll();
    }

    /**
     * Get one orderDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrderDetails findOne(Long id) {
        log.debug("Request to get OrderDetails : {}", id);
        return orderDetailsRepository.findOne(id);
    }

    /**
     * Delete the orderDetails by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderDetails : {}", id);
        orderDetailsRepository.delete(id);
        orderDetailsSearchRepository.delete(id);
    }

    /**
     * Search for the orderDetails corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderDetails> search(String query) {
        log.debug("Request to search OrderDetails for query {}", query);
        return StreamSupport
            .stream(orderDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
