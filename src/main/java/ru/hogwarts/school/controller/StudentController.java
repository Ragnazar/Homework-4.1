package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "{id}")  //GET http://localhost:8080/student/23
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping(path = "all")  //GET http://localhost:8080/student/all
    public ResponseEntity<Collection<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping  //GET http://localhost:8080/student
    public ResponseEntity<Collection<Student>> findStudent(@RequestParam Integer age,
                                                           @RequestParam String name) {
        return ResponseEntity.ok(studentService.findByNameAndAge(name, age));
    }

    @PostMapping    //POST http://localhost:8080/student
    public Student addStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @DeleteMapping(path = "{id}")  //DELETE http://localhost:8080/student/23
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping  //PUT http://localhost:8080/student/23
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student foundStudent = studentService.findStudent(student.getId());
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(studentService.editStudent(student));
    }


    @GetMapping(path = "/age-between") //GET http://localhost:8080/student/age-between?age1=11&age2=12
    public ResponseEntity<Collection<Student>> getStudentsByAgeIsBetween(@RequestParam int age1,
                                                                         @RequestParam int age2) {
        return ResponseEntity.ok(studentService.findStudentsByAgeIsBetween(age1, age2));
    }

    @GetMapping(path = "{id}/faculty")//GET http://localhost:8080/student/{id}/faculty
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findFaculty(id));
    }

    @GetMapping(path = "/count")  //GET http://localhost:8080/student/count
    public ResponseEntity<Integer> getStudentsCount() {
        return ResponseEntity.ok(studentService.getCount());
    }

    @GetMapping(path = "/average-age")  //GET http://localhost:8080/student/average-age
    public ResponseEntity<Double> getStudentsAverageAge() {
        return ResponseEntity.ok(studentService.gatAverageAge());
    }

    @GetMapping(path = "/last-five")  //GET http://localhost:8080/student/last-five
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFive());
    }
}
