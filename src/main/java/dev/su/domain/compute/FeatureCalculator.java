package dev.su.domain.compute;

import dev.su.domain.dataflow.ObjectInstanceId;

public interface FeatureCalculator {
    void calculateFeature(FeatureName feature, ObjectInstanceId instanceId);
}
