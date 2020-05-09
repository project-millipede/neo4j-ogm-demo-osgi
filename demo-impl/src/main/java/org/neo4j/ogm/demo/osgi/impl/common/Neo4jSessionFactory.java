package org.neo4j.ogm.demo.osgi.impl.common;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class Neo4jSessionFactory {

    private final static Configuration configuration = new Configuration.Builder()
            .uri("bolt://localhost:7687")
            .credentials("neo4j", "test1234")
            .build();

    private final static SessionFactory sessionFactory = new SessionFactory(configuration, "org.neo4j.ogm.demo.osgi.model");

    private static Neo4jSessionFactory factory = new Neo4jSessionFactory();

    // prevent external instantiation
    private Neo4jSessionFactory() {
    }

    public static Neo4jSessionFactory getInstance() {
        return factory;
    }

    public Session getNeo4jSession() {
        return sessionFactory.openSession();
    }
}