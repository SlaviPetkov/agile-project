package util;

import exceptions.ConstraintViolationException;

public interface Validator<T> {
    void validate(T entity) throws ConstraintViolationException;
}
