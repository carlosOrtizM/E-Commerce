package com.example.demo.Infrastructure.Repositories;

import com.example.demo.Domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{
    public List<Users> findAll();
    public Optional<Users> findByUserId(int userId);
    public Optional<Users> findByUsername(String username);
    public Users save(Users users);
    public void deleteById(int userId);

}
