package hr_platform.coding_task.exception;

public class SkillNameAlreadyExistsException extends RuntimeException  {
    public SkillNameAlreadyExistsException(String name) {
        super("Skill with name : " + name + " already exists.");
    }
}
