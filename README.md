# Neo4j-OGM Demonstrator in OSGi environments

## Modules and purpose

| Modules |                                 General purpose |                                                                        OSGi integration |
| :-----: | ----------------------------------------------: | --------------------------------------------------------------------------------------: |
|  Model  |                       Implements the data model |                                                       Fragment Host: org.neo4j.ogm-core |
|   API   |                             Contract definition |
|  Impl   | Contract implementation (incl. DB session init) |                                                                       Provide a service |
| Client  |       Fetch existing data from store at startup |                                                                     Consume the service |
| Command |                       CLI based CRUD operations |                                                                     Consume the service |
| Feature |            Generate deployment description (DD) |                               Launch the project within the OSGi-ecosystem Apache Karaf |
|  ITest  |    Validate integrity of deployment description | Launch the application in different OSGi-Runtime implementations Equinox / Apache Felix |

## Model / OGM - Binding

> The binding of the application model and Neo4j-OGM in an OSGi environment gets
> negotiated using the fragment host declaration.

```
Fragment Host: org.neo4j.ogm-osgi
```

> The attribute fragment-host has to get defined in the model`s bundle
> configuration. A respective entry gets generates in the bundles MANIFEST.MF
> file in the build phase.

## Class scanning - Domain Objects

The class DomainInfo, part of Neo4j-OGM's core module, is responsible for
scanning domain objects. The configuration requires one or more package names in
which domain objects get defined.

In this demo only one package hosts domain objects; its
org.neo4j.ogm.demo.osgi.model.

## Supported OSGi-Runtimes

### Equinox

> Status - (Fully working)

### Apache Felix

> Status - (Fully working)

> **_Note:_** Apache Felix works out of the box. Despite a minor issue with
> fragment-bundles, more precise fragment-hosts will not get refreshed when you
> start Apache Karaf for a second time, and bundles are in the cache, see the
> build instruction for Apache Felix.

## Configuration instructions

> Requirements: Apache Karaf, Neo4j-Desktop

### Apache Karaf

Follow the installation and setup instructions provided on the Apache Karaf's
website.

> Apache Karaf's default OSGi runtime setting is Apache Felix. To switch Apache
> Felix in favor for Equinox change the configuration as follows.

1. Go to the /etc folder, located relative to your Apache Karaf installation

2. Modify file config.properties file and set the value of property
   karaf.framework set to equinox

```
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

### Equinox build

```
mvn -s settings.xml clean install
```

### Apache Felix build

```
mvn -s settings.xml clean install -P felix
```

## Start instructions

To get all project artifacts necessary, two options are available.

### Use deployed project feature and artifacts directly

1. Configure Apache Karaf to use remote maven repositories which host the build
   artifacts

1.1 Install Apache Karaf's maven feature

```
feature:install maven
```

1.2 Add relevant maven repositories to Apache Karaf's settings

**_Neo4j-Java-Driver - (OSGi compatible runtime dependencies)_**

```
maven:repository-add -idx 0 -id neo4j-java-driver.repository --snapshots https://gitlab.com/api/v4/projects/18622687/packages/maven
```

**_Neo4j-OGM - (OSGi compatible runtime dependencies)_**

```
maven:repository-add -idx 1 -id neo4j-ogm.repository --snapshots https://gitlab.com/api/v4/projects/18591736/packages/maven
```

**_Fragment Bundle Refresh - (OSGi runtime dependency, optional - Relevant only
for Apache Felix relevant)_**

```
maven:repository-add -idx 2 -id osgi-helper.repository --snapshots https://gitlab.com/api/v4/projects/18953434/packages/maven
```

**_Neo4j-OGM-Demo_**

```
maven:repository-add -idx 3 -id neo4j-ogm.demo.repository --snapshots https://gitlab.com/api/v4/projects/18785831/packages/maven
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

## Application configuration

Session configuration gets handled in an OSGi agnostic manner, meaning the OSGi
configuration methodology gets used to serve configuration to the application.

### Customize configuration settings

To adjust the configuration, edit the session.config file located in the
resource folder of module demo-feature. Making the adjustments effective
requires a rebuild of the application, see build instructions.

### Driver configuration

The application uses the operation variant "bolt"; only the respective
configuration options are described.

```
username="neo4j"
password="neo4jPWD"
uri="bolt://localhost:7687"
```

### Domain object model configuration

```
domain.packages=[ \
 "org.neo4j.ogm.demo.osgi.model", \
 "org.neo4j.ogm.demo.osgi.model.second", \
]
```

### Limitations

The Neo4j-OGM provides much more configuration options than those mentioned
here. Only the options mentioned in the example above for driver and domain
object model are supported, yet.

#### Why this limitation?

As mentioned in the configuration, the OSGi configuration methodology gets used
to delegate configuration to the Neo4j OGM SessionFactory. The delegation
approach followed introduces repetitive variable declarations for configuration
options see
[module neo4j-ogm-osgi OGMSessionConfig](https://github.com/project-millipede/neo4j-ogm-osgi/blob/master/neo4j-ogm-osgi/src/main/java/org/neo4j/ogm/osgi/OGMSessionConfig.java)

> More information about supported configuration methods and the respective
> options can be found in the
> [configuration wiki entry](https://github.com/project-millipede/neo4j-ogm-demo-osgi/wiki/Session-configuration).

## Play with the demo

> After installing the feature **_neo4j-ogm-demo-osgi-feature_**, the
> application starts; the client prints existing objects in the data store
> persisted from a previous session.

### Available Commands

**_The demo application provides several CLI commands to execute CRUD operations
against the data-store._**

More information about available commands and the respective options can be
found in the
[commands wiki entry](https://github.com/project-millipede/neo4j-ogm-demo-osgi/wiki/Commands).
