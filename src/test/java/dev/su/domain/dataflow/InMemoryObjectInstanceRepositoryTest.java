package dev.su.domain.dataflow;

import dev.su.domain.datasource.*;
import dev.su.testutils.TestDataFactory;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class InMemoryObjectInstanceRepositoryTest {

    private static final InMemoryObjectInstanceRepository inMemorySourceObjectRepository = new InMemoryObjectInstanceRepository();
    private static final SourceObjectRepository sourceObjectRepository = new InMemorySourceObjectRepository();
    private static final SourceObjectDenormalizer sourceObjectDenormalizer = new SourceObjectDenormalizer(sourceObjectRepository);


    @Test
    void test_one_root_object_affected_by_downstream_objects() {

        sourceObjectRepository.saveObjectDefinition(TestDataFactory.entityDefinition());
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.transactionDefinition());
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.addressDefinition());

        SourceObjectDenormalizedView transactionView = sourceObjectDenormalizer.convertToDenormalizedView(TestDataFactory.transactionEntityRelationshipWithAddress());

        SourceObjectName transaction = SourceObjectName.of("transaction");
        SourceObjectName entity = SourceObjectName.of("entity");
        SourceObjectName address = SourceObjectName.of("address");

        inMemorySourceObjectRepository.saveObject(TestDataFactory.exampleTransaction(), transaction);
        inMemorySourceObjectRepository.saveObject(TestDataFactory.exampleSenderEntity(), entity);
        inMemorySourceObjectRepository.saveObject(TestDataFactory.exampleReceiverEntity(), entity);
        inMemorySourceObjectRepository.saveObject(TestDataFactory.exampleSenderAddress(), address);
        inMemorySourceObjectRepository.saveObject(TestDataFactory.exampleReceiverAddress(), address);

        // The addition of the new entities should affect the transaction
        assertEquals(1, inMemorySourceObjectRepository.getAffectedObjectIds(
                entity,
                TestDataFactory.exampleSenderEntity(),
                transactionView
        ).size());

        assertEquals("transaction-123", inMemorySourceObjectRepository.getAffectedObjectIds(
                entity,
                TestDataFactory.exampleSenderEntity(),
                transactionView
        ).stream().toList().get(0).getId());

        assertEquals(1, inMemorySourceObjectRepository.getAffectedObjectIds(
                entity,
                TestDataFactory.exampleReceiverEntity(),
                transactionView
        ).size());

        assertEquals("transaction-123", inMemorySourceObjectRepository.getAffectedObjectIds(
                entity,
                TestDataFactory.exampleReceiverEntity(),
                transactionView
        ).stream().toList().get(0).getId());

        // The addition of the new addresses should also affect the transaction
        assertEquals(1, inMemorySourceObjectRepository.getAffectedObjectIds(
                address,
                TestDataFactory.exampleSenderAddress(),
                transactionView
        ).size());

        assertEquals("transaction-123", inMemorySourceObjectRepository.getAffectedObjectIds(
                address,
                TestDataFactory.exampleSenderAddress(),
                transactionView
        ).stream().toList().get(0).getId());

        assertEquals(1, inMemorySourceObjectRepository.getAffectedObjectIds(
                address,
                TestDataFactory.exampleReceiverAddress(),
                transactionView
        ).size());

        assertEquals("transaction-123", inMemorySourceObjectRepository.getAffectedObjectIds(
                address,
                TestDataFactory.exampleReceiverAddress(),
                transactionView
        ).stream().toList().get(0).getId());

    }

    @Test
    void test_multiple_root_objects_affected_by_single_downstream_object() {

        sourceObjectRepository.saveObjectDefinition(TestDataFactory.entityDefinition());
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.transactionDefinition());

        SourceObjectDenormalizedView transactionView = sourceObjectDenormalizer.convertToDenormalizedView(TestDataFactory.transactionEntityRelationship());

        SourceObjectName transaction = SourceObjectName.of("transaction");
        SourceObjectName entity = SourceObjectName.of("entity");

        inMemorySourceObjectRepository.saveObject(TestDataFactory.exampleTransaction(), transaction);
        inMemorySourceObjectRepository.saveObject(TestDataFactory.exampleSenderEntity(), entity);
        inMemorySourceObjectRepository.saveObject(TestDataFactory.exampleReceiverEntity(), entity);
        inMemorySourceObjectRepository.saveObject(TestDataFactory.exampleSecondTransaction(), transaction);
        inMemorySourceObjectRepository.saveObject(TestDataFactory.exampleThirdEntity(), entity);


        // The addition of the the first entity should affect both transactions
        List<ObjectInstanceId> senderAffectedTransactions = inMemorySourceObjectRepository.getAffectedObjectIds(
                entity,
                TestDataFactory.exampleSenderEntity(),
                transactionView
        )
                .stream()
                .sorted(Comparator.comparing(ObjectInstanceId::getId))
                .collect(Collectors.toList());

        assertEquals(2, senderAffectedTransactions.size());

        assertEquals("transaction-123", senderAffectedTransactions.get(0).getId());
        assertEquals("transaction-456", senderAffectedTransactions.get(1).getId());

        // Receiver entity should only affect one transaction
        assertEquals(1, inMemorySourceObjectRepository.getAffectedObjectIds(
                entity,
                TestDataFactory.exampleReceiverEntity(),
                transactionView
        ).size());

        assertEquals("transaction-123", inMemorySourceObjectRepository.getAffectedObjectIds(
                entity,
                TestDataFactory.exampleReceiverEntity(),
                transactionView
        ).stream().toList().get(0).getId());

        // Third entity should only affect one transaction
        assertEquals(1, inMemorySourceObjectRepository.getAffectedObjectIds(
                entity,
                TestDataFactory.exampleThirdEntity(),
                transactionView
        ).size());

        assertEquals("transaction-456", inMemorySourceObjectRepository.getAffectedObjectIds(
                entity,
                TestDataFactory.exampleThirdEntity(),
                transactionView
        ).stream().toList().get(0).getId());


    }
}
