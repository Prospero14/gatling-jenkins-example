package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Debug extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://computer-database.gatling.io")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

    ScenarioBuilder scn = scenario("Debug Simulation")
            .exec(http("Home Page").get("/"));

    {
        setUp(
            scn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}
