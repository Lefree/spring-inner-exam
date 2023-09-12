package ru.example.main.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.example.main.domain.Car;
import ru.example.main.dto.CarDto;
import ru.example.main.event.SuccessCreateExternalFileEvent;
import ru.example.main.repository.CarRepository;


@Service
public class CarService {

    private final static Logger LOG = LogManager.getLogger(CarService.class);

    private final CarRepository repository;

    private final JdbcTemplate jdbcTemplate;

    private final ApplicationEventPublisher applicationEventPublisher;

    public CarService(CarRepository repository, JdbcTemplate jdbcTemplate,
                      ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Car getCarByIdUsingJpa(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Car getCarByIdUsingJdbc(Long id) {
        return jdbcTemplate.queryForObject("select * from car where id=?",
                new BeanPropertyRowMapper<Car>(Car.class), new Object[]{id});
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public void updateCarViaRepositoryReadOnlyMethod(Long id, String name) {
        var car = getCarByIdUsingJpa(id);
        if (car == null) {
            return;
        }
        car.setName(name);
        repository.save(car);
    }

    public void updateCarViaRepository(Long id, String name) {
        var car = getCarByIdUsingJpa(id);
        if (car == null) {
            return;
        }
        car.setName(name);
        repository.save(car);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public void updateViaJdbcTemplateReadOnlyMethod(Long id, String name) {
        jdbcTemplate.update("update car set name=? where id=?", name, id);
    }

    public void updateViaJdbcTemplate(Long id, String name) {
        jdbcTemplate.update("update car set name=? where id=?", name, id);
    }

    @Transactional
    public void saveCarInfo(CarDto carDto) {
        var newCar = new Car();
        newCar.setId(repository.count() + 1);
        newCar.setName(carDto.getName());
        newCar = repository.saveAndFlush(newCar);

        String additionalInfoPath = saveFileWithAdditionalInfo(carDto);
        applicationEventPublisher.publishEvent(new SuccessCreateExternalFileEvent(additionalInfoPath));
        newCar.setAdditionalInfoFilePath(additionalInfoPath);
        repository.save(newCar);
    }

    private String saveFileWithAdditionalInfo(CarDto carDto) {
        var path = Paths.get(carDto.getName() + ".txt");
        try {
            var file = Files.createFile(path);
            Files.write(file, carDto.getDescription().getBytes());
            return file.toFile().getAbsolutePath();
        } catch (IOException exception) {
            LOG.info(String.format("Error while writing file: %s", exception.getMessage()));
            return null;
        }
    }
}
