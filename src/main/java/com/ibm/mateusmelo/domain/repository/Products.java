package com.ibm.mateusmelo.domain.repository;

import com.ibm.mateusmelo.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Products extends JpaRepository<Product, Integer> {
}
