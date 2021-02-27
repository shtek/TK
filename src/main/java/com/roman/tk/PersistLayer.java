package com.roman.tk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PersistLayer {
    private static final Logger log = LoggerFactory.getLogger(PersistLayer.class);
    public Set<ProductItem> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductItem> products) {
        this.products = products;
    }
    public  boolean addProducts(List<ProductItem> products)
    {
        boolean result = this.products.addAll(products);
        log.info("added to the storage " + result);
        return result;
    }

  private  Set<ProductItem> products = new HashSet<>();

}
