package dev.su.domain.dataflow;

import dev.su.domain.datasource.SourceObjectName;

public interface ObjectInstanceRepository {
    void saveObject(ObjectInstance objectInstance, SourceObjectName sourceObjectName);
}
