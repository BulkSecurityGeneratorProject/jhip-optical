package com.chandra.myapp.web.rest;

import com.chandra.myapp.JhipsterApp;

import com.chandra.myapp.domain.Customerorder;
import com.chandra.myapp.repository.CustomerorderRepository;
import com.chandra.myapp.service.CustomerorderService;
import com.chandra.myapp.repository.search.CustomerorderSearchRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.chandra.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chandra.myapp.domain.enumeration.PaymentType;
import com.chandra.myapp.domain.enumeration.OrderStatus;
/**
 * Test class for the CustomerorderResource REST controller.
 *
 * @see CustomerorderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class CustomerorderResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_ORDERDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDERDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ORDERFULLFILLED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDERFULLFILLED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final PaymentType DEFAULT_PAYMENTYPE = PaymentType.CREDITCARD;
    private static final PaymentType UPDATED_PAYMENTYPE = PaymentType.CASH;

    private static final Instant DEFAULT_PAYMENTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_TOTAL_AMOUNT = 1L;
    private static final Long UPDATED_TOTAL_AMOUNT = 2L;

    private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.NEW;
    private static final OrderStatus UPDATED_ORDER_STATUS = OrderStatus.INPROGESS;

    @Autowired
    private CustomerorderRepository customerorderRepository;

    @Autowired
    private CustomerorderService customerorderService;

    @Autowired
    private CustomerorderSearchRepository customerorderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerorderMockMvc;

    private Customerorder customerorder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerorderResource customerorderResource = new CustomerorderResource(customerorderService);
        this.restCustomerorderMockMvc = MockMvcBuilders.standaloneSetup(customerorderResource)
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
    public static Customerorder createEntity(EntityManager em) {
        Customerorder customerorder = new Customerorder()
            .description(DEFAULT_DESCRIPTION)
            .orderdate(DEFAULT_ORDERDATE)
            .orderfullfilled(DEFAULT_ORDERFULLFILLED)
            .paymentype(DEFAULT_PAYMENTYPE)
            .paymentdate(DEFAULT_PAYMENTDATE)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .orderStatus(DEFAULT_ORDER_STATUS);
        return customerorder;
    }

    @Before
    public void initTest() {
        customerorderSearchRepository.deleteAll();
        customerorder = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerorder() throws Exception {
        int databaseSizeBeforeCreate = customerorderRepository.findAll().size();

        // Create the Customerorder
        restCustomerorderMockMvc.perform(post("/api/customerorders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerorder)))
            .andExpect(status().isCreated());

        // Validate the Customerorder in the database
        List<Customerorder> customerorderList = customerorderRepository.findAll();
        assertThat(customerorderList).hasSize(databaseSizeBeforeCreate + 1);
        Customerorder testCustomerorder = customerorderList.get(customerorderList.size() - 1);
        assertThat(testCustomerorder.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomerorder.getOrderdate()).isEqualTo(DEFAULT_ORDERDATE);
        assertThat(testCustomerorder.getOrderfullfilled()).isEqualTo(DEFAULT_ORDERFULLFILLED);
        assertThat(testCustomerorder.getPaymentype()).isEqualTo(DEFAULT_PAYMENTYPE);
        assertThat(testCustomerorder.getPaymentdate()).isEqualTo(DEFAULT_PAYMENTDATE);
        assertThat(testCustomerorder.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testCustomerorder.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);

        // Validate the Customerorder in Elasticsearch
        Customerorder customerorderEs = customerorderSearchRepository.findOne(testCustomerorder.getId());
        assertThat(customerorderEs).isEqualToComparingFieldByField(testCustomerorder);
    }

    @Test
    @Transactional
    public void createCustomerorderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerorderRepository.findAll().size();

        // Create the Customerorder with an existing ID
        customerorder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerorderMockMvc.perform(post("/api/customerorders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerorder)))
            .andExpect(status().isBadRequest());

        // Validate the Customerorder in the database
        List<Customerorder> customerorderList = customerorderRepository.findAll();
        assertThat(customerorderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerorders() throws Exception {
        // Initialize the database
        customerorderRepository.saveAndFlush(customerorder);

        // Get all the customerorderList
        restCustomerorderMockMvc.perform(get("/api/customerorders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerorder.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].orderdate").value(hasItem(DEFAULT_ORDERDATE.toString())))
            .andExpect(jsonPath("$.[*].orderfullfilled").value(hasItem(DEFAULT_ORDERFULLFILLED.toString())))
            .andExpect(jsonPath("$.[*].paymentype").value(hasItem(DEFAULT_PAYMENTYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentdate").value(hasItem(DEFAULT_PAYMENTDATE.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getCustomerorder() throws Exception {
        // Initialize the database
        customerorderRepository.saveAndFlush(customerorder);

        // Get the customerorder
        restCustomerorderMockMvc.perform(get("/api/customerorders/{id}", customerorder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerorder.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.orderdate").value(DEFAULT_ORDERDATE.toString()))
            .andExpect(jsonPath("$.orderfullfilled").value(DEFAULT_ORDERFULLFILLED.toString()))
            .andExpect(jsonPath("$.paymentype").value(DEFAULT_PAYMENTYPE.toString()))
            .andExpect(jsonPath("$.paymentdate").value(DEFAULT_PAYMENTDATE.toString()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerorder() throws Exception {
        // Get the customerorder
        restCustomerorderMockMvc.perform(get("/api/customerorders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerorder() throws Exception {
        // Initialize the database
        customerorderService.save(customerorder);

        int databaseSizeBeforeUpdate = customerorderRepository.findAll().size();

        // Update the customerorder
        Customerorder updatedCustomerorder = customerorderRepository.findOne(customerorder.getId());
        updatedCustomerorder
            .description(UPDATED_DESCRIPTION)
            .orderdate(UPDATED_ORDERDATE)
            .orderfullfilled(UPDATED_ORDERFULLFILLED)
            .paymentype(UPDATED_PAYMENTYPE)
            .paymentdate(UPDATED_PAYMENTDATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .orderStatus(UPDATED_ORDER_STATUS);

        restCustomerorderMockMvc.perform(put("/api/customerorders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerorder)))
            .andExpect(status().isOk());

        // Validate the Customerorder in the database
        List<Customerorder> customerorderList = customerorderRepository.findAll();
        assertThat(customerorderList).hasSize(databaseSizeBeforeUpdate);
        Customerorder testCustomerorder = customerorderList.get(customerorderList.size() - 1);
        assertThat(testCustomerorder.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomerorder.getOrderdate()).isEqualTo(UPDATED_ORDERDATE);
        assertThat(testCustomerorder.getOrderfullfilled()).isEqualTo(UPDATED_ORDERFULLFILLED);
        assertThat(testCustomerorder.getPaymentype()).isEqualTo(UPDATED_PAYMENTYPE);
        assertThat(testCustomerorder.getPaymentdate()).isEqualTo(UPDATED_PAYMENTDATE);
        assertThat(testCustomerorder.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testCustomerorder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);

        // Validate the Customerorder in Elasticsearch
        Customerorder customerorderEs = customerorderSearchRepository.findOne(testCustomerorder.getId());
        assertThat(customerorderEs).isEqualToComparingFieldByField(testCustomerorder);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerorder() throws Exception {
        int databaseSizeBeforeUpdate = customerorderRepository.findAll().size();

        // Create the Customerorder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerorderMockMvc.perform(put("/api/customerorders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerorder)))
            .andExpect(status().isCreated());

        // Validate the Customerorder in the database
        List<Customerorder> customerorderList = customerorderRepository.findAll();
        assertThat(customerorderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerorder() throws Exception {
        // Initialize the database
        customerorderService.save(customerorder);

        int databaseSizeBeforeDelete = customerorderRepository.findAll().size();

        // Get the customerorder
        restCustomerorderMockMvc.perform(delete("/api/customerorders/{id}", customerorder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean customerorderExistsInEs = customerorderSearchRepository.exists(customerorder.getId());
        assertThat(customerorderExistsInEs).isFalse();

        // Validate the database is empty
        List<Customerorder> customerorderList = customerorderRepository.findAll();
        assertThat(customerorderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerorder() throws Exception {
        // Initialize the database
        customerorderService.save(customerorder);

        // Search the customerorder
        restCustomerorderMockMvc.perform(get("/api/_search/customerorders?query=id:" + customerorder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerorder.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].orderdate").value(hasItem(DEFAULT_ORDERDATE.toString())))
            .andExpect(jsonPath("$.[*].orderfullfilled").value(hasItem(DEFAULT_ORDERFULLFILLED.toString())))
            .andExpect(jsonPath("$.[*].paymentype").value(hasItem(DEFAULT_PAYMENTYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentdate").value(hasItem(DEFAULT_PAYMENTDATE.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customerorder.class);
        Customerorder customerorder1 = new Customerorder();
        customerorder1.setId(1L);
        Customerorder customerorder2 = new Customerorder();
        customerorder2.setId(customerorder1.getId());
        assertThat(customerorder1).isEqualTo(customerorder2);
        customerorder2.setId(2L);
        assertThat(customerorder1).isNotEqualTo(customerorder2);
        customerorder1.setId(null);
        assertThat(customerorder1).isNotEqualTo(customerorder2);
    }
}
