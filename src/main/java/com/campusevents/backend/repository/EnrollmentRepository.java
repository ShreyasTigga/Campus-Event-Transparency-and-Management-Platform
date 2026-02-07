package com.campusevents.backend.repository;

import com.campusevents.backend.model.Enrollment;
import com.campusevents.backend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // 🔍 Check if student already enrolled in event
    boolean existsByStudentEmailAndEvent(String studentEmail, Event event);

    // 📌 Get all enrollments for a student
    List<Enrollment> findByStudentEmail(String studentEmail);

    // 📌 Get all enrollments for an event
    List<Enrollment> findByEvent(Event event);

    // 📊 Count how many students enrolled in an event
    long countByEvent(Event event);

    // ❌ Optional: remove enrollment (future feature)
    void deleteByStudentEmailAndEvent(String studentEmail, Event event);
}
