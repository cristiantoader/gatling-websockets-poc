package org.cmtoader.perf.simulation;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.ws;

public class BdsGuiWebsocketsLatencySimulation extends Simulation {

    // https://community.gatling.io/t/websocket-matching-api-different-order-of-responses/7838
    // https://community.gatling.io/t/websocket-send-message-to-one-connection-and-await-response-from-another/8477/3

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080")
            .wsBaseUrl("ws://localhost:8080");

    ScenarioBuilder writerScenario = scenario("write-scenario")
            .exec(ws("Connect BDS").wsName("bds-ws").connect("/websocket/bds-register"))
            .exec(ws("Connect GUI").wsName("gui-ws").connect("/websocket/gui-register"))
            .repeat(1_000, "id").on(
                    exec(ws("BDS send message").wsName("bds-ws")
                            .sendText("Hello, I'm #{id} and this is message #{id}!")

                    ).exec(ws("check message client").wsName("gui-ws")
                            .sendText("A").await(2000).on(
                                ws.checkTextMessage("gui-receive-text").check(bodyString().saveAs("message")))

                    ).exec(session -> {
                        System.out.println("TS123: " + session.getString("message"));
                        return session;
                    })
            )
            .exec(ws("Close BDS WS").wsName("bds-ws").close())
            .exec(ws("Close GUI WS").wsName("gui-ws").close());

    {
        setUp(writerScenario.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
    }
}
