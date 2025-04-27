package com.chanllenge.batch.infrastructure.repository.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chanllenge.batch.domain.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}