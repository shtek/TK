package com.roman.tk;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PersistLayer {
    public Set<ProductItem> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductItem> products) {
        this.products = products;
    }
    public  void addProducts(Set<ProductItem> products)
    {
        this.products.addAll(products);
    }

  private  Set<ProductItem> products = new HashSet<>();

}
