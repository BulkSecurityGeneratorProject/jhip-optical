package com.chandra.myapp.repository.search;

import com.chandra.myapp.domain.OrderDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderDetails entity.
 */
public interface OrderDetailsSearchRepository extends ElasticsearchRepository<OrderDetails, Long> {
}
