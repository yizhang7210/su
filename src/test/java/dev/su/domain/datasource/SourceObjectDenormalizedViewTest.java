package dev.su.domain.datasource;

import dev.su.testutils.TestDataFactory;
import org.junit.jupiter.api.Test;


public class SourceObjectDenormalizedViewTest {

    private static final SourceObjectRepository sourceObjectRepository = new InMemorySourceObjectRepository();


    @Test
    void test_source_object_denormalized_view() {

        sourceObjectRepository.saveObjectDefinition(TestDataFactory.entityDefinition());
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.transactionDefinition());
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.addressDefinition());

        SourceObjectDenormalizer denormalizer = new SourceObjectDenormalizer(sourceObjectRepository);
        SourceObjectDenormalizedView view = denormalizer.convertToDenormalizedView(TestDataFactory.transactionEntityRelationship());

        System.out.println(view);
    }
}
