package com.chandra.myapp.repository.search;

import com.chandra.myapp.domain.Customerorder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Customerorder entity.
 */
public interface CustomerorderSearchRepository extends ElasticsearchRepository<Customerorder, Long> {
}
