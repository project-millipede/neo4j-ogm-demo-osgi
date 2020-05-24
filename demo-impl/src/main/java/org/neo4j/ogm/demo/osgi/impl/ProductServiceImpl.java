package org.neo4j.ogm.demo.osgi.impl;

import org.neo4j.ogm.demo.osgi.api.ProductService;
import org.neo4j.ogm.demo.osgi.model.Product;
import org.osgi.service.component.annotations.Component;

@Component
public class ProductServiceImpl extends GenericService<Product> implements ProductService {

  @Override
  public Class<Product> getEntityType() {
    return Product.class;
  }
}
