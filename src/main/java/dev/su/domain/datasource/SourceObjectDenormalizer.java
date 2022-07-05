package dev.su.domain.datasource;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SourceObjectDenormalizer {

    private final SourceObjectRepository sourceObjectRepository;

    public SourceObjectDenormalizedView convertToDenormalizedView(Relationship relationship) {
        SourceObjectName rootObjectName = relationship.getRootObject();
        SourceObjectDefinition rootObjectDefinition = sourceObjectRepository.getSourceObjectDefinitionByName(rootObjectName);

        SourceObjectDenormalizedView rootView = new SourceObjectDenormalizedView(rootObjectDefinition);

        Map<SourceObjectName, SourceObjectDenormalizedView> objectNameToViewMap = new HashMap<>();
        objectNameToViewMap.put(rootObjectName, rootView);

        for (RelationshipJoin join : relationship.getJoins()) {
            SourceObjectName leftObjectName = join.getJoinCondition().getRootObjectName();
            SourceObjectName leftObjectAlias = join.getJoinCondition().getRootObjectAlias();
            SourceObjectName rightObjectName = join.getJoinCondition().getJoinObjectName();
            SourceObjectName rightObjectAlias = join.getJoinCondition().getJoinObjectAlias();

            if (objectNameToViewMap.containsKey(leftObjectAlias)) {
                SourceObjectDenormalizedView leftView = objectNameToViewMap.get(leftObjectAlias);

                SourceObjectDefinition rightObjectDefinition = sourceObjectRepository.getSourceObjectDefinitionByName(rightObjectName);
                SourceObjectDenormalizedView rightView = new SourceObjectDenormalizedView(rightObjectDefinition);
                rightView.setParent(leftView);

                leftView.addView(join, rightView);

                objectNameToViewMap.put(rightObjectAlias, rightView);

            } else {
                throw new DataSpecificationError(String.format(
                        "Trying to join an object %s to a previous non-existent object %s.",
                        rightObjectName.getValue(),
                        leftObjectName.getValue()
                ));
            }


        }

        return rootView;
    }

}
