package library_management;


import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class StudentService {
    private  final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    public Student getStudentById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }



    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }


    public Student updateStudent(Integer id, Student updated) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        existing.setName(updated.getName());
        existing.setAge(updated.getAge());
        existing.setEmail(updated.getEmail());
        return studentRepository.save(existing);
    }


    public String deleteStudent(Integer id) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(existing);
        return "Student with id " + id + " deleted successfully";
    }
}
