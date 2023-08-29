package models.config

import com.typesafe.config.Config
import play.api.ConfigLoader

case class DBConfig(host: String, username: String, password: String)

object DBConfig {

  implicit val configLoader: ConfigLoader[DBConfig] = new ConfigLoader[DBConfig] {

    def load(rootConfig: Config, path: String): DBConfig = {
      val config = rootConfig.getConfig(path)
      DBConfig(
        host     = config.getString("host"),
        username = config.getString("username"),
        password = config.getString("password"),
      )
    }
  }
}
