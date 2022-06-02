package com.example.springbootrestapp.repositories;

import com.example.springbootrestapp.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
