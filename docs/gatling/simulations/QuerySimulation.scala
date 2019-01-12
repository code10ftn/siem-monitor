package siem

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class QuerySimulation extends Simulation {

  val httpConf = http
    .baseURL("https://192.168.1.146:8080")
    .acceptHeader("application/json")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("TestSimulation")

    .exec(http("QUERY")
      .post("/api/logs/search?page=0&size=30")
      .header("Content-Type", "application/json")
      .body(StringBody("""{"timestampStart": "2018-06-28T06:00", "timestampEnd": "2018-06-28T09:00", "severity": "WARNING", "system": "Windows"}"""))
    )

  setUp(
    scn.inject(atOnceUsers(100))
  ).protocols(httpConf)
}
