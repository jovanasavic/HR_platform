package hr_platform.coding_task.services.servicesimpl;

import hr_platform.coding_task.exception.EntityDoesNotExistException;
import hr_platform.coding_task.exception.SkillNameAlreadyExistsException;
import hr_platform.coding_task.models.Candidate;
import hr_platform.coding_task.models.Skill;
import hr_platform.coding_task.repositories.CandidateRepository;
import hr_platform.coding_task.repositories.SkillRepository;
import hr_platform.coding_task.services.CandidateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final SkillRepository skillRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository, SkillRepository skillRepository) {
        this.candidateRepository = candidateRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public List<Candidate> findByFullNameStartingWith(String prefix) {
        return candidateRepository.findByFullNameStartingWith(prefix);
    }

    @Override
    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate create(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public void delete(Long id) {
        candidateRepository.deleteById(id);
    }

    @Override
    public Candidate updateCandidateSkill(Long id, Long skillId) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException(id));

        Skill newCandidateSkill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityDoesNotExistException(id));

        boolean containSkill = candidate.getSkillList().contains(newCandidateSkill);

        if (containSkill) {
            throw new SkillNameAlreadyExistsException(newCandidateSkill.getName());
        } else {
            Set<Skill> oldSkills = candidate.getSkillList();
            oldSkills.add(newCandidateSkill);
            candidate.setSkillList(oldSkills);
            return candidateRepository.save(candidate);
        }


    }


    @Override
    public Candidate deleteCandidateSkill(Long candidateId, Long skillId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityDoesNotExistException(candidateId));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityDoesNotExistException(skillId));

        boolean containSkill = candidate.getSkillList().contains(skill);

        if (containSkill) {
            candidate.getSkillList().remove(skill);
            return candidateRepository.save(candidate);
        } else {
            throw new EntityDoesNotExistException(skillId);
        }
    }

    @Override
    public List<Candidate> findBySkillListName(String name) {
        return candidateRepository.findBySkillListName(name);
    }
}
