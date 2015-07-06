package models.daos

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import play.api.db.slick.HasDatabaseConfig

/**
 * Trait that contains gimport models.daos.DBTableDefinitions
eneric slick db handling code to be mixed in with DAOs
 */
trait DaoSlick extends DBTableDefinitions with HasDatabaseConfig[JdbcProfile] {
  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](play.api.Play.current)
  import driver.api._
}
