package com.APIX.product.dao;

import com.APIX.CustomRepository;
import com.APIX.product.model.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;


@Repository("productDAS")
public class ProductDAO implements CustomRepository<Product, Long> {
    private static List<Product> db = new ArrayList<>();
    
    static {
        initDummyData();
    }

    private static void initDummyData() {
        Product dummyProduct1 = new Product("Dummy Product 1", "Vendor 1", "Electronics", 99.99, 100);
        Product dummyProduct2 = new Product("Dummy Product 2", "Vendor 2", "Clothing", 49.99, 200);
        Product dummyProduct3 = new Product("Dummy Product 3", "Vendor 3", "Clothing", 100, 100);

        db.add(dummyProduct1);
        db.add(dummyProduct2);
        db.add(dummyProduct3);
    }
    
    @Override
    public Product getById(Long id) {
        for(Product product : db){
            if(product.getId().equals(id)){
                return product;
            }
        }
        return null;
    }

    @Override
    public boolean save(Product product) {
        if(product.getPrice() < 0 || product.getCount() < 0 ){
            return false;
        }

        Product searchProduct = getById(product.getId());
        if(searchProduct != null){
            return false;
        }

        db.add(product);
        return true;
    }

    @Override
    public List<Product> getAll() {
        return db;
    }

    @Override
    public boolean update(Product product) {
        if(product.getPrice() < 0 || product.getCount() < 0 ){
            return false;
        }

        for (int i = 0; i < db.size(); i++) {
            if (db.get(i).getId().equals(product.getId())) {
                db.set(i, product);
                db.get(i).setId(product.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        for(int i = 0; i < db.size(); i++){
            if(db.get(i).getId().equals(id)){
                db.remove(i);
                return true;
            }
        }
        return false;
    }
}
