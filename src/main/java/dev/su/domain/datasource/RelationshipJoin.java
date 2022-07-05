package dev.su.domain.datasource;

import lombok.Value;

@Value(staticConstructor = "of")
public class RelationshipJoin {
    String name;
    RelationshipJoinCondition joinCondition;
    Cardinality cardinality;
    JoinType joinType;

    public enum Cardinality {
        ONE,
        MANY
    }

    public enum JoinType {
        INNER,
        LEFT
    }

    public String toString() {
        return joinCondition.getRootObjectName().getValue() +
                "." +
                joinCondition.getRootObjectField().getValue() +
                " = " +
                joinCondition.getJoinObjectName().getValue() +
                "." +
                joinCondition.getJoinObjectField().getValue();
    }

}
