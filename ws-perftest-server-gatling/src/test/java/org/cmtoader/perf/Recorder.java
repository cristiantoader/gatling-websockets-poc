package org.cmtoader.perf;

import io.gatling.recorder.GatlingRecorder;
import io.gatling.recorder.config.RecorderPropertiesBuilder;
import scala.Option;

public class Recorder {

    public static void main(String[] args) {
        RecorderPropertiesBuilder props = new RecorderPropertiesBuilder()
                .simulationFormatJava();

        GatlingRecorder.fromMap(props.build(), Option.empty());
    }
}
