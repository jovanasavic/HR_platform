package hr_platform.coding_task.repositories;

import hr_platform.coding_task.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByNameStartingWith (String prefix);

}
