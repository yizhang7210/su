package dev.su.domain.compute;

import dev.su.domain.datasource.SourceObjectName;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryFeatureRepository implements FeatureRepository {

    private final Map<SourceObjectName, Set<Feature>> featureMap = new HashMap<>();

    @Override
    public void save(Feature feature) {
        if (!featureMap.containsKey(feature.getRootObject())) {
            featureMap.put(feature.getRootObject(), new HashSet<>());
        }

        featureMap.get(feature.getRootObject()).add(feature);
    }

    @Override
    public Collection<FeatureName> getFeaturesBySourceObject(SourceObjectName sourceObject) {
        return featureMap.get(sourceObject)
                .stream()
                .map(Feature::getName)
                .collect(Collectors.toSet());
    }

    public void clearAll() {
        featureMap.clear();
    }
}
