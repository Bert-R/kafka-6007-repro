# Introduction

This repo was created to reproduce issue [KAFKA-6007](https://issues.apache.org/jira/browse/KAFKA-6007), but in the end, it became a proof showing that the issue does not exist (anymore?).

# Success scenario
Here the custom SMT JAR is mapped to ``/kafka/connect/debezium-connector-postgres/example-transform-0.0.1-SNAPSHOT.jar``.

Follow the below steps:

1. ``./gradlew build``
2.  ``docker-compose -f docker-compose-working.yaml up -d``
3. Wait for the Debezium service to start
4. ``./register-debezium-connector.sh``
5. This should give the following output:
```
{"name":"example-connector","config":{"connector.class":"io.debezium.connector.postgresql.PostgresConnector","database.hostname":"db","database.port":"5432","database.user":"admin","database.password":"admin","database.dbname":"example","plugin.name":"pgoutput","topic.prefix":"some-prefix","table.include.list":"public.hfj_tag_def","publication.autocreate.mode":"filtered","transforms":"example-message","transforms.example-message.type":"example.repro.ExampleTransformation","name":"example-connector"},"tasks":[],"type":"source"}
```

This proves it works, but it's an undesirable way of working. It should not be necessary to add the SMT to the JARS of the database connector.


# Expected failure scenario
Here the custom SMT JAR is mapped to ``/kafka/connect/dbz-custom-smt/example-transform-0.0.1-SNAPSHOT.jar``.

Follow the below steps:

1. ``./gradlew build``
2. ``docker-compose -f docker-compose-failing.yaml up -d``
3. Wait for the Debezium service to start
4. ``./register-debezium-connector.sh``
5. This should give the following output:
```
{"name":"example-connector","config":{"connector.class":"io.debezium.connector.postgresql.PostgresConnector","database.hostname":"db","database.port":"5432","database.user":"admin","database.password":"admin","database.dbname":"example","plugin.name":"pgoutput","topic.prefix":"some-prefix","table.include.list":"public.hfj_tag_def","publication.autocreate.mode":"filtered","transforms":"example-message","transforms.example-message.type":"example.repro.ExampleTransformation","name":"example-connector"},"tasks":[],"type":"source"}
```

This proves it also works and this is a desirable scenario: just add the SMT as a connector.

# Difference in compose files

The compose files differ in the following way:
```diff
56,59c56
<         # This is a weird place to add the JAR, but it's currently necessary. See the following links for background:
<         # https://issues.apache.org/jira/browse/KAFKA-6007
<         # https://stackoverflow.com/questions/46712095/using-a-custom-converter-with-kafka-connect
<         target: /kafka/connect/debezium-connector-postgres/example-transform-0.0.1-SNAPSHOT.jar
---
>         target: /kafka/connect/dbz-custom-smt
65c62
<      file: ./dbz-extra-classes/example-transform-0.0.1-SNAPSHOT.jar
---
>      file: ./dbz-extra-classes
```
