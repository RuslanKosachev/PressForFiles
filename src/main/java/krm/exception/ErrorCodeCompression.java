package krm.exception;

public enum ErrorCodeCompression {

    NO_ACCESS("нет доступа к файлу"),
    PATH_ERROR("не правильный путь"),
    DESERIALIZATION_ERROR("сжатый файл поврежден"),
    COMPRESSION_ERROR("ошибка при сжатии"),
    EXPANDER_ERROR("ошибка при восстановлении файла");

    private String errorMessage;

    private ErrorCodeCompression(String errorString) {
        this.errorMessage = errorString;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
