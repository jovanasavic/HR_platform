package hr_platform.coding_task.services.servicesimpl;

import hr_platform.coding_task.exception.EntityDoesNotExistException;
import hr_platform.coding_task.exception.SkillNameAlreadyExistsException;
import hr_platform.coding_task.models.Candidate;
import hr_platform.coding_task.models.Skill;
import hr_platform.coding_task.repositories.CandidateRepository;
import hr_platform.coding_task.repositories.SkillRepository;
import hr_platform.coding_task.services.CandidateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CandidateServiceImplTest {

    private CandidateService candidateService;
    private CandidateRepository candidateRepository;
    private SkillRepository skillRepository;

    @Before
    public void setUp() {
        skillRepository = mock(SkillRepository.class);
        candidateRepository = mock(CandidateRepository.class);
        candidateService = new CandidateServiceImpl(candidateRepository, skillRepository);
    }

    @Test
    public void testFindCandidateByName() {
        List<Candidate> candidateList = new ArrayList<>();
        Set<Skill> skillSet = new HashSet<>();
        Skill skill = new Skill("react");
        Skill skill1 = new Skill("angular");
        skillSet.add(skill);
        skillSet.add(skill1);

        Candidate candidate = new Candidate("milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        candidateList.add(candidate);
        when(candidateRepository.findByFullNameStartingWith("mi")).thenReturn(candidateList);
        List<Candidate> result = candidateService.findByFullNameStartingWith("mi");

        assertEquals(candidateList.size(), result.size());

        verify(candidateRepository).findByFullNameStartingWith("mi");
        verifyNoMoreInteractions(candidateRepository);

    }

    @Test
    public void testFindAllCandidates() {
        Skill skill = new Skill("react");
        Skill skill1 = new Skill("angular");
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(skill);
        skillSet.add(skill1);

        Candidate candidate = new Candidate("milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        Candidate candidate1 = new Candidate("marko", LocalDate.of(1992, 10, 15), "+381 626487548", "marko@gmail.com", skillSet);

        List<Candidate> candidateList = new ArrayList<>();
        candidateList.add(candidate);
        candidateList.add(candidate1);
        when(candidateRepository.findAll()).thenReturn(candidateList);
        List<Candidate> result = candidateService.findAll();

        assertEquals(candidateList.size(), result.size());

        verify(candidateRepository).findAll();
        verifyNoMoreInteractions(candidateRepository);
    }

    @Test
    public void testCreateCandidate() {
        Skill skill = new Skill("react");
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(skill);
        Candidate candidate = new Candidate("milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        Candidate newCandidate = new Candidate(1L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);

        when(candidateRepository.save(candidate)).thenReturn(newCandidate);

        Candidate result = candidateService.create(candidate);

        assertEquals(newCandidate.getFullName(), result.getFullName());
        assertEquals(newCandidate.getSkillList(), result.getSkillList());
        assertEquals(newCandidate.getContactNumber(), result.getContactNumber());
        assertEquals(newCandidate.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(newCandidate.getEmail(), result.getEmail());
        assertEquals(newCandidate.getId(), result.getId());
        assertEquals(newCandidate.getSkillList().size(), result.getSkillList().size());

        verify(candidateRepository).save(candidate);
        verifyNoMoreInteractions(candidateRepository);
    }


    @Test
    public void testGetCandidatesBySkill() {
        Skill skill = new Skill(1L, "angular");
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(skill);

        Candidate candidate = new Candidate(1L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        Candidate candidate1 = new Candidate(2L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        List<Candidate> candidateList = Arrays.asList(candidate, candidate1);
        when(candidateRepository.findBySkillListName(skill.getName())).thenReturn(candidateList);
        List<Candidate> result = candidateService.findBySkillListName(skill.getName());

        assertFalse(result.isEmpty());
        assertEquals(candidateList.size(), result.size());
        assertEquals(candidateList, result);

        verify(candidateRepository).findBySkillListName(skill.getName());
        verifyNoMoreInteractions(candidateRepository);
    }

    @Test
    public void testDeleteCandidate() {
        candidateService.delete(1L);
        verify(candidateRepository).deleteById(1L);
        verifyNoMoreInteractions(candidateRepository);
    }

    @Test
    public void testUpdateCandidateSkill() {
        Skill skill = new Skill(1L, "angular");
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(skill);

        Skill newSkill = new Skill(2L, "react");
        Set<Skill> newSkillSet = new HashSet<>();
        newSkillSet.add(skill);
        newSkillSet.add(newSkill);

        Candidate candidate = new Candidate(1L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        Candidate newCandidate = new Candidate(1L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", newSkillSet);
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
        when(skillRepository.findById(newSkill.getId())).thenReturn(Optional.of(newSkill));
        when(candidateRepository.save(candidate)).thenReturn(newCandidate);

        Candidate result = candidateService.updateCandidateSkill(candidate.getId(), newSkill.getId());

        assertEquals(newCandidate, result);
        verify(candidateRepository).findById(candidate.getId());
        verify(skillRepository).findById(newSkill.getId());
        verify(candidateRepository).save(candidate);
        verifyNoMoreInteractions(candidateRepository);
        verifyNoMoreInteractions(skillRepository);
    }
    @Test(expected = EntityDoesNotExistException.class)
    public void testUpdateCandidateSkillThrowsCandidateEntityDoesNotExistException() {
        when(candidateRepository.findById(1L)).thenThrow(new EntityDoesNotExistException(1L));
        candidateService.updateCandidateSkill(1L,1L);

        verify(candidateRepository).findById(1L);
        verifyNoMoreInteractions(candidateRepository);
        verifyNoInteractions(skillRepository);

    }

    @Test(expected = EntityDoesNotExistException.class)
    public void testUpdateCandidateSkillThrowsSkillEntityDoesNotExistException() {
        Skill skill = new Skill(1L, "react");
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(skill);

        Candidate candidate = new Candidate(1L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
        when(skillRepository.findById(1L)).thenThrow(new EntityDoesNotExistException(2L));
        candidateService.updateCandidateSkill(1L,2L);

        verify(candidateRepository).findById(1L);
        verify(skillRepository).findById(2L);
        verifyNoMoreInteractions(candidateRepository);
        verifyNoMoreInteractions(skillRepository);
    }

    @Test(expected = SkillNameAlreadyExistsException.class)
    public void testAddCandidateSkillThrowsSkillNameAlreadyExistsException() {
        Skill skill = new Skill(1L, "react");
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(skill);

        Candidate candidate = new Candidate(1L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        candidateService.updateCandidateSkill(1L,1L);

        verify(candidateRepository).findById(1L);
        verify(skillRepository).findById(2L);
        verifyNoMoreInteractions(candidateRepository);
        verifyNoMoreInteractions(skillRepository);
    }

    @Test
    public void testDeleteCandidateSkill() {
        Skill skill = new Skill(1L, "angular");
        Skill newSkill = new Skill(2L, "react");
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(skill);
        skillSet.add(newSkill);

        Set<Skill> newSkillSet = new HashSet<>();
        newSkillSet.add(skill);

        Candidate candidate = new Candidate(1L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        Candidate newCandidate = new Candidate(1L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", newSkillSet);
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
        when(skillRepository.findById(newSkill.getId())).thenReturn(Optional.of(newSkill));
        when(candidateRepository.save(candidate)).thenReturn(newCandidate);

        Candidate result = candidateService.deleteCandidateSkill(candidate.getId(), newSkill.getId());

        assertEquals(newCandidate, result);
        verify(candidateRepository).findById(candidate.getId());
        verify(skillRepository).findById(newSkill.getId());
        verify(candidateRepository).save(candidate);
        verifyNoMoreInteractions(candidateRepository);
        verifyNoMoreInteractions(skillRepository);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void testRemoveCandidateSkillThrowsCandidateEntityDoesNotExistException() {
        when(candidateRepository.findById(1L)).thenThrow(new EntityDoesNotExistException(1L));
        candidateService.deleteCandidateSkill(1L,1L);

        verify(candidateRepository).findById(1L);
        verifyNoMoreInteractions(candidateRepository);
        verifyNoInteractions(skillRepository);

    }

    @Test(expected = EntityDoesNotExistException.class)
    public void testRemoveCandidateSkillThrowsSkillEntityDoesNotExistException() {
        Skill skill = new Skill(1L, "react");
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(skill);

        Candidate candidate = new Candidate(1L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
        when(skillRepository.findById(1L)).thenThrow(new EntityDoesNotExistException(2L));
        candidateService.deleteCandidateSkill(1L,2L);

        verify(candidateRepository).findById(1L);
        verify(skillRepository).findById(2L);
        verifyNoMoreInteractions(candidateRepository);
        verifyNoMoreInteractions(skillRepository);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void testRemoveCandidateSkillThrowsSkillEntityDoesNotExistInCandidateException() {
        Skill skill = new Skill(1L, "react");
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(skill);

        Candidate candidate = new Candidate(1L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", new HashSet<>());
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        candidateService.deleteCandidateSkill(1L,1L);

        verify(candidateRepository).findById(1L);
        verify(skillRepository).findById(1L);
        verifyNoMoreInteractions(candidateRepository);
        verifyNoMoreInteractions(skillRepository);
    }


}