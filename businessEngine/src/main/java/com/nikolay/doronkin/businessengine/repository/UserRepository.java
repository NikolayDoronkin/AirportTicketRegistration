package com.nikolay.doronkin.businessengine.repository;

import com.nikolay.doronkin.businessengine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserName(String name);
    boolean existsByUserName(String userName);
}
