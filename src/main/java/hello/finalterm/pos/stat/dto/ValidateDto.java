package hello.finalterm.pos.stat.dto;

public class ValidateDto {

    private boolean isValidate;
    private String errorCode;

    public ValidateDto(boolean isValidate, String errorCode) {
        this.isValidate = isValidate;
        this.errorCode = errorCode;
    }

    public boolean isValidate() {
        return isValidate;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
