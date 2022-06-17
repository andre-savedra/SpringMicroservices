package com.ead.course.specifications;

import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path="courseLevel", spec= Equal.class),
            @Spec(path="courseStatus", spec= Equal.class),
            @Spec(path="name", spec= Like.class)
    })
    public interface CourseSpec extends Specification<CourseModel> {}

    @Spec(path="title", spec= Like.class)
    public interface ModuleSpec extends Specification<ModuleModel> {}

    @Spec(path="title", spec= Like.class)
    public interface LessonSpec extends Specification<LessonModel> {}

    //CRITERIA BUILDER AND CRITERIA QUERY , SEE DOCS...
    public static Specification<ModuleModel> moduleCourseId(final UUID courseId){
        return (root, query, cb) ->{ //arrow function lambda in Java....seems a little as arrow functions javascript
            query.distinct(true); //remove duplicate values
            //declara entities in this criteria
            Root<ModuleModel> module = root;
            Root<CourseModel> course = query.from(CourseModel.class);
            Expression<Collection<ModuleModel>> coursesModules = course.get("modules");
            //as a sql where                                        as sql subquery
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(module, coursesModules));
        };
    }

    public static Specification<LessonModel> lessonModuleId(final UUID moduleId){
        return (root, query, cb) ->{
            query.distinct(true);
            Root<LessonModel> lesson = root;
            Root<ModuleModel> module = query.from(ModuleModel.class);
            Expression<Collection<LessonModel>> moduleLessons = module.get("lessons");
            return cb.and(cb.equal(module.get("moduleId"), moduleId), cb.isMember(lesson, moduleLessons));
        };
    }


}
