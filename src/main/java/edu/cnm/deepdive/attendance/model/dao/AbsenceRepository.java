package edu.cnm.deepdive.attendance.model.dao;

import edu.cnm.deepdive.attendance.model.entity.Absence;
import edu.cnm.deepdive.attendance.model.entity.Student;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface AbsenceRepository extends CrudRepository<Absence, Long> {

  Optional<Absence> findByStudentAndId(Student student, long id);
}
