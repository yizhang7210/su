package dev.su.domain.compute;

import dev.su.domain.datasource.SourceObjectName;

import java.util.Collection;

public interface FeatureRepository {

    void save(Feature feature);

    Collection<FeatureName> getFeaturesBySourceObject(SourceObjectName sourceObject);

}
