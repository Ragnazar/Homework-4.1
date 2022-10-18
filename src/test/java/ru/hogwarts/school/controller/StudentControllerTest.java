package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudents() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", Student.class))
                .isNotNull();
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Bob");

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class))
                .isNotNull();
    }

    @Test
    public void testPutStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Sam");
        restTemplate.put("http://localhost:" + port + "/student", student, Student.class);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/1", Student.class))
                .isEqualTo(student);
    }

    @Test
    void testGetStudentsById() throws Exception {
        int one = 3;
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + one, Student.class))
                .isNotNull();
    }

    @Test
    void testRemoveStudent() {
        int one = 1;
        Student student = new Student();
        student.setId((long) one);
        student.setName("Sam");
        restTemplate.put("http://localhost:" + port + "/student", student, Student.class);
        restTemplate.delete("http://localhost:" + port + "/student/" + one, Student.class);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + one, Student.class))
                .isNull();
    }


    @Test
    void testGetStudentsByAgeIsBetween() throws Exception {
        int two = 2;

        Student student = new Student();
        student.setId((long) two);
        student.setName("John");
        student.setAge(two);

        restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age-between", Student.class))
                .isNotNull();
    }

    @Test
    void testGetFacultyById() throws Exception {
        int two = 2;

        Faculty faculty = new Faculty();
        faculty.setId((long) two);
        faculty.setName("testers");
        restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class);

        Student student = new Student();
        student.setId((long) two);
        student.setName("John");
        student.setAge(two);
        student.setFaculty(faculty);
        restTemplate.put("http://localhost:" + port + "/student", student, Student.class);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + 19 + "/faculty", Faculty.class))
                .isNotNull();
    }
}