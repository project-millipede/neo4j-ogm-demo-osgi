package org.neo4j.ogm.demo.osgi.impl;

import org.neo4j.ogm.demo.osgi.api.Service;
import org.neo4j.ogm.demo.osgi.model.Entity;
import org.neo4j.ogm.osgi.OGMSessionComponentService;
import org.neo4j.ogm.osgi.OGMSessionFactory;
import org.neo4j.ogm.osgi.OGMSessionFactoryService;
import org.neo4j.ogm.session.Session;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Reference;

abstract class GenericService<T extends Entity> implements Service<T> {

  private static final int DEPTH_LIST = 0;
  private static final int DEPTH_ENTITY = 1;

  private ComponentFactory<OGMSessionFactoryService> componentFactory;
  private OGMSessionComponentService sessionComponent;

  private Session session;

  protected ComponentFactory<OGMSessionFactoryService> getComponentFactory() {
    return componentFactory;
  }

  @Reference(target = "(component.factory=" + OGMSessionFactory.FACTORY_NAME + ")")
  protected void setComponentFactory(ComponentFactory<OGMSessionFactoryService> factory) {
    this.componentFactory = factory;

    ComponentInstance factoryInstance = factory.newInstance(null);

    // This is a local variable
    OGMSessionFactory sessionFactory = (OGMSessionFactory) factoryInstance.getInstance();
    try {
      this.session = sessionFactory.getSessionFactory().openSession();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected OGMSessionComponentService getSessionComponent() {
    return sessionComponent;
  }

  // @Reference
  protected void setSessionComponent(OGMSessionComponentService sessionComponent) {
    this.sessionComponent = sessionComponent;
    try {
      this.session = sessionComponent.getSessionFactory().openSession();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected Session getSession() {
    return session;
  }

  protected void setSession(Session session) {
    this.session = session;
  }

  public Iterable<T> findAll() {
    return session.loadAll(getEntityType(), DEPTH_LIST);
  }

  public T find(Long id) {
    return session.load(getEntityType(), id, DEPTH_ENTITY);
  }

  public void delete(Long id) {
    session.delete(session.load(getEntityType(), id));
  }

  public T createOrUpdate(T object) {
    session.save(object, DEPTH_ENTITY);
    return find(object.getId());
  }

  public abstract Class<T> getEntityType();
}
