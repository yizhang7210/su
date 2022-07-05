package dev.su.domain.datasource;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Getter
public class SourceObjectDenormalizedView {
    private final SourceObjectDefinition current;
    private SourceObjectDenormalizedView parent;
    private final Map<RelationshipJoin, SourceObjectDenormalizedView> denormalizedViews;

    public SourceObjectDenormalizedView(SourceObjectDefinition current) {
        this.current = current;
        this.denormalizedViews = new HashMap<>();
    }

    public void addView(RelationshipJoin join, SourceObjectDenormalizedView subView) {
        this.denormalizedViews.put(join, subView);
    }

    public void setParent(SourceObjectDenormalizedView newParent) {
        this.parent = newParent;
    }

    public Collection<SourceObjectDenormalizedView> findSubViewsBySourceObjectName(SourceObjectName sourceObjectName) {
        log.info("Finding subview by {}", sourceObjectName);

        List<SourceObjectDenormalizedView> results = new ArrayList<>();

        if (this.current.getName().equals(sourceObjectName)) {
            return List.of();
        }

        for (SourceObjectDenormalizedView view : denormalizedViews.values()) {
            if (view.current.getName().equals(sourceObjectName)) {
                results.add(view);
            }
            results.addAll(view.findSubViewsBySourceObjectName(sourceObjectName));
        }

        return results;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nroot = ").append(current.getName().getValue()).append("\n");
        denormalizedViews.forEach(
                (join, denormalizedView) ->
                        stringBuilder
                                .append("    nested join: (")
                                .append(join.toString())
                                .append(") => \n")
                                .append(
                                        String.join("\n",
                                                Arrays.stream(denormalizedView.toString()
                                                        .split("\n"))
                                                        .map(line -> "    " + line)
                                                        .toList()
                                        )
                                )
                                .append("\n")

        );
        return stringBuilder.toString();
    }

}
