package com.seedon.SeedOnTanda.user.repository;

import com.seedon.SeedOnTanda.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, UserBaseRepository {
    Optional<User> findUserByUsernameOrEmail(String username, String email);

    Boolean existsUserByUsernameOrEmail(String username, String email);

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);

    Long deleteUserById(String id);

    @Query("select count(id)>0 from User where username = :username and id<>:id ")
    Boolean existsUserByUsernameExcludeId(@Param("username") String username, @Param("id") String id);

    @Query("select count(id)>0 from User where email = :email and id<>:id")
    Boolean existsUserByEmailExcludeId(@Param("email") String email, @Param("id") String id);

}

