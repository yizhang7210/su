package dev.su.domain.dataflow;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

@Value(staticConstructor = "of")
public class ObjectInstance {
    ObjectInstanceId instanceId;
    JsonNode instanceContent;
}
