package dev.su.domain.dataflow;

import dev.su.domain.datasource.SourceObjectField;
import dev.su.domain.datasource.SourceObjectName;
import dev.su.domain.datasource.SourceObjectDenormalizedView;

import java.util.Collection;
import java.util.Map;

public interface ObjectInstanceRepository {
    void saveObject(ObjectInstance objectInstance, SourceObjectName sourceObjectName);

    Collection<ObjectInstanceId> getAffectedObjectIds(
            SourceObjectName changedObjectName,
            ObjectInstance changedObjectInstance,
            SourceObjectDenormalizedView objectDenormalizedView
    );

    Collection<ObjectInstance> getObjectsByFieldsEquality(SourceObjectName objectName, Map<SourceObjectField, Object> queryFields);

    Collection<ObjectInstance> getObjectsByFieldsContainment(SourceObjectName objectName, Map<SourceObjectField, Collection<Object>> queryFields);

}
