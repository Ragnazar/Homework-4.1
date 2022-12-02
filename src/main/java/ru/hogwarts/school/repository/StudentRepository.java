package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByNameIgnoreCaseAndAge(String name, int age);

    Collection<Student> findStudentsByAgeIsBetween(int age1, int age2);

    Collection<Student> findStudentsByFacultyId(Long facultyId);

    @Query(value = "SELECT COUNT(*) as count FROM student", nativeQuery = true)
    Integer getCount();

    @Query(value = "SELECT AVG(age) as age FROM student", nativeQuery = true)
    Double getAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC limit 5",
            nativeQuery = true)
    List<Student> getLastFive();
}
