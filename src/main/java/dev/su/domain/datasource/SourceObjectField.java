package dev.su.domain.datasource;

import lombok.Value;

@Value(staticConstructor = "of")
public class SourceObjectField {
    String name;
    SourceObjectFieldType type;

    public enum SourceObjectFieldType {
        NUMBER,
        STRING,
        DATE,
        DATETIME
    }
}
