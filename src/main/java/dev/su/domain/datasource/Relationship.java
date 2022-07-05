package dev.su.domain.datasource;

import lombok.Data;

import java.util.List;

@Data(staticConstructor = "of")
public class Relationship {
    private final SourceObjectName rootObject;

    // TODO: Check name uniqueness
    // TODO: Check for circular joins
    private final List<RelationshipJoin> joins;

}


/*
select
rule.id,
avg(rule_execution.start_time - rule_schedule.window_end)
from
rule
join rule_schedule on rule.id = rule_schedule.rule_id
join rule_schedule_execution_association on rule_schedule_execution_association.rule_scheduled_id = rule_schedule.id
join rule_execution on rule_schedule_execution_association.rule_execution_id = rule_execution.id
where
rule_execution.is_first = true
group by rule.id;



Receive a rule_execution with id 123
rule_execution 123 ->
 get the rule_schedule_id of all rule_schedule_execution_association where rule_execution_id = 123
 get all rule_schedule where id in (rule_schedule_ids)
 get all rule.id whe

 */