package models.daos

import models.Thing
import scala.concurrent.Future

trait ThingDao {

  def list: Future[List[Thing]]

  def save(thing: Thing): Future[Long]

  def find(id: Long): Future[Option[Thing]]
}
