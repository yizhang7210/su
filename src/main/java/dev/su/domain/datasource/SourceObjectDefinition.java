package dev.su.domain.datasource;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data(staticConstructor = "of")
public class SourceObjectDefinition {
    private final SourceObjectName name;
    private final List<SourceObjectField> fields;

    private List<SourceObjectFieldName> idFields;

    public void setIdFields(List<SourceObjectFieldName> idFields) {
        List<SourceObjectFieldName> allFieldNames = fields
                .stream()
                .map(SourceObjectField::getName)
                .collect(Collectors.toList());

        for (SourceObjectFieldName field : idFields) {
            if (!allFieldNames.contains(field)) {
                throw new DataSpecificationError(String.format(
                        "Can not designate field %s to be an id field as it's not in the list of fields",
                        field.getValue()
                ));
            }
        }
    }

    public Optional<SourceObjectField> getFieldByName(SourceObjectFieldName fieldName) {
        return fields.stream()
                .filter(field -> field.getName().equals(fieldName))
                .findFirst();
    }

}
