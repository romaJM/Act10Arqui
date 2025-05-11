import java.util.ArrayList;
import java.util.List;
// Patrón Creacional: Factory Method
class Task {
    private String title;
    private String description;
    private boolean completed = false;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void markComplete() {
        this.completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return (completed ? "[Hecho]" : "[ ]") + " " + title + ": " + description;
    }
}

// Interfaz Factory
interface TaskFactory {
    Task createTask(String title, String description);
}

// Implementación del Factory Method
class SimpleTaskFactory implements TaskFactory {
    @Override
    public Task createTask(String title, String description) {
        return new Task(title, description);
    }
}

// Patrón Estructural: Decorator

abstract class TaskDecorator extends Task {
    protected Task task;

    public TaskDecorator(Task task) {
        super(task.getTitle(), "Decorado");
        this.task = task;
    }

    @Override
    public String toString() {
        return task.toString();
    }
}

class UrgentTaskDecorator extends TaskDecorator {
    public UrgentTaskDecorator(Task task) {
        super(task);
    }

    @Override
    public String toString() {
        return "[URGENTE] " + super.toString();
    }
}

// Patrón de Comportamiento: Command
interface Command {
    void execute();
}

class AddTaskCommand implements Command {
    private List<Task> taskList;
    private Task task;

    public AddTaskCommand(List<Task> taskList, Task task) {
        this.taskList = taskList;
        this.task = task;
    }

    public void execute() {
        taskList.add(task);
        System.out.println("Tarea agregada: " + task.getTitle());
    }
}

class CompleteTaskCommand implements Command {
    private Task task;

    public CompleteTaskCommand(Task task) {
        this.task = task;
    }

    public void execute() {
        task.markComplete();
        System.out.println("Tarea completada: " + task.getTitle());
    }
}

// Cliente: Aplicación de consola

public class Main {
    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();
        TaskFactory factory = new SimpleTaskFactory();

        // Crear tareas
        Task t1 = factory.createTask("Estudiar patrones", "Revisar patrones de diseño");
        Task t2 = factory.createTask("Subir tarea", "Subir el proyecto a Git");

        // Decorar tarea urgente
        Task urgentTask = new UrgentTaskDecorator(t2);

        // Comandos
        Command addT1 = new AddTaskCommand(tasks, t1);
        Command addUrgent = new AddTaskCommand(tasks, urgentTask);
        Command completeT1 = new CompleteTaskCommand(t1);

        // Ejecutar comandos
        addT1.execute();
        addUrgent.execute();
        completeT1.execute();

        // Mostrar tareas
        System.out.println("\n--- Lista de Tareas ---");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
}
