package hr_platform.coding_task.exception;

public class EntityDoesNotExistException extends RuntimeException {
    public EntityDoesNotExistException(Long id) {
        super("Entity with id : " + id + " does not exists.");
    }

}
