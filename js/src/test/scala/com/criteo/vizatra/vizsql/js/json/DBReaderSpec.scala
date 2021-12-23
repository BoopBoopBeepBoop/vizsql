package com.criteo.vizatra.vizsql.js.json

import com.criteo.vizatra.vizsql._
import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers

class DBReaderSpec extends AnyFunSpecLike with Matchers {
  describe("apply()"){
    it("should returns a DB") {
      val db = DBReader.apply(
        """
          |{
          | "dialect": "vertica",
          | "schemas": [
          |   {
          |     "name":"schema1",
          |     "tables": [
          |       {
          |         "name":"table1",
          |         "columns": [
          |           {"name": "col1", "type": "int4"}
          |         ]
          |       }
          |     ]
          |   }
          | ]
          |}
      """.stripMargin)
      db.dialect shouldBe vertica.dialect
      db.schemas shouldEqual Schemas(List(Schema("schema1", List(Table("table1", List(Column("col1", INTEGER())))))))
    }
  }
}
