package ru.itis.pdfgenerator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.pdfgenerator.model.Data;

public interface DataRepository extends JpaRepository<Data, Long> {
}
