package dev.su.domain.dataflow;

import lombok.Value;

@Value(staticConstructor = "of")
public class ObjectInstanceId {
    String Id;
}

