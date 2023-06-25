package com.ead.authuser.specifications;

import com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

//this class was created with the purpose to implement ResolverConfig (advanced filters)
public class SpecificationTemplate {
    //also is possible to include the @Or
    //this will work like: /users?userType=ADMIN&email=savedra
    //if we give two filters, both must be compatible to return data... if we use Or, this is not necessary
    //we can give one filter for example /users?userType=STUDENT
    @And({
        @Spec(path="userType", spec= Equal.class), //the filter userType must be equal the value in database (enum)
        @Spec(path="userStatus", spec= Equal.class), //the filter userStatus must be equal the value in database (enum)
        @Spec(path="email", spec= Like.class), //doesn't have to be equal, but must constains the value in some place,
        @Spec(path = "fullName", spec = Like.class)
    })
    public interface UserSpec extends Specification<UserModel>{}

}
