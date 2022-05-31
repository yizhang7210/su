package dev.su.domain.datasource;

import lombok.Data;

import java.util.List;

@Data(staticConstructor = "of")
public class Relationship {
    private final SourceObjectName rootObject;
    private final List<RelationshipJoin> joins;
}
