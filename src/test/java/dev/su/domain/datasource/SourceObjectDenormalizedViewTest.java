package dev.su.domain.datasource;

import dev.su.testutils.TestDataFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SourceObjectDenormalizedViewTest {

    private static final SourceObjectRepository sourceObjectRepository = new InMemorySourceObjectRepository();


    @Test
    void test_transaction_entity_denormalized_view() {
        // Given
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.entityDefinition());
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.transactionDefinition());

        // When
        SourceObjectDenormalizer denormalizer = new SourceObjectDenormalizer(sourceObjectRepository);
        SourceObjectDenormalizedView transactionView = denormalizer.convertToDenormalizedView(TestDataFactory.transactionEntityRelationship());

        // Then
        // Root is transaction
        assertEquals("transaction", transactionView.getCurrent().getName().getValue());

        // joins the senders and receivers correctly
        assertEquals(2, transactionView.getDenormalizedViews().keySet().size());

        RelationshipJoin senderJoin = TestDataFactory.transactionEntityRelationship().getJoinByName("sender_join");
        RelationshipJoin receiverJoin = TestDataFactory.transactionEntityRelationship().getJoinByName("receiver_join");

        assertTrue(transactionView.getDenormalizedViews().containsKey(senderJoin));
        assertTrue(transactionView.getDenormalizedViews().containsKey(receiverJoin));

        assertEquals("entity", transactionView.getDenormalizedViews().get(senderJoin).getCurrent().getName().getValue());
        assertEquals("entity", transactionView.getDenormalizedViews().get(receiverJoin).getCurrent().getName().getValue());
    }

    @Test
    void test_transaction_entity_denormalized_view_with_address() {
        // Given
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.entityDefinition());
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.transactionDefinition());
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.addressDefinition());

        // When
        SourceObjectDenormalizer denormalizer = new SourceObjectDenormalizer(sourceObjectRepository);
        SourceObjectDenormalizedView transactionView = denormalizer.convertToDenormalizedView(TestDataFactory.transactionEntityRelationshipWithAddress());

        // Then
        // Root is transaction
        assertEquals("transaction", transactionView.getCurrent().getName().getValue());

        // joins the senders and receivers correctly
        assertEquals(2, transactionView.getDenormalizedViews().keySet().size());

        RelationshipJoin senderJoin = TestDataFactory.transactionEntityRelationship().getJoinByName("sender_join");
        RelationshipJoin receiverJoin = TestDataFactory.transactionEntityRelationship().getJoinByName("receiver_join");
        SourceObjectDenormalizedView senderEntityView = transactionView.getDenormalizedViews().get(senderJoin);
        SourceObjectDenormalizedView receiverEntityView = transactionView.getDenormalizedViews().get(receiverJoin);

        assertEquals("entity", senderEntityView.getCurrent().getName().getValue());
        assertEquals("entity", receiverEntityView.getCurrent().getName().getValue());

        // joins the sender and receiver addresses correctly too
        assertEquals(1, senderEntityView.getDenormalizedViews().keySet().size());
        RelationshipJoin senderAddressJoin = TestDataFactory.transactionEntityRelationshipWithAddress().getJoinByName("sender_address_join");
        assertEquals("address", senderEntityView.getDenormalizedViews().get(senderAddressJoin).getCurrent().getName().getValue());

        assertEquals(1, receiverEntityView.getDenormalizedViews().keySet().size());
        RelationshipJoin receiverAddressJoin = TestDataFactory.transactionEntityRelationshipWithAddress().getJoinByName("receiver_address_join");
        assertEquals("address", receiverEntityView.getDenormalizedViews().get(receiverAddressJoin).getCurrent().getName().getValue());
    }

    @Test
    void test_entity_transaction_denormalized_view() {
        // Given
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.entityDefinition());
        sourceObjectRepository.saveObjectDefinition(TestDataFactory.transactionDefinition());

        // When
        SourceObjectDenormalizer denormalizer = new SourceObjectDenormalizer(sourceObjectRepository);
        SourceObjectDenormalizedView entityView = denormalizer.convertToDenormalizedView(TestDataFactory.entityTransactionRelationship());

        // Then
        // Root is transaction
        assertEquals("entity", entityView.getCurrent().getName().getValue());

        // joins the senders and receivers correctly
        assertEquals(1, entityView.getDenormalizedViews().keySet().size());

        RelationshipJoin transactionJoin = TestDataFactory.entityTransactionRelationship().getJoinByName("entity_transaction_join");
        SourceObjectDenormalizedView transactionView = entityView.getDenormalizedViews().get(transactionJoin);
        assertEquals("transaction", transactionView.getCurrent().getName().getValue());

    }

}
