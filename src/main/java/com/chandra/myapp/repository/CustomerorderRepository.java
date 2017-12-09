package com.chandra.myapp.repository;

import com.chandra.myapp.domain.Customerorder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Customerorder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerorderRepository extends JpaRepository<Customerorder, Long> {

}
