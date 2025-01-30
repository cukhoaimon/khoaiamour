package org.cukhoaimon

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/domain")
class DomainController {
    @Get(uri = "/", produces = ["text/plain"])
    fun index(): String = "Example Response"
}
