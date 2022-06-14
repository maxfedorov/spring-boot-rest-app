package com.example.springbootrestapp.gatling.simulations

import com.example.springbootrestapp.config.Config.getPort
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration.DurationInt

class GetUserSimulation extends Simulation {

  val httpConf: HttpProtocolBuilder = http.baseUrl(String.format("http://localhost:%s/api/v1/", getPort))
    .header("Accept", "application/json")

  val scn: ScenarioBuilder = scenario("Get user")
    .during(10.seconds) {
      exec(http("Get user")
        .get("users/1")
        .check(jsonPath("$.id").is("1"))
        .check(jsonPath("$.firstName").is("John"))
        .check(jsonPath("$.lastName").is("Parker"))
        .check(jsonPath("$.email").is("jp@email.com"))
        .check(status.is(200)))
    }

  setUp(
    scn.inject(
      atOnceUsers(1),
      rampUsers(10) during 5.seconds
    ).protocols(httpConf)
  ).maxDuration(10.seconds)
    .assertions(
      global.successfulRequests.percent.gt(95),
      global.responseTime.max.lt(5000)
    )
}