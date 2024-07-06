package com.example.stagetwohng.repository;

import com.example.stagetwohng.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends JpaRepository<User, Long> {

    @Override
    public <S extends User> S save(S entity) {
        return null;
    }
}
