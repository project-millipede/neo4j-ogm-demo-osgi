package org.neo4j.ogm.demo.osgi.impl;

import org.neo4j.ogm.demo.osgi.api.Service;
import org.neo4j.ogm.demo.osgi.model.Entity;
import org.neo4j.ogm.demo.osgi.impl.common.Neo4jSessionFactory;
import org.neo4j.ogm.session.Session;

abstract class GenericService<T extends Entity> implements Service<T> {

    private static final int DEPTH_LIST = 0;
    private static final int DEPTH_ENTITY = 1;

    protected Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

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

    abstract Class<T> getEntityType();
}