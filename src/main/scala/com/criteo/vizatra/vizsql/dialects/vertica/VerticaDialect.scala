package com.criteo.vizatra.vizsql

object vertica {
  implicit val dialect = new Dialect {
    def parser = new SQL99Parser
    val functions = postgresql.dialect.functions orElse {
      case "datediff" => new SQLFunction3 {
        def result = { case (_, (_, t1), (_, t2)) => Right(INTEGER(nullable = t1.nullable ||t2.nullable)) }
      }
    }: PartialFunction[String,SQLFunction]
    override def toString = "Vertica"
  }
}
