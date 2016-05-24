# VizSQL

## VizSQL is a SQL parser & typer for scala

VizSQL provides support for parsing SQL statements into a scala AST. This AST can then be used to support many interesting transformations. We provide for example a static optimizer and an OLAP query rewriter. It also support typing the query to retrieve the resultset columns returned by a SQL statement.

### tl;dr

Here we use VizSQL to parse a SQL SELECT statement based on the SAKILA database, and to retrieve the column names and types of the returned resultset.

```scala
import com.criteo.vizatra.vizsql

val resultColumns =
  VizSQL.parseQuery(
    """SELECT country_id, max(last_update) as updated from City as x""",
    SAKILA
  )
  .fold(e => sys.error(s"SQL syntax error: $e"), identity)
  .columns
  .fold(e => sys.error(s"SQL error: $e"), identity)

assert(
  resultColumns == List(
    Column("country_id", INTEGER(nullable = false)),
    Column("updated", TIMESTAMP(nullable = true))
  )
)
```

Note that the query is not executed. It is just parsed, validated, analyzed and typed using the SAKILA database schema. The schema can be provided directly or extracted at runtime from a JDBC connection if needed. Here is for example the minimum SAKILA schema we need to provide so VizSQL is able to type the previous query:

```scala
val SAKILA = DB(schemas = List(
  Schema(
    "sakila",
    tables = List(
      Table(
        "City",
        columns = List(
          Column("city_id", INTEGER(nullable = false)),
          Column("city", STRING(nullable = true)),
          Column("country_id", INTEGER(nullable = false)),
          Column("last_update", TIMESTAMP(nullable = true))
        )
      )
    )
  )
))
```

### Dialects

VizSQL support several dialects allowing to understand specific SQL syntax or functions for different databases. We started the core one based on the *SQL99* standard. Then we enriched it to create specific dialects for **Vertica** and **Hive**. This is of course a work in progress (even for the SQL99 one).

### License

This project is licensed under the Apache 2.0 license.

### Copyright

Copyright © [Criteo](http://labs.criteo.com), 2016.
