package ru.example.main.listener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.example.main.event.SuccessCreateExternalFileEvent;


/**
 * Обработчик события, который удаляет созданный файл в случае отката транзакции.
 */
@Component
public class ExternalStorageEventListener {

    private final static Logger LOG = LogManager.getLogger(ExternalStorageEventListener.class);

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onCarInfoSaveFail(final SuccessCreateExternalFileEvent event) {
        var filePath = Paths.get(event.getFilePath());
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException exception) {
            LOG.info(String.format("Error while writing file: %s", exception.getMessage()));
        }
    }
}
