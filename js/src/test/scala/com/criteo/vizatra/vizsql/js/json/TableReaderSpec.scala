package com.criteo.vizatra.vizsql.js.json

import com.criteo.vizatra.vizsql.{Column, DECIMAL, INTEGER}
import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers

class TableReaderSpec extends AnyFunSpecLike with Matchers {
  describe("apply()") {
    it("return a table") {
      val res = TableReader.apply(
        """
          |{
          |"name": "table_1",
          |"columns": [
          | {
          |   "name": "col1",
          |   "type": "int4"
          | },
          | {
          |   "name": "col2",
          |   "type": "float4"
          | }
          |]
          |}""".stripMargin)
      res.name shouldBe "table_1"
      res.columns shouldBe List(Column("col1", INTEGER()), Column("col2", DECIMAL()))
    }
  }
}
