package models.daos

import slick.driver.JdbcProfile
import slick.lifted.ProvenShape.proveShapeOf
import play.api.Logger

trait DBTableDefinitions {

  protected val driver: JdbcProfile
  import driver.api._

  case class DBThing(
    id: Option[Long],
    title: String,
    description: String
  )

  class Things(tag: Tag) extends Table[DBThing](tag, "thing") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
		def title = column[String]("title", O.SqlType("LONGTEXT"))
    def description = column[String]("body", O.SqlType("LONGTEXT"))
    def * = (id.?, title, description) <> (DBThing.tupled, DBThing.unapply)
  }

  // table query definitions
  val slickThings = TableQuery[Things]

  lazy val allTables = Array(
    slickThings.schema
  ).reduceLeft(_ ++ _)

  def create = {
    allTables.create
  }

  def drop = {
    allTables.drop
  }

}
