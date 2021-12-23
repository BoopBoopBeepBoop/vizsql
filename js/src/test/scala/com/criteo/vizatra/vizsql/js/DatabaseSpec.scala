package com.criteo.vizatra.vizsql.js

import com.criteo.vizatra.vizsql.DB
import com.criteo.vizatra.vizsql._
import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers

class DatabaseSpec extends AnyFunSpecLike with Matchers {
  implicit val dialect: Dialect = sql99.dialect
  val db = DB(schemas = List(
    Schema(
      "sakila",
      tables = List(
        Table(
          "City",
          columns = List(
            Column("city_id", INTEGER(nullable = false)),
            Column("city", STRING(nullable = true)),
            Column("country_id", INTEGER(nullable = false)),
            Column("last_update", TIMESTAMP(nullable = false))
          )
        )
      )
    )
  ))

  describe("Database") {
    it("should be able to parse queries") {
      val database = new Database(db)
      val res = database.parse("SELECT city_id FROM city")
      res.select.get.columns.head.name shouldEqual "city_id"
    }
  }
}
