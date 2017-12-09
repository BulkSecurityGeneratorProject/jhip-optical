package com.chandra.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chandra.myapp.domain.OrderDetails;
import com.chandra.myapp.service.OrderDetailsService;
import com.chandra.myapp.web.rest.errors.BadRequestAlertException;
import com.chandra.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing OrderDetails.
 */
@RestController
@RequestMapping("/api")
public class OrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(OrderDetailsResource.class);

    private static final String ENTITY_NAME = "orderDetails";

    private final OrderDetailsService orderDetailsService;

    public OrderDetailsResource(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    /**
     * POST  /order-details : Create a new orderDetails.
     *
     * @param orderDetails the orderDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderDetails, or with status 400 (Bad Request) if the orderDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-details")
    @Timed
    public ResponseEntity<OrderDetails> createOrderDetails(@RequestBody OrderDetails orderDetails) throws URISyntaxException {
        log.debug("REST request to save OrderDetails : {}", orderDetails);
        if (orderDetails.getId() != null) {
            throw new BadRequestAlertException("A new orderDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderDetails result = orderDetailsService.save(orderDetails);
        return ResponseEntity.created(new URI("/api/order-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-details : Updates an existing orderDetails.
     *
     * @param orderDetails the orderDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderDetails,
     * or with status 400 (Bad Request) if the orderDetails is not valid,
     * or with status 500 (Internal Server Error) if the orderDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-details")
    @Timed
    public ResponseEntity<OrderDetails> updateOrderDetails(@RequestBody OrderDetails orderDetails) throws URISyntaxException {
        log.debug("REST request to update OrderDetails : {}", orderDetails);
        if (orderDetails.getId() == null) {
            return createOrderDetails(orderDetails);
        }
        OrderDetails result = orderDetailsService.save(orderDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-details : get all the orderDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderDetails in body
     */
    @GetMapping("/order-details")
    @Timed
    public List<OrderDetails> getAllOrderDetails() {
        log.debug("REST request to get all OrderDetails");
        return orderDetailsService.findAll();
        }

    /**
     * GET  /order-details/:id : get the "id" orderDetails.
     *
     * @param id the id of the orderDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderDetails, or with status 404 (Not Found)
     */
    @GetMapping("/order-details/{id}")
    @Timed
    public ResponseEntity<OrderDetails> getOrderDetails(@PathVariable Long id) {
        log.debug("REST request to get OrderDetails : {}", id);
        OrderDetails orderDetails = orderDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderDetails));
    }

    /**
     * DELETE  /order-details/:id : delete the "id" orderDetails.
     *
     * @param id the id of the orderDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long id) {
        log.debug("REST request to delete OrderDetails : {}", id);
        orderDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/order-details?query=:query : search for the orderDetails corresponding
     * to the query.
     *
     * @param query the query of the orderDetails search
     * @return the result of the search
     */
    @GetMapping("/_search/order-details")
    @Timed
    public List<OrderDetails> searchOrderDetails(@RequestParam String query) {
        log.debug("REST request to search OrderDetails for query {}", query);
        return orderDetailsService.search(query);
    }

}
