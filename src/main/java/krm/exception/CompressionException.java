package krm.exception;

public class CompressionException extends Exception {

    ErrorCodeCompression errorCode;

    public CompressionException(ErrorCodeCompression errorCode, Throwable cause) {
        super(errorCode.getErrorMessage(), cause);
        this.errorCode = errorCode;
    }

    public CompressionException(ErrorCodeCompression errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public ErrorCodeCompression getErrorCode() {
        return errorCode;
    }
}
