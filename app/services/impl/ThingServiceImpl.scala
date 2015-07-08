package services.impl

import javax.inject.Inject

import models.Thing
import models.daos.ThingDao
import services.ThingService
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

class ThingServiceImpl @Inject() (thingDao: ThingDao) extends ThingService {

  override def list = thingDao.list
  override def save(thing: Thing): Future[Long] = thingDao.save(thing)
  override def find(id: Long): Future[Option[Thing]] = thingDao.find(id)
  override def delete(id: Long) = thingDao.delete(id)

}
