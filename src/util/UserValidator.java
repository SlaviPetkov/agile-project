package util;

import exceptions.ConstraintViolation;
import exceptions.ConstraintViolationException;
import exceptions.InvalidEntityException;
import model.entity.User;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    public static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-z]{2,6}$");
    public static final  Pattern PASSWORD_PATTERN =
            Pattern.compile("\\d{4}");
    public static final  Pattern MOBILE_PATTERN =
            Pattern.compile("08\\d{8}");

    @Override
    public void validate(User entity) throws ConstraintViolationException {
        List<ConstraintViolation> violations = new ArrayList<>();

        var firstNameLength = entity.getFirstName().trim().length();
        if(firstNameLength < 2 || firstNameLength > 15){
            violations.add(new ConstraintViolation(entity.getClass().getName(),
                    "firstName",entity.getFirstName(),
                    "First name should be between 2 and 15 characters"));
        }

        var lastNameLength = entity.getLastName().trim().length();
        if(lastNameLength < 2 || lastNameLength > 15){
            violations.add(new ConstraintViolation(entity.getClass().getName(),
                    "lastName",entity.getLastName(),
                    "Last name should be between 2 and 15 characters"));
        }

        var userNameLength = entity.getUsername().trim().length();
        if(userNameLength < 2 || userNameLength > 15){
            violations.add(new ConstraintViolation(entity.getClass().getName(),
                    "username",entity.getUsername(),
                    "Username should be between 2 and 15 characters" ));
        }

        Matcher passwordMatcher = PASSWORD_PATTERN.matcher(entity.getPassword());
        if(!passwordMatcher.matches()) {
            violations.add(new ConstraintViolation(entity.getClass().getName(),
                    "password",entity.getPassword(),
                    "Password must be 4 digits"));
        }

        Matcher mobileMatcher = MOBILE_PATTERN.matcher(entity.getMobile());
        if(!mobileMatcher.matches()){
            violations.add(new ConstraintViolation(entity.getClass().getName(),"mobile"
            ,entity.getMobile(),"Mobile number must be 10 digits, starting with \"08\""));
        }

        Matcher emailMatcher = EMAIL_PATTERN.matcher(entity.getEmail());
        if (!emailMatcher.matches()) {
            violations.add(new ConstraintViolation(entity.getClass().getName(),"email",
                    entity.getEmail(),"User's email is invalid"));
        }
        if (violations.size() > 0 ){
            throw new ConstraintViolationException("One or more invalid user fields",violations);
        }

    }
}
