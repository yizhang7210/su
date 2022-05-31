package dev.su.domain.compute;

import dev.su.domain.datasource.SourceObjectName;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Collection<Feature> getAllFeatures() {
        return featureMap.values().stream().reduce(
                (featureSet1, featureSet2) -> Stream.concat(
                        featureSet1.stream(),
                        featureSet2.stream()
                ).collect(Collectors.toSet())
        ).orElse(Set.of());
    }

    @Override
    public Collection<Feature> getFeaturesBySourceObject(SourceObjectName sourceObject) {
        return featureMap.get(sourceObject);
    }

    public void clearAll() {
        featureMap.clear();
    }
}
