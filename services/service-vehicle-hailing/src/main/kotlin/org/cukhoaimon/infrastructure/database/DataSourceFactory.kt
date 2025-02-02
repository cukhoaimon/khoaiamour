package org.cukhoaimon.infrastructure.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.util.Properties
import javax.sql.DataSource

internal interface DataSourceFactory {
	fun create(kind: DatabaseKind, dbConfig: DatabaseConfig, pool: ConnectionPoolConfig): DataSource

	companion object {
		val HIKARI = object : DataSourceFactory {
			override fun create(kind: DatabaseKind, dbConfig: DatabaseConfig, pool: ConnectionPoolConfig): DataSource {
				return HikariDataSource(
					pool.hikariConfig(
						if (kind == DatabaseKind.WRITE) {
							dbConfig.write
						} else {
							dbConfig.read
						}
					),
				)
			}

			/**
			 * A quick note transaction isolation levels:
			 * dirty read: A transaction reads data written by a concurrent uncommitted transaction.
			 *
			 * nonrepeatable read: A transaction re-reads data it has previously read and finds that data has been modified by another transaction (that committed since the initial read).
			 *
			 * phantom read: A transaction re-executes a query returning a set of rows that satisfy a search condition and finds that the set of rows satisfying the condition has changed due to another recently-committed transaction.
			 *
			 * serialization anomaly: The result of successfully committing a group of transactions is inconsistent with all possible orderings of running those transactions one at a time.
			 *
			 * | Isolation Level  | Dirty Read             | Nonrepeatable Read | Phantom Read           | Serialization Anomaly |
			 * |------------------|------------------------|--------------------|------------------------|-----------------------|
			 * | Read uncommitted | Allowed, but not in PG | Possible           | Possible               | Possible              |
			 * | Read committed   | Not possible           | Possible           | Possible               | Possible              |
			 * | Repeatable read  | Not possible           | Not possible       | Allowed, but not in PG | Possible              |
			 * | Serializable     | Not possible           | Not possible       | Not possible           | Not possible          |
			 */
			fun ConnectionPoolConfig.hikariConfig(properties: Properties): HikariConfig {
				val config = HikariConfig(properties)
				config.connectionTimeout = connectionTimeout
				config.maximumPoolSize = maxPoolSize
				config.maxLifetime = maxLifetime
				config.minimumIdle = maxPoolSize
				config.idleTimeout = idleTimeout
				config.transactionIsolation = "TRANSACTION_READ_COMMITTED"
				config.leakDetectionThreshold = 60_000
				return config
			}
		}
	}
}