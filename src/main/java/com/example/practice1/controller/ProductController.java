package com.example.practice1.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private record Product(
            Integer proudctId,
            String productName,
            double price){}

List<Product> products = new ArrayList<>(
        List.of(
                new Product(1,"iPhone",999.0),
                new Product(1,"Mac Pro",2000.0)
        )
);
    @GetMapping
    public List<Product> getProducts(){
        return products;
    }

    @PostMapping
    public Product saveproduct(@RequestBody Product product){
        products.add(product);
        return product;
    }
}

