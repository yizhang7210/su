package dev.su.domain.dataflow;

import dev.su.domain.datasource.SourceObjectName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryObjectInstanceRepository implements ObjectInstanceRepository {

    private final Map<SourceObjectName, List<ObjectInstance>> objectStore = new HashMap<>();

    @Override
    public void saveObject(ObjectInstance objectInstance, SourceObjectName sourceObjectName) {
        if (!objectStore.containsKey(sourceObjectName)) {
            objectStore.put(sourceObjectName, new ArrayList<>());
        }

        objectStore.get(sourceObjectName).add(objectInstance);
    }

    public void clearAll() {
        objectStore.clear();
    }
}
