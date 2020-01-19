package hr_platform.coding_task.services.servicesimpl;

import hr_platform.coding_task.exception.SkillNameAlreadyExistsException;
import hr_platform.coding_task.models.Skill;
import hr_platform.coding_task.repositories.SkillRepository;
import hr_platform.coding_task.services.SkillService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<Skill> findByNameStartingWith(String prefix) {
        return skillRepository.findByNameStartingWith(prefix);
    }

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public Skill create(Skill skill) {
        boolean nameExists = false;
        List<Skill> skills = new ArrayList<>(skillRepository.findAll());

        for (Skill s : skills) {
            if (s.getName().equals(skill.getName())) {
                nameExists = true;
                break;
            }
        }
        if (!nameExists) {
            return skillRepository.save(skill);
        } else {
            throw new SkillNameAlreadyExistsException(skill.getName());
        }
    }


}
