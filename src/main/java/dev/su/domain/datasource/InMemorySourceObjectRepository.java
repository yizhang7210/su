package dev.su.domain.datasource;

import java.util.*;

public class InMemorySourceObjectRepository implements SourceObjectRepository {

    private final Map<SourceObjectName, SourceObjectDefinition> sourceObjectsMap = new HashMap<>();
    private final Map<SourceObjectName, Set<Relationship>> relationshipsMap = new HashMap<>();

    @Override
    public void saveObjectDefinition(SourceObjectDefinition objectDefinition) {
        sourceObjectsMap.put(objectDefinition.getName(), objectDefinition);
    }

    @Override
    public void saveRelationshipDefinition(Relationship relationship) {
        if (!relationshipsMap.containsKey(relationship.getRootObject())) {
            relationshipsMap.put(relationship.getRootObject(), new HashSet<>());
        }

        relationshipsMap.get(relationship.getRootObject()).add(relationship);
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
        return relationshipsMap.getOrDefault(objectName, Set.of());
    }

    public void clearAll() {
        sourceObjectsMap.clear();
        relationshipsMap.clear();
    }
}
