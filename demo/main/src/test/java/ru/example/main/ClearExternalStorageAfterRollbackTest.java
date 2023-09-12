package ru.example.main;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.example.main.dto.CarDto;
import ru.example.main.service.CarService;


/**
 * Класс тестов, проверяет удаление созданного файла после отката транзакции.
 */
@SpringBootTest
public class ClearExternalStorageAfterRollbackTest {

    @Autowired
    private CarService carService;

    @Test
    void testFileDeleteAfterTransactionRollback() {
        var carDto = new CarDto();
        carDto.setName("Porsche");
        carDto.setDescription("Porsche Cayenne");
        Exception exception = Assertions.assertThrows(Exception.class, () -> carService.saveCarInfo(carDto));
        Assertions.assertInstanceOf(DataIntegrityViolationException.class, exception);
        var path = Paths.get(carDto.getName() + ".txt");
        Assertions.assertFalse(Files.exists(path),
                "File created by service haven't been remover after rollback!");
    }
}
