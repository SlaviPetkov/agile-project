package exceptions;

public class ConstraintViolation {
    private String type;
    private String field;
    private Object invalidValue;
    private String errorMessage;

    public ConstraintViolation() {
    }

    public ConstraintViolation(String type, String field, Object invalidValue, String errorMessage) {
        this.type = type;
        this.field = field;
        this.invalidValue = invalidValue;
        this.errorMessage = errorMessage;
    }

    public String getType() {
        return type;
    }

    public ConstraintViolation setType(String type) {
        this.type = type;
        return this;
    }

    public String getField() {
        return field;
    }

    public ConstraintViolation setField(String field) {
        this.field = field;
        return this;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    public ConstraintViolation setInvalidValue(Object invalidValue) {
        this.invalidValue = invalidValue;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ConstraintViolation setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstraintViolation that = (ConstraintViolation) o;

        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) return false;
        if (getField() != null ? !getField().equals(that.getField()) : that.getField() != null) return false;
        if (getInvalidValue() != null ? !getInvalidValue().equals(that.getInvalidValue()) : that.getInvalidValue() != null)
            return false;
        return getErrorMessage() != null ? getErrorMessage().equals(that.getErrorMessage()) : that.getErrorMessage() == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + field.hashCode();
        result = 31 * result + invalidValue.hashCode();
        result = 31 * result + errorMessage.hashCode();
        return result;
    }
}
