package ru.example.main.dto;

/**
 * Dto-объект для метода, в котором проверяется откат данных из внешнего хранилища.
 */
public class CarDto {

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
