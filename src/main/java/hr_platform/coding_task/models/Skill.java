package hr_platform.coding_task.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Skill implements Serializable {

    @Id
    @SequenceGenerator(name = "SKILL_ID_GENERATOR", sequenceName = "SKILL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SKILL_ID_GENERATOR")
    private Long id;

    @NotBlank(message = "Name cannot be empty.")
    @Column(unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "skillList")
    @JsonIgnore
    private Set<Candidate> candidateList;

    public Skill() {
    }

    public Skill(Long id, @NotBlank(message = "Name cannot be empty.") String name) {
        this.id = id;
        this.name = name;
    }

    public Skill(@NotBlank(message = "Name cannot be empty.") String name) {
        this.name = name;
    }


    public Skill(Long id, @NotBlank(message = "Name cannot be empty.") String name, Set<Candidate> candidateList) {
        this.id = id;
        this.name = name;
        this.candidateList = candidateList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Candidate> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(Set<Candidate> candidateList) {
        this.candidateList = candidateList;
    }
}
