package com.sl.ms.ordermanagement.repository;

import com.sl.ms.ordermanagement.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Orders,Integer> {
}
