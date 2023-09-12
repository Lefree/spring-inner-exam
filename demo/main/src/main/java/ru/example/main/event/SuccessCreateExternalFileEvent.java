package ru.example.main.event;

/**
 * Событие, которое выбрасывается при успешном создании файла с дополнительными данными.
 *
 */
public class SuccessCreateExternalFileEvent {

    /**
     * Путь до созданного файла.
     */
    private String filePath;

    public SuccessCreateExternalFileEvent(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
