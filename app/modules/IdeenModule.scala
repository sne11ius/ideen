package modules

import com.google.inject.{ AbstractModule, Provides }
import models.Thing
import models.daos._
import services.ThingService
import services.impl.ThingServiceImpl
import net.codingwell.scalaguice.ScalaModule
import play.api.Configuration
import play.api.libs.concurrent.Execution.Implicits._

class IdeenModule extends AbstractModule with ScalaModule {

  /**
   * Configures the module.
   */
  def configure() {
    bind[ThingService].to[ThingServiceImpl]
    bind[ThingDao].to[ThingDaoSlick]
  }

}
