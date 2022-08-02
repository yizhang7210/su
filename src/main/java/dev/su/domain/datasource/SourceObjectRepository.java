package dev.su.domain.datasource;

import java.util.Collection;

public interface SourceObjectRepository {

    void saveObjectDefinition(SourceObjectDefinition objectDefinition);

    SourceObjectDefinition getSourceObjectDefinitionByName(SourceObjectName objectName);

    Collection<SourceObjectDefinition> getAllSourceObjectDefinitions();

    void saveRelationshipDefinition(Relationship relationship);

    Relationship getRelationshipByName(RelationshipName relationshipName);

    Collection<Relationship> getRelationshipsBySourceObject(SourceObjectName objectName);

}
