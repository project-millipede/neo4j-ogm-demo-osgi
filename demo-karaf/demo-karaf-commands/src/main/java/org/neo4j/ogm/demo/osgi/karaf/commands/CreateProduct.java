package org.neo4j.ogm.demo.osgi.karaf.commands;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.neo4j.ogm.demo.osgi.api.ProductService;
import org.neo4j.ogm.demo.osgi.model.Product;

@Command(scope = "product", name = "create", description = "Creates and persists product")
@Service
public class CreateProduct implements Action {

  @Reference private ProductService service;

  @Option(
      name = "-name",
      aliases = {"--productName"},
      description = "Option product name to the command",
      required = true)
  private String productName;

  @Option(
      name = "-units",
      aliases = {"--unitsInStock"},
      description = "Option units in stock to the command")
  private Integer unitsInStock = 0;

  @Override
  public Object execute() throws Exception {
    System.out.println("Executing command product:create");
    System.out.println("Argument productName: " + productName);
    System.out.println("Argument unitsInStock: " + unitsInStock);

    Product product = new Product();
    product.setProductName(productName);
    product.setUnitsInStock(unitsInStock);

    service.createOrUpdate(product);

    return null;
  }
}
