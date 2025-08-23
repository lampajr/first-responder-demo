# First Responder Demo

The First Responder Demo application is meant to act as a test application for gauging the impact of OpenTelemetry tracing on a 
typical WildFly/EAP workload. The application is still in early stages, so more details on the performance tests -- the results, how
to run them locally, etc -- will be added soon.

## Setup

### Starting the database

```bash
podman run --replace -d --env POSTGRES_PASSWORD=frdemo --env POSTGRES_USER=frdemo --env POSTGRES_DB=frdemo --name frdemo-db -p 5432:5432 mirror.gcr.io/library/postgres:16
```

### Starting Kafka
To download and install Kafka, perform the steps below. For more information on Kafka, see the
[Apache Kafka Quickstart](https://kafka.apache.org/quickstart).

```bash
cd /tmp
wget https://dlcdn.apache.org/kafka/3.9.1/kafka_2.13-3.9.1.tgz
tar xf kafka_2.13-3.9.1.tgz
cd kafka_2.13-3.9.1
# (In separate terminal windows/tabs)
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
# (This next is optional)
bin/kafka-console-consumer.sh --topic IncidentReportedEvent --from-beginning --bootstrap-server localhost:9092
```

### Starting MapBox API mock

```bash
cd /tmp

git clone https://github.com/hpehl/frdemo-mapbox.git

# build
cd frdemo-mapbox && mvn -B -ntp clean package -DskipTests -Dquarkus.package.type=uber-jar

# run
java -jar target/frdemo-mapbox-1.0.0-SNAPSHOT-runner.jar
```

### Package the application's backend

```bash
mvn clean package -pl backend
```

### Run the application's backend

```bash
source ./backend/local.env
./backend/target/server/bin/standalone.sh
```

### [optional] Starting the load simulator

```bash
mvn -B -ntp package -DskipTests -Dquarkus.container-image.build=false -Dquarkus.container-image.push=false -pl simulator -Dquarkus.package.type=uber-jar
java -jar -DBACKEND_URL=http://localhost:8080 -DSIM_SEND=true simulator/target/simulator-1.0-SNAPSHOT-runner.jar
```

## Arquillian tests

The First Responder Demo has a number of basic integration tests using [Arquillian](https://arquillian.org/). The tests and coverage
are not exhaustive, but are meant to be a quick check of the application's basic functionality. To make running these tests as simple
as possible, PostgreSQL and Kafka are managed in Docker containers via the [testcontainers](https://testcontainers.org) project, so
a [working Docker/Podman environment will be required](https://www.testcontainers.org/supported_docker_environment/).

To run the tests, execute this command:

```
mvn clean compile verify -Parq-managed 
```
