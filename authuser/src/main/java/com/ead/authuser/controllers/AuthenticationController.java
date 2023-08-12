package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
//import org.slf4j.Logger; //VERSION 1
//import org.slf4j.LoggerFactory; //VERSION 1
import lombok.extern.log4j.Log4j2;
//import org.apache.logging.log4j.LogManager; //VERSION 2
//import org.apache.logging.log4j.Logger; //VERSION 2
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    //Implement loggers in backend! SL4J VERSION 1!!!
    //SLF4J is standard of Spring Starter Web dependency
    //Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    //Implement loggers in backend! SL4J VERSION 2!!!
    //however if we want to use the SL4J2 we need apply in pom.xml and exclude the standard
    //MOREOVER, WE CAN EXCLUDE THE CREATING OF INSTANCE JUST INCLUDING THE ANNOTATION OF LOMBOK CALLED:
    //@Log4j2 in the top of the class!!!!!
    //Logger logger = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                               @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto){
        log.debug("POST registerUser userDto received {} ", userDto.toString());
        if(userService.existsByUsername(userDto.getUsername())){
            log.warn("Username {} is Already Taken", userDto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken");
        }
        if(userService.existsByEmail(userDto.getEmail())){
            log.warn("Email {} is Already Taken", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken");
        }
        //this is a new feature in Java 11
        //if we are in a closed scope, we can avoid the repetion of class type variable
        //the old way was -> UserModel userModel = new UserModel();
        var userModel = new UserModel();

        //copy objects in a deep clone (from, to)
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.saveUser(userModel);
        log.debug("POST registerUser userModel saved {} ", userModel.getUserId());
        log.info("User saved successufully userId {} ", userModel.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    //OBSERVATIONS FOR LOGGS:
    //In default just info, warn and error logs are showed in terminal.
    //if we want more than default we must start application with maven command line:
    //mvn spring-boot:run -D spring-boot.run.arguments=--logging.level.com.ead=TRACE
    //OR we can enable the logs in the application.yaml
    @GetMapping("/")
    public String index(){
//        when we use the annotation of lombok to create logs the name of object is log
//        logger.trace("TRACE"); //granularity of code... more detailed
//        logger.debug("DEBUG"); //development level for debug purposes
//        logger.info("INFO");   //general informations
//        logger.warn("WARN");   //warn situations
//        logger.error("ERROR"); //error happened generally used in try/catch
        log.trace("TRACE"); //granularity of code... more detailed
        log.debug("DEBUG"); //development level for debug purposes
        log.info("INFO");   //general informations
        log.warn("WARN");   //warn situations
        log.error("ERROR"); //error happened generally used in try/catch
        return "Logging Spring Boot...";
    }

}
