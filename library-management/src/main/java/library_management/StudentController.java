package library_management;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;

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
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new StudentNotFoundException(id));
    }

    @PostMapping
    public Student createStudent(@Valid @RequestBody Student student) {
        student.setId(nextId++);
        students.add(student);
        return student;
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Integer id,@Valid @RequestBody Student updated) {
        Student existing = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException(id));
        existing.setName(updated.getName());
        existing.setAge(updated.getAge());
        existing.setEmail(updated.getEmail());
        return existing;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        Student existing = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException(id));
        students.remove(existing);
        return "Student with id " + id + " deleted successfully";
    }

}