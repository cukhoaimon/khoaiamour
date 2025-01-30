package org.cukhoaimon

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info =
        Info(
            title = "domain",
            version = "0.0",
        ),
)
object Api

fun main(args: Array<String>) {
    run(*args)
}
