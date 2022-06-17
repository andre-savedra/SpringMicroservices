package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {

    //this is a way to work with FetchType Lazy and in some situations with Eager
    //with this function, we can call this method to retrieve the model with all courses as an Eager
    //@EntityGraph(attributePaths = {"courses"})
    //ModuleModel findByTitle(String title);

    //if we want to use in updates post, put,  use @Modifying and @Query

    //must be in this repository instead of courseRepository because the owning side of the relationship
    //is the courseModel, so there is a foreign key of course in moduleModel

    @Query(value= "select * from tb_modules where course_course_id = :courseId", nativeQuery = true)
    List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    @Query(value = "select * from tb_modules where course_course_id = :courseId and module_id = :moduleId",
           nativeQuery = true)
    Optional<ModuleModel> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);
}
