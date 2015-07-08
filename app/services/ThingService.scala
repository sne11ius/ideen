package services

import models.Thing

import scala.concurrent.Future

trait ThingService {

  def list: Future[List[Thing]]
  def save(thing: Thing): Future[Long]
  def find(id: Long): Future[Option[Thing]]
  def delete(id: Long)

}
