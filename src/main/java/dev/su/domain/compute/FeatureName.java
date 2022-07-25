package dev.su.domain.compute;

import lombok.Value;

@Value(staticConstructor = "of")
public class FeatureName {
    String value;
}
