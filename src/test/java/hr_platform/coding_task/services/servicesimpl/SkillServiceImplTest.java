package hr_platform.coding_task.services.servicesimpl;

import hr_platform.coding_task.exception.SkillNameAlreadyExistsException;
import hr_platform.coding_task.models.Skill;
import hr_platform.coding_task.repositories.SkillRepository;
import hr_platform.coding_task.services.SkillService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)

public class SkillServiceImplTest {

    private SkillService skillService;
    private SkillRepository skillRepository;

    @Before
    public void setUp() {
        skillRepository = mock(SkillRepository.class);
        skillService = new SkillServiceImpl(skillRepository);
    }

    @Test
    public void testFindSkillsByName() {
        List<Skill> skills = new ArrayList<>();
        Skill skill = new Skill(1L, "sql");
        skills.add(skill);
        when(skillRepository.findByNameStartingWith("sq")).thenReturn(skills);
        List<Skill> result = skillService.findByNameStartingWith("sq");

        assertEquals(skills.size(), result.size());
        verify(skillRepository).findByNameStartingWith("sq");
        verifyNoMoreInteractions(skillRepository);
    }

    @Test
    public void testFindAllSkills() {
        List<Skill> skills = new ArrayList<>();

        Skill skill = new Skill(1L, "react");
        Skill skill1 = new Skill(2L, "angular");
        skills.add(skill);
        skills.add(skill1);
        when(skillRepository.findAll()).thenReturn(skills);
        List<Skill> result = skillService.findAll();

        assertEquals(skills.size(), result.size());
        verify(skillRepository).findAll();
        verifyNoMoreInteractions(skillRepository);

    }

    @Test
    public void testCreateSkill() {
        Skill skill = new Skill("react");
        Skill newSkill = new Skill(1L, "react");
        when(skillRepository.findAll()).thenReturn(Collections.emptyList());
        when(skillRepository.save(skill)).thenReturn(newSkill);
        Skill result = skillService.create(skill);

        assertEquals(newSkill.getName(), result.getName());
        assertEquals(newSkill.getId(), result.getId());
        assertEquals(newSkill.getCandidateList(), result.getCandidateList());
        assertNull(result.getCandidateList());

        verify(skillRepository).save(skill);
        verify(skillRepository).findAll();
        verifyNoMoreInteractions(skillRepository);
    }

    @Test(expected = SkillNameAlreadyExistsException.class)
    public void testCreateException() {
        Skill skill = new Skill("react");

        when(skillRepository.findAll()).thenReturn(Collections.singletonList(skill));
        when(skillRepository.save(skill)).thenThrow(new SkillNameAlreadyExistsException(skill.getName()));

        skillService.create(skill);

        verify(skillRepository).save(skill);
        verify(skillRepository).findAll();
        verifyNoMoreInteractions(skillRepository);
    }


}