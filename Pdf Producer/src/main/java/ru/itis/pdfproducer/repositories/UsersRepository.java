package ru.itis.pdfproducer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.pdfproducer.models.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

