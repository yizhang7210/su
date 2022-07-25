package dev.su.domain.compute;

import com.google.common.collect.Sets;
import dev.su.domain.dataflow.ObjectInstance;
import dev.su.domain.dataflow.ObjectInstanceRepository;
import dev.su.domain.datasource.SourceObjectDefinition;
import dev.su.domain.datasource.SourceObjectDenormalizer;
import dev.su.domain.datasource.SourceObjectName;
import dev.su.domain.datasource.SourceObjectRepository;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class DataConsumer {
    private final SourceObjectRepository sourceObjectRepository;
    private final FeatureRepository featureRepository;
    private final ObjectInstanceRepository objectInstanceRepository;
    private final FeatureCalculator featureCalculator;
    private final SourceObjectDenormalizer sourceObjectDenormalizer;

    public void consumeNewRecord(SourceObjectName objectName, ObjectInstance objectInstance) {
        // TODO: Validate the objectInstance is indeed an "objectName" object
        objectInstanceRepository.saveObject(objectInstance, objectName);

        // Part 1: Trigger calculation of features of this new object
        featureRepository.getFeaturesBySourceObject(objectName)
                .forEach(
                        feature -> featureCalculator.calculateFeature(feature, objectInstance.getInstanceId())
                );

        // Part 2: Trigger calculation of features for objects that are affected by the update
        sourceObjectRepository.getAllSourceObjectDefinitions()
                .stream()
                .map(SourceObjectDefinition::getName)
                .forEach(
                        rootObjectName -> sourceObjectRepository
                                .getRelationshipsBySourceObject(rootObjectName)
                                .stream()
                                .map(relationship ->
                                        objectInstanceRepository.getAffectedObjectIds(
                                                objectName,
                                                objectInstance,
                                                sourceObjectDenormalizer.convertToDenormalizedView(relationship)
                                        )
                                )
                                .map(Set::copyOf)
                                .reduce(Sets::union)
                                .orElse(Set.of())
                                .forEach(objectId ->
                                        featureRepository.getFeaturesBySourceObject(rootObjectName)
                                                .forEach(feature ->
                                                        featureCalculator.calculateFeature(feature, objectId))
                                )
                );

    }


}
