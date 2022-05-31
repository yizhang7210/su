package dev.su.domain.compute;

import dev.su.domain.dataflow.ObjectInstance;
import dev.su.domain.dataflow.ObjectInstanceRepository;
import dev.su.domain.datasource.SourceObjectDefinition;
import dev.su.domain.datasource.SourceObjectName;
import dev.su.domain.datasource.SourceObjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DataConsumer {
    private final SourceObjectRepository sourceObjectRepository;
    private final FeatureRepository featureRepository;
    private final ObjectInstanceRepository objectInstanceRepository;
    private final FeatureComputeTrigger computeTrigger;

    public void consumeNewRecord(SourceObjectName objectName, ObjectInstance objectInstance) {
        // TODO: Validate the objectInstance is indeed an "objectName" object

        objectInstanceRepository.saveObject(objectInstance, objectName);

        SourceObjectDefinition objectDefinition = sourceObjectRepository.getSourceObjectDefinitionByName(objectName);

        featureRepository.getFeaturesBySourceObject(objectName)
                .forEach(
                        feature -> computeTrigger.triggerCalculate(feature, objectInstance.getInstanceId())
                );

        // TODO: Calculate all features where this record contributes to
    }


}
