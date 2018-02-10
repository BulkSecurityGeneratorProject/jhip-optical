package com.chandra.myapp.repository.search;

import com.chandra.myapp.domain.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Spring Data Elasticsearch repository for the Customer entity.
 */
public interface CustomerSearchRepository extends ElasticsearchRepository<Customer, Long> {

    List<Customer> findByNameLikeOrPhonenumberLike(String name,String phonenumber);
}
