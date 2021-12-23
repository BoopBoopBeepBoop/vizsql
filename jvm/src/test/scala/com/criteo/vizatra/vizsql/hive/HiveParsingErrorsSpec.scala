package com.criteo.vizatra.vizsql.hive

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.EitherValues
import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers

class HiveParsingErrorsSpec extends AnyFunSpecLike with Matchers with EitherValues {

  val invalidHiveSelectStatements = TableDrivenPropertyChecks.Table(
    ("SQL", "Expected error"),

    (
      "select bucket from t",
      """select bucket from t
        |       ^
        |Error: *, table or expression expected
      """
    ),
    (
      "select foo from tbl limit 100 order by foo",
      """select foo from tbl limit 100 order by foo
        |                              ^
        |Error: ; expected
      """
    ),
// TODO: reenable
//    (
//      "select foo from bar tablesample (bucket 2 out af 3)",
//      """select foo from bar tablesample (bucket 2 out af 3)
//        |                                              ^
//        |Error: ; expected
//      """.stripMargin
//    )
  )

  // --

  for { (sql, err) <- invalidHiveSelectStatements } yield {
    it(s"Error on invalid Hive [$sql]") {
      val result = new HiveDialect(Map.empty).parser.parseStatement(sql)
        .fold(_.toString(sql, ' ').trim, _ => "[NO ERROR]")
      val expected = err.toString.stripMargin.trim

      result shouldEqual expected
    }
  }

}
