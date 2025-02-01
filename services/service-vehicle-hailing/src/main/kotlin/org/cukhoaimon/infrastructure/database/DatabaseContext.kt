package org.cukhoaimon.infrastructure.database

import org.jetbrains.exposed.sql.Database


interface DatabaseContext {
	val config: DatabaseConfig
	val write: Database

	class Builder(
		private val config: DatabaseConfig
	) {
		fun build(): DatabaseContext {
			val factory = DataSourceFactory.HIKARI
			val config = this.config
			val primary = factory.create(
				dbConfig = config,
				pool = ConnectionPoolConfig()
			)

			return object: DatabaseContext {
				override val config = config
				override val write = Database.connect(datasource = primary)
			}
		}
	}
}