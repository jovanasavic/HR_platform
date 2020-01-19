package hr_platform.coding_task.controllers;

import hr_platform.coding_task.models.Candidate;
import hr_platform.coding_task.services.CandidateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidate")
public class CandidateRestController {
    private final CandidateService candidateService;

   public CandidateRestController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @ApiOperation(value = "Returns all candidates from database.")
    @GetMapping
    public ResponseEntity <List<Candidate>> getAllCandidates(){
        return ResponseEntity.ok(candidateService.findAll());
    }

    @ApiOperation(value = "Adds candidates to database.")
    @PostMapping
    public ResponseEntity<Candidate> addCandidate(@Valid @RequestBody Candidate candidate){
        return ResponseEntity.ok(candidateService.create(candidate));
    }

    @ApiOperation(value = "Removes candidates from database.")
    @DeleteMapping("/{id}")
    public void deleteCandidate (@PathVariable Long id){
        candidateService.delete(id);
    }

    @ApiOperation(value = "Returns all candidates from database which name starts with given string.")
    @GetMapping("/{name}")
    public ResponseEntity<List<Candidate>> getCandidateByName(@PathVariable String name){
        List<Candidate> response = candidateService.findByFullNameStartingWith(name);
        return Optional.of(response)
                .filter(responseList -> !responseList.isEmpty())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @ApiOperation(value = "Adds skill to candidate with given id.")
    @PutMapping("/{id}/updateSkills/{skillId}")
    public ResponseEntity<Candidate> updateCandidateSkills (@PathVariable Long id, @PathVariable Long skillId){
       return ResponseEntity.ok(candidateService.updateCandidateSkill(id, skillId));
    }

    @ApiOperation(value = "Removes skill with given id from candidate.")
    @PutMapping("/{candidateId}/skill/{skillId}")
    public ResponseEntity<Candidate> deleteCandidateSkill(@PathVariable Long candidateId, @PathVariable Long skillId){
       return ResponseEntity.ok(candidateService.deleteCandidateSkill(candidateId, skillId));
    }

    @ApiOperation(value = "Returns all candidates with given skill.")
    @GetMapping("/skill/{name}")
    public ResponseEntity<List<Candidate>> getCandidatesBySkill (@PathVariable String name){
        List<Candidate> response = candidateService.findBySkillListName(name);
        return Optional.of(response)
                .filter(responseList -> !responseList.isEmpty())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
