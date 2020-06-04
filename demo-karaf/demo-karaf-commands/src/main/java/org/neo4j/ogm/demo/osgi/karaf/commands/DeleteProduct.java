package org.neo4j.ogm.demo.osgi.karaf.commands;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.demo.osgi.model.Product;
import org.neo4j.ogm.osgi.OGMSessionFactory;
import org.neo4j.ogm.osgi.OGMSessionFactoryService;
import org.neo4j.ogm.session.Session;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;

@Command(scope = "product", name = "delete", description = "Delete a persisted product")
@Service
public class DeleteProduct implements Action {

  @Reference(filter = "(component.factory=" + OGMSessionFactory.FACTORY_NAME + ")")
  private ComponentFactory<OGMSessionFactoryService> componentFactory;

  @Option(
      name = "-name",
      aliases = {"--productName"},
      description = "Option product name to the command",
      required = true)
  private String productName;

  @Override
  public Object execute() throws Exception {
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

    return null;
  }
}
