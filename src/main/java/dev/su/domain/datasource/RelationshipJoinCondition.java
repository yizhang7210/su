package dev.su.domain.datasource;

import lombok.Value;

@Value(staticConstructor = "of")
public class RelationshipJoinCondition {

    SourceObjectName rootObjectName;
    SourceObjectFieldName rootObjectField;
    SourceObjectName rootObjectAlias;

    SourceObjectName joinObjectName;
    SourceObjectFieldName joinObjectField;
    SourceObjectName joinObjectAlias;

    // TODO: Make this a recursive boolean formula
    JoinOperator operator;

    public enum JoinOperator {
        EQ,
    }

}
