package hr_platform.coding_task.controllers;

import hr_platform.coding_task.models.Skill;
import hr_platform.coding_task.services.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/skill")
public class SkillRestController {

    private final SkillService skillService;

    public SkillRestController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<Skill> addSkill(@RequestBody Skill skill) {
        return ResponseEntity.ok(skillService.create(skill));
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Skill>> getSkillByName(@PathVariable String name) {
        List<Skill> response = skillService.findByNameStartingWith(name);
        return Optional.of(response)
                .filter(responseList -> !responseList.isEmpty())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

}
