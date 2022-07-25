package dev.su.domain.compute;

import dev.su.domain.dataflow.ObjectInstanceId;

public interface FeatureResultRepository {

    void save(FeatureName feature, ObjectInstanceId objectInstanceId, FeatureValue value);

    FeatureValue getFeatureValue(FeatureName feature, ObjectInstanceId objectInstanceId);
}
