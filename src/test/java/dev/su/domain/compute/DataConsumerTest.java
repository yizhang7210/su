package dev.su.domain.compute;

import dev.su.domain.dataflow.InMemoryObjectInstanceRepository;
import dev.su.domain.datasource.InMemorySourceObjectRepository;
import dev.su.domain.datasource.SourceObjectName;
import dev.su.testutils.TestDataFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class DataConsumerTest {

    private final InMemorySourceObjectRepository sourceObjectRepository = new InMemorySourceObjectRepository();
    private final InMemoryFeatureRepository featureRepository = new InMemoryFeatureRepository();
    private final InMemoryObjectInstanceRepository objectInstanceRepository = new InMemoryObjectInstanceRepository();

    private final FeatureComputeTrigger featureComputeTrigger = mock(FeatureComputeTrigger.class);

    private final DataConsumer dataConsumer = new DataConsumer(
            sourceObjectRepository,
            featureRepository,
            objectInstanceRepository,
            featureComputeTrigger
    );

    @BeforeEach
    public void setUp() {
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.entityDefinition());
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.transactionDefinition());
        sourceObjectRepository.saveRelationshipDefinition(TestDataFactory.entityTransactionRelationship());
        sourceObjectRepository.saveRelationshipDefinition(TestDataFactory.transactionEntityRelationship());
    }

    @AfterEach
    public void tearDown() {
        sourceObjectRepository.clearAll();
        objectInstanceRepository.clearAll();
    }

    @Test
    void smoke_test() {

        // Given
        featureRepository.save(TestDataFactory.entityFirstNameFeature());
        featureRepository.save(TestDataFactory.entityLastWeekTransactionCountFeature());
        featureRepository.save(TestDataFactory.entityLastMonthTransactionVolumeFeature());

        // When
        dataConsumer.consumeNewRecord(
                SourceObjectName.of("entity"),
                TestDataFactory.exampleEntity()
        );

        // Then
        verify(featureComputeTrigger, times(3));

    }
}
