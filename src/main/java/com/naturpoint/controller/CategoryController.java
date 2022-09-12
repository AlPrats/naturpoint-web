package com.naturpoint.controller;

import com.naturpoint.model.Category;
import com.naturpoint.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private ICategoryService service;

    @GetMapping
    public List<Category> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable("id") Integer id){
        return service.findById(id);
    }

    @PostMapping
    public Category save(@RequestBody Category category){
        return service.save(category);
    }

    @PutMapping
    public Category update(@RequestBody Category category){
        Category obj = service.findById(category.getIdCategory());
        if (obj == null){
            System.out.println("ID NOT FOUND: " + category.getIdCategory());
        }
        return service.update(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        service.delete(id);
    }

}
