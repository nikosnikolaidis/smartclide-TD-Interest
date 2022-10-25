# Smartclide TD Interest
SmartCLIDE TD Interest Backend Component

## Preconditions to build and run TD Interest

To build and run the backend service of TD Interest, the following software is required:

- Java (at least version 11)
- Apache Maven (at least version 3.2+)
- Docker (for building and running the final image)

## How to build TD Interest

TD Interest can be built using maven with the following command:

```shell
mvn install
```

In order to build a Docker image of the service that can be deployed, the following commands can be used:

```shell
mvn install
docker build -t ${IMAGE_NAME:IMAGE_TAG} .
```

More specifically:

```shell
mvn install
docker build -t smartclide-td-interest-backend:latest .
```

## How to run TD Interest

All the images of this component can be found [here](https://github.com/eclipse-opensmartclide/smartclide-TD-Interest/pkgs/container/smartclide%2Ftdinterest).

You can run the backend service with the following command:

```shell
docker run smartclide-td-interest-backend:latest
```

## Extra dependencies of TD Interest

This component uses an internal database in order to calculate the TD Interest, as from its definition requires historical data.
This database is a postgresql where the schema can be found [here](https://github.com/eclipse-opensmartclide/smartclide-TD-Interest/blob/main/src/main/resources/database/schema.sql).

After the creation of the database, the properties for accessing the database can be changed from 
[here](https://github.com/eclipse-opensmartclide/smartclide-TD-Interest/blob/main/src/main/resources/application.properties). In order to change the database url, username, and password.
- spring.datasource.url=jdbc:postgresql://localhost:5432/tdinterest
- spring.datasource.username=tdinterest
- spring.datasource.password=tdinterest
