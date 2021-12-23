package com.criteo.vizatra.vizsql.js.json

import com.criteo.vizatra.vizsql.{Column, INTEGER, Schema, Table}
import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers

class SchemaReaderSpec extends AnyFunSpecLike with Matchers {
  describe("apply()") {
    it("should return a schema") {
      val res = SchemaReader(
        """
          |{
          | "name":"schema1",
          | "tables": [
          |   {
          |     "name":"table1",
          |     "columns": [
          |       {"name": "col1", "type": "int4"}
          |     ]
          |   }
          | ]
          |}
      """.stripMargin)
      res shouldEqual Schema(
        "schema1",
        List(
          Table("table1", List(Column("col1", INTEGER())))
        )
      )
    }
  }
}
