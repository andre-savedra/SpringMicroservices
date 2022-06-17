package com.ead.authuser.repositories;

import com.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
//it is not necessary to put the bean annotation for this case, spring makes it automatically
//JpaSpecificationExecutor is used to implement advanced filters in API (library specification arg resolver)
public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
