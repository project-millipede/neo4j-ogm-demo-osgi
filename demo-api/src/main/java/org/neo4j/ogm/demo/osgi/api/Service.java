package org.neo4j.ogm.demo.osgi.api;

import org.neo4j.ogm.demo.osgi.model.Entity;

public interface Service<T extends Entity> {
  Iterable<T> findAll();

  T find(Long id);

  void delete(Long id);

  T createOrUpdate(T object);
}
