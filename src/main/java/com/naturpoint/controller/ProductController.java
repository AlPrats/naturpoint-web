package com.naturpoint.controller;

import com.naturpoint.dto.ProductDTO;
import com.naturpoint.exception.ModelNotFoundException;
import com.naturpoint.model.Category;
import com.naturpoint.model.Product;
import com.naturpoint.service.ICategoryService;
import com.naturpoint.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductService service;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> listProducts = service.findAll()
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") Integer id) {
        ProductDTO dtoResponse;
        Product obj = service.findById(id);
        if (obj == null){
            throw new ModelNotFoundException("Id not found: " + id);
        } else {
            dtoResponse = mapper.map(obj, ProductDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ProductDTO dto){
        Optional<Category> optionalCategory = Optional.ofNullable(categoryService.findById(dto.getIdCategory()));
        if (optionalCategory.isEmpty()){
            throw new ModelNotFoundException("Id category not found: " + dto.getIdCategory());
        }
        Product product = service.save(mapper.map(dto, Product.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getIdProduct()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Product> update(@Valid @RequestBody ProductDTO dto){
        Product obj = service.findById(dto.getIdProduct());
        if (obj == null){
            throw new ModelNotFoundException("Id product not found: " + dto.getIdProduct());
        }
        return new ResponseEntity<>(service.update(mapper.map(dto, Product.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){
        Product obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("Id product not found: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
