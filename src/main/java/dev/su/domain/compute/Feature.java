package dev.su.domain.compute;

import dev.su.domain.datasource.SourceObjectName;
import lombok.Value;

@Value
public class Feature {
    FeatureName name;
    SourceObjectName rootObject;
    FeatureDefinition definition;
}
