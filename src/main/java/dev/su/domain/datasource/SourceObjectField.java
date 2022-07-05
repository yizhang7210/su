package dev.su.domain.datasource;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class SourceObjectField {
    SourceObjectFieldName name;
    SourceObjectFieldType type;

    // TODO: Do not allow name to contain "/"
    public enum SourceObjectFieldType {
        LONG,
        DOUBLE,
        STRING,
        DATE,
        DATETIME
    }

    public Object castToType(JsonNode jsonNode) {
        if (type == SourceObjectFieldType.LONG) {
            return jsonNode.longValue();
        } else if (type == SourceObjectFieldType.DOUBLE) {
            return jsonNode.doubleValue();
        } else if (type == SourceObjectFieldType.STRING) {
            return jsonNode.textValue();
        } else if (type == SourceObjectFieldType.DATE) {
            return LocalDate.parse(jsonNode.textValue());
        } else {
            return LocalDateTime.parse(jsonNode.textValue());
        }
    }

}
