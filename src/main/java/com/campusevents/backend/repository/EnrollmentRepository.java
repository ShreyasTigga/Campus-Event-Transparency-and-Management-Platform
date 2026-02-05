package com.campusevents.backend.repository;

import java.util.List;
import com.campusevents.backend.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentEmailAndEventId(String email, Long eventId);

    List<Enrollment> findByStudentEmail(String email);
}
