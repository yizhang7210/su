package dev.su.domain.datasource;

import lombok.Value;

@Value(staticConstructor = "of")
public class RelationshipName {
    String value;
}
