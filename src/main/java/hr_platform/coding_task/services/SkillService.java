package hr_platform.coding_task.services;

import hr_platform.coding_task.models.Skill;

import java.util.List;

public interface SkillService extends AbstractService<Skill, Long> {

    List<Skill> findByNameStartingWith(String prefix);
    List<Skill> findAll();
    Skill create(Skill s);
}

