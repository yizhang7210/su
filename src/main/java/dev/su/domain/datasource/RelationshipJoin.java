package dev.su.domain.datasource;

import lombok.Value;

@Value(staticConstructor = "of")
public class RelationshipJoin {
    private String name;
    private RelationshipJoinCondition joinCondition;
    private Cardinality cardinality;
    private JoinType joinType;

    public enum Cardinality {
        ONE,
        MANY
    }

    public enum JoinType {
        INNER,
        LEFT
    }

}
