package com.criteo.vizatra.vizsql

object mysql {
  implicit val dialect: Dialect = new Dialect {
    lazy val parser = new SQL99Parser
    lazy val functions = SQLFunction.standard
    override def toString = "MySQL"
  }
}
