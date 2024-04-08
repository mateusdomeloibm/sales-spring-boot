package com.ibm.mateusmelo.rest.controller;

import com.ibm.mateusmelo.domain.entity.Product;
import com.ibm.mateusmelo.domain.repository.Products;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    private final Products repository;

    public ProductController(Products repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> find(Product filter) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Product> example = Example.of(filter, matcher);
        return repository.findAll(example);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product not found"
                        )
                );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody Product product) {
        return repository.save(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Product update(@PathVariable Integer id, @Valid @RequestBody Product newProduct) {
        return repository.findById(id)
                .map(
                        product -> {
                            newProduct.setId(product.getId());
                            repository.save(newProduct);
                            return newProduct;
                }).orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product not found"
                        )
                );
    }

    @DeleteMapping("/{id}")
    public Product delete(@PathVariable Integer id) {
        return repository.findById(id)
                .map(
                        product -> {
                            repository.deleteById(id);
                            return product;
                }).orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product not found"
                        )
                );
        }
}
