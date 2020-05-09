package org.neo4j.ogm.demo.osgi.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Product extends Entity {

    private String productName;

    private int unitsInStock;

    public Product() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }
}
