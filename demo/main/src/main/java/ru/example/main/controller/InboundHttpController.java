package ru.example.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.example.main.domain.Car;
import ru.example.main.dto.CarDto;
import ru.example.main.service.CarService;

/**
 * Контроллер для обработки входящих запросов.
 */
@RestController
public class InboundHttpController {

    private final CarService carService;

    public InboundHttpController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Метод для проверки работы параметра readOnly в аннотации @Transactional для случая использования Jpa.
     *
     * @param id   идентификатор сущности
     * @param name наименование
     * @return сущность с указанным идентификатором
     */
    @PostMapping("/read-only-jpa")
    public ResponseEntity<Car> testReadOnlyRepository(@RequestParam Long id, @RequestParam String name) {
        carService.updateCarViaRepositoryReadOnlyMethod(id, name);
        return ResponseEntity.ok(carService.getCarByIdUsingJpa(id));
    }

    /**
     * Метод для проверки работы параметра readOnly в аннотации @Transactional для случая использования JDBC.
     *
     * @param id   идентификатор сущности
     * @param name наименование
     * @return сущность с указанным идентификатором
     */
    @PostMapping("/read-only-jdbc")
    public void testReadOnlyJdbc(@RequestParam Long id, @RequestParam String name) {
        carService.updateViaJdbcTemplateReadOnlyMethod(id, name);
    }

    /**
     * Метод для проверки отката изменений для внешнего хранилища в случае отката транзакции.
     *
     * @param carDto дто с данными
     */
    @PostMapping("/test-transaction-rollback")
    public void testTransactionRollback(@RequestBody CarDto carDto) {
        carService.saveCarInfo(carDto);
    }
}
