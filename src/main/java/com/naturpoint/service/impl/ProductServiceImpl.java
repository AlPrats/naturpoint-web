package com.naturpoint.service.impl;

import com.naturpoint.model.Product;
import com.naturpoint.repository.IGenericRepository;
import com.naturpoint.repository.IProductRepository;
import com.naturpoint.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends CRUDImpl<Product, Integer> implements IProductService {

    @Autowired
    private IProductRepository repository;

    @Override
    protected IGenericRepository<Product, Integer> getRepository() {
        return repository;
    }
}
