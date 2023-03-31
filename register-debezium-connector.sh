curl -X 'POST' \
  'localhost:8083/connectors/' \
  -H 'Accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "example-connector",
  "config": {
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
    "database.hostname": "db",
    "database.port": "5432",
    "database.user": "admin",
    "database.password": "admin",
    "database.dbname" : "example",
    "plugin.name": "pgoutput",
    "topic.prefix": "some-prefix",
    "table.include.list": "public.hfj_tag_def",
    "publication.autocreate.mode": "filtered",
    "transforms" : "example-message",
    "transforms.example-message.type" : "example.repro.ExampleTransformation"
  }
}'
