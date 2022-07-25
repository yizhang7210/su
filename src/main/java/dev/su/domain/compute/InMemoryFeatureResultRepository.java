package dev.su.domain.compute;

import dev.su.domain.dataflow.ObjectInstanceId;

import java.util.HashMap;
import java.util.Map;

public class InMemoryFeatureResultRepository implements FeatureResultRepository {

    private final Map<ObjectInstanceId, Map<FeatureName, FeatureValue>> featureResultsMap = new HashMap<>();

    @Override
    public void save(FeatureName feature, ObjectInstanceId objectInstanceId, FeatureValue value) {
        if (!featureResultsMap.containsKey(objectInstanceId)) {
            featureResultsMap.put(objectInstanceId, new HashMap<>());
        }
        featureResultsMap.get(objectInstanceId).put(feature, value);
    }

    @Override
    public FeatureValue getFeatureValue(FeatureName feature, ObjectInstanceId objectInstanceId) {
        return featureResultsMap.getOrDefault(objectInstanceId, Map.of()).get(feature);
    }
}
