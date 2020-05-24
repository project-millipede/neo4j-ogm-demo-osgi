# Neo4j-OGM Demonstrator in OSGi environments

## Modules and purpose

| Modules |                                 General purpose |                                                                        OSGi integration |
| :-----: | ----------------------------------------------: | --------------------------------------------------------------------------------------: |
|  Model  |                       Implements the data model |                                                       Fragment Host: org.neo4j.ogm-core |
|   API   |                             Contract definition |
|  Impl   | Contract implementation (incl. DB session init) |                                                                       Provide a service |
| Client  |                                                 |                                                                     Consume the service |
| Feature |            Generate deployment description (DD) |                               Launch the project within the OSGi-ecosystem Apache Karaf |
|  ITest  |    Validate integrity of deployment description | Launch the application in different OSGi-Runtime implementations Equinox / Apache Felix |

## Model / OGM - Binding

> The binding of the application model and Neo4j-OGM in an OSGi environment gets
> negotiated using the fragment host declaration.

```
Fragment Host: org.neo4j.ogm-core
```

> The attribute fragment-host has to get defined in the model`s bundle configuration,
> which generates the MANIFEST.MF file.

## Class scanning - Domain Objects

The class DomainInfo, part of Neo4j-OGM's core module, is responsible for scanning domain objects.
The configuration requires one or more package names in which domain objects get defined.

In this demo only one package hosts domain objects; its org.neo4j.ogm.demo.osgi.model.

## Supported OSGi-Runtimes

### Equinox

> Status - (Fully working)

### Apache Felix

> Status - (Not working)
> Blocker - Fix classloading issue in Neo4j-OGM / module - Core / class - DomainInfo

## Configuration instructions

> Requirements: Apache Karaf, Neo4j-Desktop

### Apache Karaf

Follow the installation and setup instructions provided on the Karaf's website.

> Important: The project is currently only compatible with the OSGi equinox runtime,
> see section Supported OSGi-Runtimes

The default settings of Apache Karaf is set to set Equinox as its runtime.

1. Go to the /etc folder, located relative to your Karaf installation

2. Modify file config.properties file and ensure the property karaf.framework is set to equinox

```
# karaf.framework=felix
karaf.framework=equinox
```

### Neo4j Desktop

Follow the installation and setup instructions provided on the Neo4j's website.

1. Start the Neo4j desktop application

2. Add a new local graph store

```
Add Database => Create a local Graph => Set password neo4jPWD
```

3. Start the database created store

## Build instructions

```
mvn -s settings.xml clean install
```

## Start instructions

To get all project artifacts necessary, two options are available.

### Use deployed project feature and artifacts directly

1. Configure Apache Karaf to use remote maven repositories which host the build artifacts

1.1 Install Karaf's maven feature

```
feature:install maven
```

1.2 Add relevant maven repositories to Karaf's settings

**_Neo4j-Java-Driver - (OSGi compatible runtime dependencies)_**

```
maven:repository-add -idx 0 -id neo4j-java-driver.repository --snapshots https://gitlab.com/api/v4/projects/18622687/packages/maven
```

**_Neo4j-OGM - (OSGi compatible runtime dependencies)_**

```
maven:repository-add -idx 1 -id neo4j-ogm.repository --snapshots https://gitlab.com/api/v4/projects/18591736/packages/maven
```

**_Neo4j-OGM-Demo_**

```
maven:repository-add -idx 2 -id neo4j-ogm.demo.repository --snapshots https://gitlab.com/api/v4/projects/18785831/packages/maven
```

2. Add the projects feature repository

```
repo-add mvn:org.neo4j/neo4j-ogm-demo-osgi-feature/1.0.0/xml/features
```

3. Install the feature

```
feature:install neo4j-ogm-demo-osgi-feature
```

### Use local build project feature and artifacts

1. Follow the build instructions

2. Add the projects feature repository

```
repo-add file:${placeholder-replace-with-absolute-path-to-project}/neo4j-ogm-demo-osgi/demo-feature/target/feature/feature.xml
```

3. Install the feature

```
feature:install neo4j-ogm-demo-osgi-feature
```
