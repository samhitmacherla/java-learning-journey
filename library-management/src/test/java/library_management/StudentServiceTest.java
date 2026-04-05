package library_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        student1 = new Student(1, "Samhit", 21, "samhit@gmail.com");
        student2 = new Student(2, "Rahul", 22, "rahul@gmail.com");
    }

    @Test
    void getAllStudents_shouldReturnAllStudents() {
        // Arrange
        List<Student> students = Arrays.asList(student1, student2);
        when(studentRepository.findAll()).thenReturn(students);

        // Act
        List<Student> result = studentService.getAllStudents();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Samhit", result.get(0).getName());
        assertEquals("Rahul", result.get(1).getName());
        verify(studentRepository, times(1)).findAll();
    }
    @Test
    void getStudentById_shouldReturnStudent_whenExists() {
        // Arrange
        when(studentRepository.findById(1)).thenReturn(Optional.of(student1));

        // Act
        Student result = studentService.getStudentById(1);

        // Assert
        assertEquals("Samhit", result.getName());
        assertEquals(21, result.getAge());
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    void getStudentById_shouldThrowException_whenNotFound() {
        // Arrange
        when(studentRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentById(99);
        });
        verify(studentRepository, times(1)).findById(99);
    }
    @Test
    void createStudent_shouldSaveAndReturnStudent() {
        // Arrange
        when(studentRepository.save(student1)).thenReturn(student1);

        // Act
        Student result = studentService.createStudent(student1);

        // Assert
        assertEquals("Samhit", result.getName());
        assertEquals("samhit@gmail.com", result.getEmail());
        verify(studentRepository, times(1)).save(student1);
    }
    @Test
    void deleteStudent_shouldThrowException_whenNotFound() {
        // Arrange
        when(studentRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.deleteStudent(99);
        });
        verify(studentRepository, never()).delete(any());
    }
}
