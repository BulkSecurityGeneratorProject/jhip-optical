package com.chandra.myapp.web.rest;

import com.chandra.myapp.JhipsterApp;

import com.chandra.myapp.domain.OrderDetails;
import com.chandra.myapp.repository.OrderDetailsRepository;
import com.chandra.myapp.service.OrderDetailsService;
import com.chandra.myapp.repository.search.OrderDetailsSearchRepository;
import com.chandra.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.chandra.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderDetailsResource REST controller.
 *
 * @see OrderDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class OrderDetailsResourceIntTest {

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final Long DEFAULT_UNITPRICE = 1L;
    private static final Long UPDATED_UNITPRICE = 2L;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrderDetailsSearchRepository orderDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderDetailsMockMvc;

    private OrderDetails orderDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderDetailsResource orderDetailsResource = new OrderDetailsResource(orderDetailsService);
        this.restOrderDetailsMockMvc = MockMvcBuilders.standaloneSetup(orderDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderDetails createEntity(EntityManager em) {
        OrderDetails orderDetails = new OrderDetails()
            .quantity(DEFAULT_QUANTITY)
            .unitprice(DEFAULT_UNITPRICE);
        return orderDetails;
    }

    @Before
    public void initTest() {
        orderDetailsSearchRepository.deleteAll();
        orderDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderDetails() throws Exception {
        int databaseSizeBeforeCreate = orderDetailsRepository.findAll().size();

        // Create the OrderDetails
        restOrderDetailsMockMvc.perform(post("/api/order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDetails)))
            .andExpect(status().isCreated());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        OrderDetails testOrderDetails = orderDetailsList.get(orderDetailsList.size() - 1);
        assertThat(testOrderDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderDetails.getUnitprice()).isEqualTo(DEFAULT_UNITPRICE);

        // Validate the OrderDetails in Elasticsearch
        OrderDetails orderDetailsEs = orderDetailsSearchRepository.findOne(testOrderDetails.getId());
        assertThat(orderDetailsEs).isEqualToComparingFieldByField(testOrderDetails);
    }

    @Test
    @Transactional
    public void createOrderDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderDetailsRepository.findAll().size();

        // Create the OrderDetails with an existing ID
        orderDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderDetailsMockMvc.perform(post("/api/order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDetails)))
            .andExpect(status().isBadRequest());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        // Get all the orderDetailsList
        restOrderDetailsMockMvc.perform(get("/api/order-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unitprice").value(hasItem(DEFAULT_UNITPRICE.intValue())));
    }

    @Test
    @Transactional
    public void getOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        // Get the orderDetails
        restOrderDetailsMockMvc.perform(get("/api/order-details/{id}", orderDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderDetails.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.unitprice").value(DEFAULT_UNITPRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderDetails() throws Exception {
        // Get the orderDetails
        restOrderDetailsMockMvc.perform(get("/api/order-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsService.save(orderDetails);

        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();

        // Update the orderDetails
        OrderDetails updatedOrderDetails = orderDetailsRepository.findOne(orderDetails.getId());
        updatedOrderDetails
            .quantity(UPDATED_QUANTITY)
            .unitprice(UPDATED_UNITPRICE);

        restOrderDetailsMockMvc.perform(put("/api/order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderDetails)))
            .andExpect(status().isOk());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
        OrderDetails testOrderDetails = orderDetailsList.get(orderDetailsList.size() - 1);
        assertThat(testOrderDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderDetails.getUnitprice()).isEqualTo(UPDATED_UNITPRICE);

        // Validate the OrderDetails in Elasticsearch
        OrderDetails orderDetailsEs = orderDetailsSearchRepository.findOne(testOrderDetails.getId());
        assertThat(orderDetailsEs).isEqualToComparingFieldByField(testOrderDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderDetails() throws Exception {
        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();

        // Create the OrderDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderDetailsMockMvc.perform(put("/api/order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDetails)))
            .andExpect(status().isCreated());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsService.save(orderDetails);

        int databaseSizeBeforeDelete = orderDetailsRepository.findAll().size();

        // Get the orderDetails
        restOrderDetailsMockMvc.perform(delete("/api/order-details/{id}", orderDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean orderDetailsExistsInEs = orderDetailsSearchRepository.exists(orderDetails.getId());
        assertThat(orderDetailsExistsInEs).isFalse();

        // Validate the database is empty
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsService.save(orderDetails);

        // Search the orderDetails
        restOrderDetailsMockMvc.perform(get("/api/_search/order-details?query=id:" + orderDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unitprice").value(hasItem(DEFAULT_UNITPRICE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderDetails.class);
        OrderDetails orderDetails1 = new OrderDetails();
        orderDetails1.setId(1L);
        OrderDetails orderDetails2 = new OrderDetails();
        orderDetails2.setId(orderDetails1.getId());
        assertThat(orderDetails1).isEqualTo(orderDetails2);
        orderDetails2.setId(2L);
        assertThat(orderDetails1).isNotEqualTo(orderDetails2);
        orderDetails1.setId(null);
        assertThat(orderDetails1).isNotEqualTo(orderDetails2);
    }
}
