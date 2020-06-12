package org.neo4j.ogm.demo.osgi.runtime.commands;

import org.apache.felix.service.command.Descriptor;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.demo.osgi.model.Product;
import org.neo4j.ogm.osgi.OGMSessionFactory;
import org.neo4j.ogm.osgi.OGMSessionFactoryService;
import org.neo4j.ogm.session.Session;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(
    service = DeleteProduct.class,
    property = {"osgi.command.scope=product", "osgi.command.function=delete"},
    name = "delete.command")
public class DeleteProduct {
  @Reference(target = "(component.factory=" + OGMSessionFactory.FACTORY_NAME + ")")
  private ComponentFactory<OGMSessionFactoryService> componentFactory;

  /**
   * Delete entity product from data store based on its name
   *
   * @param productName Product name to delete
   * @throws Exception When delete failed
   */
  @Descriptor("Delete entity product from data store based on its name")
  public void delete(@Descriptor("Enter productName to delete") String productName)
      throws Exception {
    System.out.println("Executing command product:delete");
    System.out.println("Argument productName: " + productName);

    ComponentInstance factoryInstance = componentFactory.newInstance(null);
    OGMSessionFactory sessionFactory = (OGMSessionFactory) factoryInstance.getInstance();
    Session session = sessionFactory.getSessionFactory().openSession();

    System.out.println("Products in store before delete operation:");
    for (Product product : session.loadAll(Product.class)) {
      System.out.println(product);
    }

    Filter getByProductName = new Filter("productName", ComparisonOperator.EQUALS, productName);
    Object deleteOperationInformation =
        session.delete(Product.class, new Filters().add(getByProductName), true);

    // Print products after delete operation
    System.out.println("Products affected by the delete operation: " + deleteOperationInformation);

    System.out.println("Products in store after delete operation:");
    for (Product product : session.loadAll(Product.class)) {
      System.out.println(product);
    }
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
