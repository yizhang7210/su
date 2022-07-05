package dev.su.domain.dataflow;

import com.fasterxml.jackson.databind.JsonNode;
import dev.su.domain.datasource.SourceObjectField;
import lombok.Value;

@Value(staticConstructor = "of")
public class ObjectInstance {
    ObjectInstanceId instanceId;
    JsonNode instanceContent;

    public Object getFieldValue(SourceObjectField field) {
        return field.castToType(instanceContent.get(field.getName().getValue()));
    }

}
