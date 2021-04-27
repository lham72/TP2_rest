package fr.epsi.rest.store.db;

import fr.epsi.rest.store.model.Produit;

import java.util.ArrayList;
import java.util.List;

public class ProductDB {
    public static ProductDB INSTANCE = new ProductDB();

    private List<Produit> myProducts;



    public ProductDB() {
        myProducts = new ArrayList<>();
        for (int i = 1; i<=10; i++) {
            Produit myProduct = new Produit();
            myProduct.setId(i);
            myProduct.setName("Product_name_" + i);
            myProduct.setDetail("Product detail which have id = " + i);
            myProduct.setPrice(i*10);
            myProduct.setQuantity(i +3);
            myProduct.setInfo("Product Info which have id = " + i);
            myProduct.setImage("http://placehold.it/300x300/" + i +"/CCC");

            myProducts.add(myProduct);
        }
    }

    public ProductDB getInstance() {
        return INSTANCE;
    }

    public List<Produit> getDB() {
        return myProducts;
    }
}
