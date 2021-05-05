package pl.edu.utp.pz1.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.utp.pz1.model.Project;
import pl.edu.utp.pz1.model.Student;
import pl.edu.utp.pz1.model.Task;
import pl.edu.utp.pz1.model.TaskState;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class InitService {

    private ProjectService projectService;
    private TaskService taskService;
    private StudentService studentService;
    private PasswordEncoder passwordEncoder;

    public InitService(ProjectService projectService, TaskService taskService,
                       StudentService studentService, PasswordEncoder passwordEncoder) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeData() {
        Project project1 = new Project("Programowanie zwinne", "opis");
        Project project2 = new Project("Programowanie obiektowe", "opis2");
        Project project3 = new Project("Programowanie współbieżne", "opis3");

        project1 = projectService.create(project1);
        project2 = projectService.create(project2);
        project3 = projectService.create(project3);

        Student student = new Student(null, "Jan", "Kowalski", "111000", true, "jankow@wp.pl", passwordEncoder.encode("Qwerty.1"), Set.of(project1, project2, project3));
        student = studentService.create(student);

        for (int i = 0; i < 10; i++) {
            Task task = new Task(null, "zadanie" + i, "opis" + i, i, TaskState.TODO, LocalDateTime.now(), project1, student);
            taskService.create(task);
        }

    }
}
