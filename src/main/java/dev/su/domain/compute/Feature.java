package dev.su.domain.compute;

import dev.su.domain.datasource.SourceObjectName;
import lombok.Value;

@Value
public class Feature {
    String name;
    SourceObjectName rootObject;
    FeatureDefinition definition;
}
