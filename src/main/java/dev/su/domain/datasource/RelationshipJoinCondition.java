package dev.su.domain.datasource;

import lombok.Value;

@Value(staticConstructor = "of")
public class RelationshipJoinCondition {

    SourceObjectName rootObjectName;
    SourceObjectField rootObjectField;

    SourceObjectName joinObjectName;
    SourceObjectField joinObjectField;

    JoinOperator operator;

    public enum JoinOperator {
        EQ,
        LT,
        LE,
        GT,
        GE
    }

}
