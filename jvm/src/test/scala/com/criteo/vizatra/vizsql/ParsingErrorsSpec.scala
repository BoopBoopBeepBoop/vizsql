package com.criteo.vizatra.vizsql

import sql99.*
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.EitherValues
import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers

class ParsingErrorsSpec extends AnyFunSpecLike with Matchers with EitherValues {

  val invalidSQL99SelectStatements = Seq(
    (
      """xxx""",
      """|xxx
         |^
         |Error: select expected
      """
    ),
    (
      """select""",
      """|select
         |      ^
         |Error: *, table or expression expected
      """
    ),
    (
      """select 1 +""",
      """|select 1 +
         |         ^
         |Error: expression expected
      """
    ),
    (
      """select 1 + *""",
      """|select 1 + *
         |           ^
         |Error: expression expected
      """
    ),
    (
      """select (1 + 3""",
      """|select (1 + 3
         |             ^
         |Error: ) expected
      """
    ),
    (
      """select * from""",
      """|select * from
         |             ^
         |Error: table, join or subselect expected
      """
    ),
    (
      """select * from (selet 1)""",
      """|select * from (selet 1)
         |               ^
         |Error: select expected
      """
    ),
    (
      """select * from (select 1sh);""",
      """|select * from (select 1sh);
         |                          ^
         |Error: ident expected
      """
    ),
    (
      """select * from (select 1)sh)""",
      """|select * from (select 1)sh)
         |                          ^
         |Error: ; expected
      """
    ),
    (
      """SELECT CustomerName; City FROM Customers;""",
      """|SELECT CustomerName; City FROM Customers;
         |                     ^
         |Error: end of statement expected
      """
    ),
    (
      """SELECT CustomerName FROM Customers UNION ALL""",
      """|SELECT CustomerName FROM Customers UNION ALL
         |                                            ^
         |Error: select expected
      """
    )
  )

  // --

  for { (sql, err) <- invalidSQL99SelectStatements } yield {
    // TODO: reenable
    ignore(s"fail on invalid SQL-99 SELECT statements [$sql]") {
      val parsed =
        (new SQL99Parser).parseStatement(sql)
          .fold(
            _.toString(sql, ' ').trim,
            _ => "[NO ERROR]"
          )

      val expected = err.stripMargin.trim
      println(parsed)
      parsed shouldEqual (expected)
    }
  }
}