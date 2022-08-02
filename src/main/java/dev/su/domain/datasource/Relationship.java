package dev.su.domain.datasource;

import com.google.common.collect.MoreCollectors;
import lombok.Data;

import java.util.List;

@Data(staticConstructor = "of")
public class Relationship {
    private final RelationshipName name;
    private final SourceObjectName rootObject;

    // TODO: Check name uniqueness
    // TODO: Check for circular joins
    private final List<RelationshipJoin> joins;

    public RelationshipJoin getJoinByName(String name) {
        return joins
                .stream()
                .filter(join -> join.getName().equals(name))
                .collect(MoreCollectors.onlyElement());
    }

}
