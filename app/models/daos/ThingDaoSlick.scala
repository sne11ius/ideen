package models.daos

import slick.dbio.DBIOAction
import models.Thing
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ThingDaoSlick extends ThingDao with DaoSlick {

  import driver.api._

  override def list: Future[List[Thing]] = {
    val action = slickThings.result
    val result: Future[Seq[DBThing]] = db.run(action)
    result map {
      case list => {
        list.toList.map { t => Thing(t.id, t.title, t.description) }
      }
    }
  }

  override def save(thing: Thing): Future[Long] = {
    val dbThing = DBThing(None, thing.title, thing.description)
    val id = db.run((slickThings returning slickThings.map(_.id)) += dbThing)
    id
  }

  override def find(id: Long): Future[Option[Thing]] = {
    val action = slickThings.filter { _.id === id }.take(1).result
    val result = db.run(action)
    result map {
      case l => {
        if (0 < l.length)
          Some(Thing(l.head.id, l.head.title, l.head.description))
        else
          None
      }
    }
  }

  override def delete(id: Long) = {
    db.run(slickThings.filter { _.id === id }.delete)
  }

  allTables.createStatements.foreach(println)

}
