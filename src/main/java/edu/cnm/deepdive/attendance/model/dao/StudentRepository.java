package edu.cnm.deepdive.attendance.model.dao;

import edu.cnm.deepdive.attendance.model.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {

  Iterable<Student> findAllByOrderByLastNameAscFirstNameAsc();


}
