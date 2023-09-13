package ru.example.main;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.example.main.service.CarService;


/**
 * Класс тестов проверяющих поведение методов, помеченных аннотацией @Transactional(readOnly=true),
 * обновления сущности для случаев Jpa и JDBC.
 */
@SpringBootTest
public class TransactionalReadOnlyTest {

    private final long CAR_ID = 5L;

    private final String CAR_OLD_NAME = "Kia";

    private final String CAR_NEW_NAME = "Dodge";

    @Autowired
    private CarService carService;

    @Test
    @Sql("/clear_data.sql")
    @Sql("/data.sql")
    void testJpaUpdateInReadOnlyMethod() {
        var car = carService.getCarByIdUsingJpa(CAR_ID);
        Assertions.assertNotNull(car,
                String.format("Car with id = %s not found. Variable car is null!", String.valueOf(CAR_ID)));
        carService.updateCarViaRepositoryReadOnlyMethod(CAR_ID, CAR_NEW_NAME);
        car = carService.getCarByIdUsingJpa(CAR_ID);
        Assertions.assertNotEquals(CAR_NEW_NAME, car.getName(),
                "The transaction was not rolled back. Data changed");
    }

    @Test
    @Sql("/clear_data.sql")
    @Sql("/data.sql")
    void testJpaUpdate() {
        var car = carService.getCarByIdUsingJpa(CAR_ID);
        Assertions.assertNotNull(car,
                String.format("Car with id = %s not found. Variable car is null!", String.valueOf(CAR_ID)));
        carService.updateCarViaRepository(CAR_ID, CAR_NEW_NAME);
        car = carService.getCarByIdUsingJpa(CAR_ID);
        Assertions.assertEquals(CAR_NEW_NAME, car.getName(),
                "No data change occurred");
    }

    @Test()
    @Sql("/clear_data.sql")
    @Sql("/data.sql")
    void testJdbcUpdateInReadOnlyMethod() {
        var car = carService.getCarByIdUsingJdbc(CAR_ID);
        Assertions.assertNotNull(car,
                String.format("Car with id = %s not found. Variable car is null!", String.valueOf(CAR_ID)));

        Exception exception = Assertions.assertThrows(Exception.class,
                () -> carService.updateViaJdbcTemplateReadOnlyMethod(CAR_ID, CAR_NEW_NAME));
        assertThat(exception.getMessage(), containsString("cannot execute UPDATE in a read-only transaction"));
    }

    @Test()
    @Sql("/clear_data.sql")
    @Sql("/data.sql")
    void testJdbcUpdate() {
        var car = carService.getCarByIdUsingJdbc(CAR_ID);
        Assertions.assertNotNull(car,
                String.format("Car with id = %s not found. Variable car is null!", String.valueOf(CAR_ID)));
        carService.updateViaJdbcTemplate(CAR_ID, CAR_NEW_NAME);
        car = carService.getCarByIdUsingJdbc(CAR_ID);
        Assertions.assertEquals(CAR_NEW_NAME, car.getName(),
                "No data change occurred");
    }
}
