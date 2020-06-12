package org.neo4j.ogm.demo.osgi.runtime.commands;

import org.apache.felix.service.command.Descriptor;
import org.neo4j.ogm.demo.osgi.api.ProductService;
import org.neo4j.ogm.demo.osgi.model.Product;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(
    service = CreateProduct.class,
    property = {"osgi.command.scope=product", "osgi.command.function=create"},
    name = "create.command")
public class CreateProduct {

  @Reference private ProductService service;

  /**
   * Create entity product and persist it to data store
   *
   * @param productName Product name to create
   * @param unitsInStock Number of items of a product to create
   * @throws Exception When create failed
   */
  @Descriptor("Create entity product and persist it to data store")
  public void create(
      @Descriptor("Product name command argument") String productName,
      @Descriptor("Units in stock command argument") Integer unitsInStock)
      throws Exception {
    System.out.println("Executing command product:create");
    System.out.println("Argument productName: " + productName);
    System.out.println("Argument unitsInStock: " + unitsInStock);

    Product product = new Product();
    product.setProductName(productName);
    product.setUnitsInStock(unitsInStock);

    service.createOrUpdate(product);
  }

  @Activate
  void activate() {
    System.out.println("Activated: " + this.getClass().getCanonicalName());
  }

  @Deactivate
  void deactivate() {
    System.out.println("Deactivated: " + this.getClass().getCanonicalName());
  }
}
