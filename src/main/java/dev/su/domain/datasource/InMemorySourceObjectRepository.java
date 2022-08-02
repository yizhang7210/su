package dev.su.domain.datasource;

import java.util.*;
import java.util.stream.Collectors;

public class InMemorySourceObjectRepository implements SourceObjectRepository {

    private final Map<SourceObjectName, SourceObjectDefinition> sourceObjectsMap = new HashMap<>();
    private final Map<RelationshipName, Relationship> relationshipsMap = new HashMap<>();

    @Override
    public void saveObjectDefinition(SourceObjectDefinition objectDefinition) {
        sourceObjectsMap.put(objectDefinition.getName(), objectDefinition);
    }

    @Override
    public void saveRelationshipDefinition(Relationship relationship) {
        relationshipsMap.put(relationship.getName(), relationship);
    }

    @Override
    public Relationship getRelationshipByName(RelationshipName relationshipName) {
        return relationshipsMap.get(relationshipName);
    }

    @Override
    public SourceObjectDefinition getSourceObjectDefinitionByName(SourceObjectName objectName) {
        return sourceObjectsMap.get(objectName);
    }

    @Override
    public Collection<SourceObjectDefinition> getAllSourceObjectDefinitions() {
        return sourceObjectsMap.values();
    }

    @Override
    public Collection<Relationship> getRelationshipsBySourceObject(SourceObjectName objectName) {
        return relationshipsMap.values()
                .stream()
                .filter(relationship -> relationship.getRootObject().equals(objectName))
                .collect(Collectors.toList());
    }

    public void clearAll() {
        sourceObjectsMap.clear();
        relationshipsMap.clear();
    }
}
