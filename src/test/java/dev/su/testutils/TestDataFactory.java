package dev.su.testutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.su.domain.compute.Feature;
import dev.su.domain.compute.FeatureDefinition;
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
                        SourceObjectField.of("id", SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of("given_name", SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of("family_name", SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of("date_of_birth", SourceObjectField.SourceObjectFieldType.DATE),
                        SourceObjectField.of("status", SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of("tax_id", SourceObjectField.SourceObjectFieldType.STRING)
                )
        );
    }

    public static SourceObjectDefinition transactionDefinition() {
        return SourceObjectDefinition.of(
                SourceObjectName.of("transaction"),
                List.of(
                        SourceObjectField.of("id", SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of("amount", SourceObjectField.SourceObjectFieldType.NUMBER),
                        SourceObjectField.of("sender_id", SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of("receiver_id", SourceObjectField.SourceObjectFieldType.STRING),
                        SourceObjectField.of("event_time", SourceObjectField.SourceObjectFieldType.DATETIME),
                        SourceObjectField.of("status", SourceObjectField.SourceObjectFieldType.STRING)
                )
        );
    }

    public static Relationship entityTransactionRelationship() {
        return Relationship.of(
                SourceObjectName.of("entity"),
                List.of(
                        RelationshipJoin.of(
                                "entity_transaction_join",
                                RelationshipJoinCondition.of(
                                        SourceObjectName.of("entity"),
                                        SourceObjectField.of("id", SourceObjectField.SourceObjectFieldType.STRING),
                                        SourceObjectName.of("transaction"),
                                        SourceObjectField.of("sender_id", SourceObjectField.SourceObjectFieldType.STRING),
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
                SourceObjectName.of("transaction"),
                List.of(
                        RelationshipJoin.of(
                                "transaction_entity_join",
                                RelationshipJoinCondition.of(
                                        SourceObjectName.of("transaction"),
                                        SourceObjectField.of("sender_id", SourceObjectField.SourceObjectFieldType.STRING),
                                        SourceObjectName.of("entity"),
                                        SourceObjectField.of("id", SourceObjectField.SourceObjectFieldType.STRING),
                                        RelationshipJoinCondition.JoinOperator.EQ
                                ),
                                RelationshipJoin.Cardinality.ONE,
                                RelationshipJoin.JoinType.INNER
                        ),
                        RelationshipJoin.of(
                                "transaction_entity_join",
                                RelationshipJoinCondition.of(
                                        SourceObjectName.of("transaction"),
                                        SourceObjectField.of("receiver_id", SourceObjectField.SourceObjectFieldType.STRING),
                                        SourceObjectName.of("entity"),
                                        SourceObjectField.of("id", SourceObjectField.SourceObjectFieldType.STRING),
                                        RelationshipJoinCondition.JoinOperator.EQ
                                ),
                                RelationshipJoin.Cardinality.ONE,
                                RelationshipJoin.JoinType.INNER
                        )
                )
        );
    }

    public static ObjectInstance exampleEntity() {
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
                                "status", "ACTIVE"
                        )
                )
        );
    }

    public static Feature entityFirstNameFeature() {
        return new Feature(
                "entity-first-name",
                SourceObjectName.of("entity"),
                FeatureDefinition.of()
        );
    }

    public static Feature entityLastWeekTransactionCountFeature() {
        return new Feature(
                "entity-last-week-transaction-count",
                SourceObjectName.of("entity"),
                FeatureDefinition.of()
        );
    }

    public static Feature entityLastMonthTransactionVolumeFeature() {
        return new Feature(
                "entity-last-month-transaction-volume",
                SourceObjectName.of("entity"),
                FeatureDefinition.of()
        );
    }

}
