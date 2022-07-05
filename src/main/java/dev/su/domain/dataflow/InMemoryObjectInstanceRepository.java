package dev.su.domain.dataflow;

import dev.su.domain.datasource.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class InMemoryObjectInstanceRepository implements ObjectInstanceRepository {

    private final Map<SourceObjectName, List<ObjectInstance>> objectStore = new HashMap<>();

    @Override
    public void saveObject(ObjectInstance objectInstance, SourceObjectName sourceObjectName) {
        if (!objectStore.containsKey(sourceObjectName)) {
            objectStore.put(sourceObjectName, new ArrayList<>());
        }

        objectStore.get(sourceObjectName).add(objectInstance);
    }

    @Override
    public Collection<ObjectInstanceId> getAffectedObjectIds(
            SourceObjectName changedObjectName,
            ObjectInstance changedObjectInstance,
            SourceObjectDenormalizedView objectDenormalizedView
    ) {

        Collection<SourceObjectDenormalizedView> changedObjectView = objectDenormalizedView
                .findSubViewsBySourceObjectName(changedObjectName);

        if (changedObjectView.isEmpty()) {
            return Collections.emptySet();
        }

        Set<ObjectInstanceId> affectedObjectIds = new HashSet<>();

        for (SourceObjectDenormalizedView currentView : changedObjectView) {
            log.info("Identified denormalized view to detect affected objects: {}", currentView);

            Set<Object> currentFieldValues;
            Set<ObjectInstance> currentObjectInstances = new HashSet<>();
            currentObjectInstances.add(changedObjectInstance);

            while (currentView.getParent() != null) {
                SourceObjectDefinition current = currentView.getCurrent();

                SourceObjectDenormalizedView parentView = currentView.getParent();
                SourceObjectDefinition parent = parentView.getCurrent();

                for (RelationshipJoin join : parentView.getDenormalizedViews().keySet()) {
                    if (parentView.getDenormalizedViews().get(join) == currentView) {

                        // Get parent field
                        SourceObjectFieldName parentFieldName = join.getJoinCondition().getRootObjectField();
                        SourceObjectField parentField = parent.getFieldByName(parentFieldName).orElseThrow(
                                () -> new DataSpecificationError(String.format(
                                        "The field %s does not exist on the object %s",
                                        parentFieldName.getValue(),
                                        parent.getName()
                                ))
                        );

                        // Get current field and its value
                        SourceObjectFieldName currentFieldName = join.getJoinCondition().getJoinObjectField();
                        SourceObjectField currentField = current.getFieldByName(currentFieldName).orElseThrow(
                                () -> new DataSpecificationError(String.format(
                                        "The field %s does not exist on the object %s",
                                        currentFieldName.getValue(),
                                        current.getName()
                                ))
                        );

                        log.info("Following the join {}.{} = {}.{}",
                                parent.getName().getValue(),
                                parentFieldName.getValue(),
                                current.getName().getValue(),
                                currentFieldName.getValue()
                        );

                        currentFieldValues = currentObjectInstances
                                .stream()
                                .map(o -> o.getFieldValue(currentField))
                                .collect(Collectors.toSet());

                        currentObjectInstances = Set.copyOf(
                                getObjectsByFieldsContainment(parent.getName(), Map.of(
                                        parentField, currentFieldValues
                                )));
                    }
                }

                currentView = parentView;
            }

            affectedObjectIds.addAll(
                    currentObjectInstances.
                            stream()
                            .map(ObjectInstance::getInstanceId)
                            .collect(Collectors.toSet())
            );

        }

        return affectedObjectIds;
    }

    @Override
    public Collection<ObjectInstance> getObjectsByFieldsEquality(SourceObjectName objectName, Map<SourceObjectField, Object> queryFields) {
        return objectStore.getOrDefault(objectName, List.of())
                .stream()
                .filter(
                        object -> queryFields.keySet().stream().allMatch(
                                field -> object.getFieldValue(field).equals(queryFields.get(field))
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ObjectInstance> getObjectsByFieldsContainment(SourceObjectName objectName, Map<SourceObjectField, Collection<Object>> queryFields) {
        return objectStore.getOrDefault(objectName, List.of())
                .stream()
                .filter(
                        object -> queryFields.keySet().stream().allMatch(
                                field -> queryFields.get(field).contains(object.getFieldValue(field))
                        )
                )
                .collect(Collectors.toList());
    }

    public void clearAll() {
        objectStore.clear();
    }
}
