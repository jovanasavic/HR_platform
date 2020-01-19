package hr_platform.coding_task.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Candidate implements Serializable {

    @Id
    @SequenceGenerator(name = "CANDIDATE_ID_GENERATOR", sequenceName ="CANDIDATE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CANDIDATE_ID_GENERATOR")
    private Long id;

    @NotBlank(message = "Full name cannot be empty.")
    private String fullName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Format of number must be +1 123456789")
    private String contactNumber;

    @Email(message = "Email is not valid")
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "candidate_skill",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skillList;

    public Candidate() {
    }

    public Candidate(Long id, @NotBlank(message = "Full name cannot be empty.") String fullName, LocalDate dateOfBirth, @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Format of number must be +1 123456789") String contactNumber, @Email(message = "Email is not valid") String email, Set<Skill> skillList) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.email = email;
        this.skillList = skillList;
    }

    public Candidate(@NotBlank(message = "Full name cannot be empty.") String fullName, @NotBlank(message = "Date of birth cannot be empty.") LocalDate dateOfBirth, @Pattern(regexp = "^\\\\+(?:[0-9] ?){6,14}[0-9]$", message = "Format of number must be +1 123456789") String contactNumber, @Email(message = "Email is not valid") String email, Set<Skill> skillList) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.email = email;
        this.skillList = skillList;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(Set<Skill> skillList) {
        this.skillList = skillList;
    }
}
