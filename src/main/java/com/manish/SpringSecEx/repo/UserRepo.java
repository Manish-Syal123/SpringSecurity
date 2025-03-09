package com.manish.SpringSecEx.repo;

import com.manish.SpringSecEx.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {

    //Jpa will understand this and provide the implementation/query to this method by understanding it and generate the query behind the scene to get/find the users by their username.
    //Jpa uses the method name (findBy + field name Username) to generate a query behind the scenes. This feature is known as Query Method Parsing.
    Users findByUsername(String username);
}
