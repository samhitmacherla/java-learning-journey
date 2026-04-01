package library_management;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private List<Student> students = new ArrayList<>();
    private int nextId = 1;

    public StudentController() {
        students.add(new Student(nextId++, "Samhit", 21, "samhit@gmail.com"));
        students.add(new Student(nextId++, "Rahul", 22, "rahul@gmail.com"));
        students.add(new Student(nextId++, "Priya", 20, "priya@gmail.com"));
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        student.setId(nextId++);
        students.add(student);
        return student;
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Integer id, @RequestBody Student updated) {
        for (Student s : students) {
            if (s.getId() == id) {
                s.setName(updated.getName());
                s.setAge(updated.getAge());
                s.setEmail(updated.getEmail());
                return s;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        students.removeIf(s -> s.getId() == id);
        return "Student with id " + id + " deleted successfully";
    }
}