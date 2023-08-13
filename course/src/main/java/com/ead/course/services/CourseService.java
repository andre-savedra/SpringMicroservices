package com.ead.course.services;

import com.ead.course.models.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {
    void delete(CourseModel courseModel);

    CourseModel save(CourseModel courseModel);

    Optional<CourseModel> findById(UUID courseId);

//    can be used Specification argument in two ways:
//    SpecificationTemplate.CourseSpec spec (where CourseSpec is the name given in Spectification template)
//    Specification<CourseModel> spec (referencying the class is better because if someone changes the name of spec
//    there isn't any problem)
    Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable);

    List<CourseModel> findAll();

    boolean existsByCourseAndUser(UUID courseId, UUID userId);

    void saveSubscriptionUserInCourse(UUID courseId, UUID userId);
}
