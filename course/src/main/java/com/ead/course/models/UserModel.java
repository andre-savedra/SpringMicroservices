package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor //using constructor with all parameters
@NoArgsConstructor //using constructor with none parameter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID userId;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 150)
    private String fullName;
    @Column(nullable = false)
    private String userStatus;
    @Column(nullable = false)
    private String userType;
    @Column(length = 20)
    private String cpf;
    @Column
    private String imageUrl;

    /* to create many to many we need to create an intermediary table, but hibernate makes it automatically with
     * this annotation (bidirectional side) */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "users",fetch = FetchType.LAZY)
    private Set<CourseModel> courses;
}
