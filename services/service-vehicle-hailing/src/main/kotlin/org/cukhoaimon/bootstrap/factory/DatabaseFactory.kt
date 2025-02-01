package org.cukhoaimon.bootstrap.factory

import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import org.cukhoaimon.infrastructure.database.DatabaseConfig
import org.cukhoaimon.infrastructure.database.DatabaseContext

@Factory
class DatabaseFactory {
	@Singleton
	fun provideDatabase(): DatabaseContext {
		return DatabaseContext
			.Builder(
				config = DatabaseConfig()
			)
			.build()
	}
}
