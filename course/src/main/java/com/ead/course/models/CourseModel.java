package com.ead.course.models;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_COURSES")
public class CourseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 250)
    private String description;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    //ISO 8601 Format:
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @Column(nullable = false)
    //ISO 8601 Format:
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;

    @Column(nullable = false)
    private UUID userInstructor;

    /** it is possible to use List instead Set, however there are important differences between them
        List-> ordered, not unique
        Set -> not ordered, unique
       But in Jpa there is another difference: in a model with many relationships of collections, using
       List the Jpa will return just one collection...
       However, the Set can be returned in multiple relationships of collections.
       In this example we just have one module collection, but we must prepare the project to receive more in
       the future.
       Moreover, in List, the Jpa generate more queries than the necessary, reducing the performance

     Another thing to consider that this way we made a bidirectional mapping (OneToMany with MappedBy in a model and
     ManyToOne in another), that is better than the unidirectional, because in uni mode is generated more queries
     and table by Jpa.
     **/

    //--------OBSERVATIONS TO DELETE METHODS:------------
    //"cascade = CascadeType.ALL, orphanRemoval = true" inside OneToMany is not recommended for performance purposes
    //because will generate a delete sql command to each module present in a course
    //the responsibility to delete in this case is by Hibernate
    //@OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)

    //the @OnDelete annotation give the responsability to delete to database
    //in this case will be generated two sql delete commands: delete the course and after one sql to delete all modules
    //however, in some cases the performance also stay with problems and we cannot know what is going on
    //due the responsabilitu is from database
    //@OnDelete(action = OnDeleteAction.CASCADE)

    //the better way to make a delete with high performance, controll and safety is to create
    //a method to delete the course and modules (and the module's lessons)
    //it's a hardwork but we have the return of initial state if happen a problem
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //showed just when creating or changing model, not in get
    @Fetch(FetchMode.SUBSELECT)
    /* Fetch mode SELECT: generate a select query in each module that exists (not recommeded for big datas)
     SUBSELECT: two queries: one to the main model, and another to the collections (e.g. modules)
     JOIN: One query to get all data
     However... this attribute can change the FetchType we've chosen...
      if we choose JOIN the FetchType will be automatically EAGER
      but if we choose SUBSELECT or SELECT, will use what we defined in FetchMode

     if we don't choose any FetchMode, the default is JOIN and not ignore the FetchType
    * */
    private Set<ModuleModel> modules;

    /* to create many to many we need to create an intermediary table, but hibernate makes it automatically with
    * this annotation */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_COURSES_USERS",
               joinColumns = @JoinColumn(name = "course_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserModel> users;
}
