package org.neo4j.ogm.demo.osgi.client;

import org.neo4j.ogm.demo.osgi.api.ProductService;
import org.neo4j.ogm.demo.osgi.model.Product;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class TestClient {

    @Reference
    private ProductService service;

    @Activate
    public void start() {
        Product productToCreate = new Product();
        productToCreate.setProductName("Vanilla Ice");
        productToCreate.setUnitsInStock(10);

        service.createOrUpdate(productToCreate);

        for (Product product : service.findAll()) {
            System.out.println(product);
        }
    }
}
