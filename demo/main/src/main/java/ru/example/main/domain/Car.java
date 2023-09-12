package ru.example.main.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Car {

    /**
     * Идентификатор.
     */
    @Id
    private long id;

    /**
     * Наименование.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Путь для файла, который будет записываться при сохранении сущности.
     * Значение длины указано достаточно маленьким, чтобы в тестах ловить исключение.
     */
    @Column(length = 10)
    private String additionalInfoFilePath;

    public Car() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdditionalInfoFilePath() {
        return additionalInfoFilePath;
    }

    public void setAdditionalInfoFilePath(String additionalInfoFilePath) {
        this.additionalInfoFilePath = additionalInfoFilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && name.equals(car.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}