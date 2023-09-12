package ru.example.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.main.domain.Car;

/**
 * Репозиторий для {@link Car}.
 */
public interface CarRepository extends JpaRepository<Car, Long> {

}
