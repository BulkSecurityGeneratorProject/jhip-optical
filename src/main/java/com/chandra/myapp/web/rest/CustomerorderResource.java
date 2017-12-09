package com.chandra.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chandra.myapp.domain.Customerorder;
import com.chandra.myapp.service.CustomerorderService;
import com.chandra.myapp.web.rest.errors.BadRequestAlertException;
import com.chandra.myapp.web.rest.util.HeaderUtil;
import com.chandra.myapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Customerorder.
 */
@RestController
@RequestMapping("/api")
public class CustomerorderResource {

    private final Logger log = LoggerFactory.getLogger(CustomerorderResource.class);

    private static final String ENTITY_NAME = "customerorder";

    private final CustomerorderService customerorderService;

    public CustomerorderResource(CustomerorderService customerorderService) {
        this.customerorderService = customerorderService;
    }

    /**
     * POST  /customerorders : Create a new customerorder.
     *
     * @param customerorder the customerorder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerorder, or with status 400 (Bad Request) if the customerorder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customerorders")
    @Timed
    public ResponseEntity<Customerorder> createCustomerorder(@RequestBody Customerorder customerorder) throws URISyntaxException {
        log.debug("REST request to save Customerorder : {}", customerorder);
        if (customerorder.getId() != null) {
            throw new BadRequestAlertException("A new customerorder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Customerorder result = customerorderService.save(customerorder);
        return ResponseEntity.created(new URI("/api/customerorders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customerorders : Updates an existing customerorder.
     *
     * @param customerorder the customerorder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerorder,
     * or with status 400 (Bad Request) if the customerorder is not valid,
     * or with status 500 (Internal Server Error) if the customerorder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customerorders")
    @Timed
    public ResponseEntity<Customerorder> updateCustomerorder(@RequestBody Customerorder customerorder) throws URISyntaxException {
        log.debug("REST request to update Customerorder : {}", customerorder);
        if (customerorder.getId() == null) {
            return createCustomerorder(customerorder);
        }
        Customerorder result = customerorderService.save(customerorder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerorder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customerorders : get all the customerorders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customerorders in body
     */
    @GetMapping("/customerorders")
    @Timed
    public ResponseEntity<List<Customerorder>> getAllCustomerorders(Pageable pageable) {
        log.debug("REST request to get a page of Customerorders");
        Page<Customerorder> page = customerorderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customerorders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customerorders/:id : get the "id" customerorder.
     *
     * @param id the id of the customerorder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerorder, or with status 404 (Not Found)
     */
    @GetMapping("/customerorders/{id}")
    @Timed
    public ResponseEntity<Customerorder> getCustomerorder(@PathVariable Long id) {
        log.debug("REST request to get Customerorder : {}", id);
        Customerorder customerorder = customerorderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerorder));
    }

    /**
     * DELETE  /customerorders/:id : delete the "id" customerorder.
     *
     * @param id the id of the customerorder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customerorders/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerorder(@PathVariable Long id) {
        log.debug("REST request to delete Customerorder : {}", id);
        customerorderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/customerorders?query=:query : search for the customerorder corresponding
     * to the query.
     *
     * @param query the query of the customerorder search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/customerorders")
    @Timed
    public ResponseEntity<List<Customerorder>> searchCustomerorders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Customerorders for query {}", query);
        Page<Customerorder> page = customerorderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/customerorders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
