package org.cukhoaimon.infrastructure.database

import java.util.Properties

data class DatabaseConfig(
	val url: String = "localhost",
	val name: String = "local",
	val username: String = "local",
	val password: String = "local",
	val portNumber: Int? = 5432
)

internal val DatabaseConfig.write: Properties
	get(): Properties {
		return properties(
			user = username,
			password = password,
			database = name,
			url = url,
			portNumber = 5433
		)
	}

internal val DatabaseConfig.read: Properties
	get(): Properties {
		return properties(
			user = username,
			password = password,
			database = name,
			url = url,
			portNumber = portNumber
		)
	}

private fun properties(
	user: String,
	password: String,
	database: String,
	url: String,
	portNumber: Int?
): Properties {
	return Properties().apply {
		setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
		setProperty("dataSource.user", user)
		setProperty("dataSource.password", password)
		setProperty("dataSource.databaseName", database)
		setProperty("dataSource.serverName", url)
		portNumber?.let {
			setProperty("dataSource.portNumber", it.toString())
		}
	}
}
