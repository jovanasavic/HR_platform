package hr_platform.coding_task.repositories;

import hr_platform.coding_task.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByFullNameStartingWith (String prefix);
    List<Candidate> findBySkillListName(String name);

}
