package org.cukhoaimon.infrastructure.database

import org.jetbrains.exposed.sql.Database


interface DatabaseContext {
	val config: DatabaseConfig
	val write: Database
	val read: Database

	class Builder(
		private val config: DatabaseConfig
	) {
		fun build(): DatabaseContext {
			val factory = DataSourceFactory.HIKARI
			val config = this.config
			val write = factory.create(
				kind = DatabaseKind.WRITE,
				dbConfig = config,
				pool = ConnectionPoolConfig()
			)
			val read = factory.create(
				kind = DatabaseKind.READ,
				dbConfig = config,
				pool = ConnectionPoolConfig()
			)

			return object: DatabaseContext {
				override val config = config
				override val write = Database.connect(datasource = write)
				override val read = Database.connect(datasource = read)
			}
		}
	}
}