package com.ibm.mateusmelo.domain.repository;

import com.ibm.mateusmelo.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Clients extends JpaRepository<Client, Integer> {
    @Query(value = "SELECT c FROM Client c LEFT JOIN FETCH c.orders WHERE c.id = :customer_id")
    Client findClientFetchOrders(@Param("customer_id") Integer customerId);
}
