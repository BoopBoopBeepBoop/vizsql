package com.criteo.vizatra.vizsql.js.json

import com.criteo.vizatra.vizsql.hive.{HiveArray, HiveMap, HiveStruct, TypeParser}
import com.criteo.vizatra.vizsql.{BOOLEAN, Column, INTEGER, STRING}
import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers

class ColumnReaderSpec extends AnyFunSpecLike with Matchers {
  describe("apply()") {
    it("should return a column with nullable") {
      val res = ColumnReader.apply("""{"name":"col","type":"int4","nullable":true}""")
      res shouldEqual Column("col", INTEGER(true))
    }
    it("should return a column without nullable") {
      val res = ColumnReader.apply("""{"name":"col","type":"int4"}""")
      res shouldEqual Column("col", INTEGER(false))
    }
  }

  describe("parseType()") {
    it("should parse map type") {
      val res = ColumnReader.parseType("""map<string,integer>""", true)
      res shouldEqual HiveMap(STRING(true), INTEGER(true))
    }
    it("should parse array type") {
      ColumnReader.parseType("array<int>", true) shouldEqual HiveArray(INTEGER(true))
    }
    it("should parse struct type") {
      ColumnReader.parseType("struct<a:struct<b:boolean>,c:struct<d:string>>", true) shouldEqual HiveStruct(List(
        Column("a", HiveStruct(List(
          Column("b", BOOLEAN(true))
        ))),
        Column("c", HiveStruct(List(
          Column("d", STRING(true))
        )))
      ))
    }
  }

}
