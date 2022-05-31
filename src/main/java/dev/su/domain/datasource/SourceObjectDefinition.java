package dev.su.domain.datasource;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Data(staticConstructor = "of")
public class SourceObjectDefinition {
    private final SourceObjectName name;
    private final List<SourceObjectField> fields;
}
