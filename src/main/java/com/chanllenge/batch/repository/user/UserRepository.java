package com.chanllenge.batch.repository.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chanllenge.batch.entity.user.User;

public interface UserRepository extends JpaRepository<User, UUID> {}
