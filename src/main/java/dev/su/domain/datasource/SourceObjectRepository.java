package dev.su.domain.datasource;

import java.util.Collection;

public interface SourceObjectRepository {

    void saveObjectDefinition(SourceObjectDefinition objectDefinition);

    void saveRelationshipDefinition(Relationship relationship);

    SourceObjectDefinition getSourceObjectDefinitionByName(SourceObjectName objectName);

    Collection<SourceObjectDefinition> getAllSourceObjectDefinitions();

    Collection<Relationship> getRelationshipsBySourceObject(SourceObjectName objectName);
}
