package dev.su.testutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.su.domain.compute.Feature;
import dev.su.domain.compute.FeatureDefinition;
import dev.su.domain.compute.FeatureName;
import dev.su.domain.dataflow.ObjectInstance;
import dev.su.domain.dataflow.ObjectInstanceId;
import dev.su.domain.datasource.*;

import java.util.List;
import java.util.Map;

public class TestDataFactory {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static SourceObjectDefinition entityDefinition() {
        return SourceObjectDefinition.of(
                SourceObjectName.of("entity"),
                List.of(
                        SourceObjectField.of(SourceObjectFieldName.of("id"), SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of(SourceObjectFieldName.of("given_name"), SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of(SourceObjectFieldName.of("family_name"), SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of(SourceObjectFieldName.of("date_of_birth"), SourceObjectField.SourceObjectFieldType.DATE),
                        SourceObjectField.of(SourceObjectFieldName.of("status"), SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of(SourceObjectFieldName.of("tax_id"), SourceObjectField.SourceObjectFieldType.STRING)
                )
        );
    }

    public static SourceObjectDefinition transactionDefinition() {
        return SourceObjectDefinition.of(
                SourceObjectName.of("transaction"),
                List.of(
                        SourceObjectField.of(SourceObjectFieldName.of("id"), SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of(SourceObjectFieldName.of("amount"), SourceObjectField.SourceObjectFieldType.DOUBLE),
                        SourceObjectField.of(SourceObjectFieldName.of("sender_id"), SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of(SourceObjectFieldName.of("receiver_id"), SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of(SourceObjectFieldName.of("event_time"), SourceObjectField.SourceObjectFieldType.DATETIME),
                        SourceObjectField.of(SourceObjectFieldName.of("status"), SourceObjectField.SourceObjectFieldType.STRING)
                )
        );
    }

    public static SourceObjectDefinition addressDefinition() {
        return SourceObjectDefinition.of(
                SourceObjectName.of("address"),
                List.of(
                        SourceObjectField.of(SourceObjectFieldName.of("id"), SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of(SourceObjectFieldName.of("street_address"), SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of(SourceObjectFieldName.of("city"), SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of(SourceObjectFieldName.of("country"), SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of(SourceObjectFieldName.of("entity_id"), SourceObjectField.SourceObjectFieldType.STRING)
                )
        );
    }

    public static Relationship entityTransactionRelationship() {
        return Relationship.of(
                RelationshipName.of("denormalized_entity"),
                SourceObjectName.of("entity"),
                List.of(
                        RelationshipJoin.of(
                                "entity_transaction_join",
                                RelationshipJoinCondition.of(
                                        SourceObjectName.of("entity"),
                                        SourceObjectFieldName.of("id"),
                                        SourceObjectName.of("entity"),
                                        SourceObjectName.of("transaction"),
                                        SourceObjectFieldName.of("sender_id"),
                                        SourceObjectName.of("transaction"),
                                        RelationshipJoinCondition.JoinOperator.EQ
                                ),
                                RelationshipJoin.Cardinality.MANY,
                                RelationshipJoin.JoinType.INNER
                        )
                )
        );
    }

    public static Relationship transactionEntityRelationshipWithAddress() {
        return Relationship.of(
                RelationshipName.of("denormalized_transaction_with_entity_address"),
                SourceObjectName.of("transaction"),
                List.of(
                        RelationshipJoin.of(
                                "sender_join",
                                RelationshipJoinCondition.of(
                                        SourceObjectName.of("transaction"),
                                        SourceObjectFieldName.of("sender_id"),
                                        SourceObjectName.of("transaction"),
                                        SourceObjectName.of("entity"),
                                        SourceObjectFieldName.of("id"),
                                        SourceObjectName.of("sender_entity"),
                                        RelationshipJoinCondition.JoinOperator.EQ
                                ),
                                RelationshipJoin.Cardinality.ONE,
                                RelationshipJoin.JoinType.INNER
                        ),
                        RelationshipJoin.of(
                                "receiver_join",
                                RelationshipJoinCondition.of(
                                        SourceObjectName.of("transaction"),
                                        SourceObjectFieldName.of("receiver_id"),
                                        SourceObjectName.of("transaction"),
                                        SourceObjectName.of("entity"),
                                        SourceObjectFieldName.of("id"),
                                        SourceObjectName.of("receiver_entity"),
                                        RelationshipJoinCondition.JoinOperator.EQ
                                ),
                                RelationshipJoin.Cardinality.ONE,
                                RelationshipJoin.JoinType.INNER
                        ),
                        RelationshipJoin.of(
                                "sender_address_join",
                                RelationshipJoinCondition.of(
                                        SourceObjectName.of("entity"),
                                        SourceObjectFieldName.of("id"),
                                        SourceObjectName.of("sender_entity"),
                                        SourceObjectName.of("address"),
                                        SourceObjectFieldName.of("entity_id"),
                                        SourceObjectName.of("sender_address"),
                                        RelationshipJoinCondition.JoinOperator.EQ
                                ),
                                RelationshipJoin.Cardinality.MANY,
                                RelationshipJoin.JoinType.INNER
                        ),
                        RelationshipJoin.of(
                                "receiver_address_join",
                                RelationshipJoinCondition.of(
                                        SourceObjectName.of("entity"),
                                        SourceObjectFieldName.of("id"),
                                        SourceObjectName.of("receiver_entity"),
                                        SourceObjectName.of("address"),
                                        SourceObjectFieldName.of("entity_id"),
                                        SourceObjectName.of("receiver_address"),
                                        RelationshipJoinCondition.JoinOperator.EQ
                                ),
                                RelationshipJoin.Cardinality.MANY,
                                RelationshipJoin.JoinType.INNER
                        )
                )
        );
    }

    public static Relationship transactionEntityRelationship() {
        return Relationship.of(
                RelationshipName.of("denormalized_transaction"),
                SourceObjectName.of("transaction"),
                List.of(
                        RelationshipJoin.of(
                                "sender_join",
                                RelationshipJoinCondition.of(
                                        SourceObjectName.of("transaction"),
                                        SourceObjectFieldName.of("sender_id"),
                                        SourceObjectName.of("transaction"),
                                        SourceObjectName.of("entity"),
                                        SourceObjectFieldName.of("id"),
                                        SourceObjectName.of("sender_entity"),
                                        RelationshipJoinCondition.JoinOperator.EQ
                                ),
                                RelationshipJoin.Cardinality.ONE,
                                RelationshipJoin.JoinType.INNER
                        ),
                        RelationshipJoin.of(
                                "receiver_join",
                                RelationshipJoinCondition.of(
                                        SourceObjectName.of("transaction"),
                                        SourceObjectFieldName.of("receiver_id"),
                                        SourceObjectName.of("transaction"),
                                        SourceObjectName.of("entity"),
                                        SourceObjectFieldName.of("id"),
                                        SourceObjectName.of("receiver_entity"),
                                        RelationshipJoinCondition.JoinOperator.EQ
                                ),
                                RelationshipJoin.Cardinality.ONE,
                                RelationshipJoin.JoinType.INNER
                        )
                )
        );
    }


    public static ObjectInstance exampleSenderEntity() {
        return ObjectInstance.of(
                ObjectInstanceId.of("entity-123"),
                objectMapper.valueToTree(
                        Map.of(
                                "id", "entity-123",
                                "given_name", "John",
                                "family_name", "Smith",
                                "date_of_birth", "1990-01-01",
                                "status", "ACTIVE",
                                "tax_id", "1234567890"
                        )
                )
        );
    }

    public static ObjectInstance exampleReceiverEntity() {
        return ObjectInstance.of(
                ObjectInstanceId.of("entity-789"),
                objectMapper.valueToTree(
                        Map.of(
                                "id", "entity-789",
                                "given_name", "Robert",
                                "family_name", "Johnson",
                                "date_of_birth", "1993-01-01",
                                "status", "ACTIVE",
                                "tax_id", "0987654321"
                        )
                )
        );
    }

    public static ObjectInstance exampleThirdEntity() {
        return ObjectInstance.of(
                ObjectInstanceId.of("entity-abc"),
                objectMapper.valueToTree(
                        Map.of(
                                "id", "entity-abc",
                                "given_name", "Emma",
                                "family_name", "Thompson",
                                "date_of_birth", "1980-09-01",
                                "status", "ACTIVE",
                                "tax_id", "999888777"
                        )
                )
        );
    }

    public static ObjectInstance exampleSenderAddress() {
        return ObjectInstance.of(
                ObjectInstanceId.of("address-123"),
                objectMapper.valueToTree(
                        Map.of(
                                "id", "address-123",
                                "street_address", "10 Downing Street",
                                "city", "London",
                                "country", "UK",
                                "entity_id", "entity-123"
                        )
                )
        );
    }

    public static ObjectInstance exampleReceiverAddress() {
        return ObjectInstance.of(
                ObjectInstanceId.of("address-789"),
                objectMapper.valueToTree(
                        Map.of(
                                "id", "address-789",
                                "street_address", "99 Upping Street",
                                "city", "London",
                                "country", "UK",
                                "entity_id", "entity-789"
                        )
                )
        );
    }

    public static ObjectInstance exampleTransaction() {
        return ObjectInstance.of(
                ObjectInstanceId.of("transaction-123"),
                objectMapper.valueToTree(
                        Map.of(
                                "id", "transaction-123",
                                "amount", 1200,
                                "sender_id", "entity-123",
                                "receiver_id", "entity-789",
                                "event_time", "2021-12-07 01:23:45",
                                "status", "COMPLETE"
                        )
                )
        );
    }

    public static ObjectInstance exampleSecondTransaction() {
        return ObjectInstance.of(
                ObjectInstanceId.of("transaction-456"),
                objectMapper.valueToTree(
                        Map.of(
                                "id", "transaction-456",
                                "amount", 400,
                                "sender_id", "entity-123",
                                "receiver_id", "entity-abc",
                                "event_time", "2022-04-07 01:23:45",
                                "status", "PENDING"
                        )
                )
        );
    }

    public static Feature entityFirstNameFeature() {
        return new Feature(
                FeatureName.of("entity-first-name"),
                SourceObjectName.of("entity"),
                FeatureDefinition.of()
        );
    }

    public static Feature entityLastWeekTransactionCountFeature() {
        return new Feature(
                FeatureName.of("entity-last-week-transaction-count"),
                SourceObjectName.of("entity"),
                FeatureDefinition.of()
        );
    }

    public static Feature entityLastMonthTransactionVolumeFeature() {
        return new Feature(
                FeatureName.of("entity-last-month-transaction-volume"),
                SourceObjectName.of("entity"),
                FeatureDefinition.of()
        );
    }

}
