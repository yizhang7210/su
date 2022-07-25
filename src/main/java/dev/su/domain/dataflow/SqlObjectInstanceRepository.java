package dev.su.domain.dataflow;

import dev.su.domain.datasource.SourceObjectDenormalizedView;
import dev.su.domain.datasource.SourceObjectField;
import dev.su.domain.datasource.SourceObjectName;

import java.util.Collection;
import java.util.Map;

public class SqlObjectInstanceRepository implements ObjectInstanceRepository {

    @Override
    public void saveObject(ObjectInstance objectInstance, SourceObjectName sourceObjectName) {
        // Example query when selecting affected transactions by entity address
        /*
        select
            distinct transaction.id
        from
            transaction
            join entity as sender_entity on sender_entity.id = transaction.sender_entity_id
            join entity as receiver_entity on receiver_entity.id = transaction.receiver_entity_id
            join address as sender_address on sender_entity.id = sender_address.entity_id
            join address as receiver_address on receiver_entity.id = receiver_address.entity_id
        where
            sender_address.id = <supplied-address-id> OR
            receiver_address.id = <supplied-address-id>
         */
    }

    @Override
    public Collection<ObjectInstanceId> getAffectedObjectIds(SourceObjectName changedObjectName, ObjectInstance changedObjectInstance, SourceObjectDenormalizedView objectDenormalizedView) {
        return null;
    }

    @Override
    public Collection<ObjectInstance> getObjectsByFieldsEquality(SourceObjectName objectName, Map<SourceObjectField, Object> queryFields) {
        return null;
    }

    @Override
    public Collection<ObjectInstance> getObjectsByFieldsContainment(SourceObjectName objectName, Map<SourceObjectField, Collection<Object>> queryFields) {
        return null;
    }

}
