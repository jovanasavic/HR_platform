package hr_platform.coding_task.services;

import hr_platform.coding_task.models.Candidate;

import java.util.List;

public interface CandidateService extends AbstractService<Candidate, Long> {

    List<Candidate> findByFullNameStartingWith(String prefix);
    List<Candidate> findAll();
    Candidate create(Candidate c);
    void delete(Long id);
    Candidate updateCandidateSkill(Long id, Long skillId);
    Candidate deleteCandidateSkill(Long candidateId, Long skillId);
    List<Candidate> findBySkillListName(String name);
}
