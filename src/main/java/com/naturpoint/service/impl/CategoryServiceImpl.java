package com.naturpoint.service.impl;

import com.naturpoint.model.Category;
import com.naturpoint.repository.ICategoryRepository;
import com.naturpoint.repository.IGenericRepository;
import com.naturpoint.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends CRUDImpl<Category, Integer> implements ICategoryService {

    @Autowired
    private ICategoryRepository repository;

    @Override
    protected IGenericRepository<Category, Integer> getRepository() {
        return repository;
    }
}
