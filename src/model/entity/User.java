package model.entity;

import exceptions.InvalidEntityException;
import model.enums.GenderEnum;
import model.enums.RoleTypeEnum;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User extends BaseEntity implements Serializable {
    public static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-z]{2,6}$");
    public static final  Pattern PASSWORD_PATTERN =
            Pattern.compile("\\d{4}");
    public static final  Pattern MOBILE_PATTERN =
            Pattern.compile("08\\d{8}");

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String mobile;
    private GenderEnum gender;
    private String email;
    private LocalDateTime lastLogin;

    private RoleTypeEnum role;

    public User() {
        lastLogin = LocalDateTime.now();
    }

    public User(String firstName, String lastName,
                String userName, String password, String mobile,
                GenderEnum gender, String email, LocalDateTime lastLogin,
                RoleTypeEnum role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = userName;
        this.password = password;
        this.mobile = mobile;
        this.gender = gender;
        this.email = email;
        this.lastLogin = lastLogin;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }



    public String getLastName() {
        return lastName;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }



    public String getMobile() {
        return mobile;
    }


    public String getEmail() {
        return email;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public User setGender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public User setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public RoleTypeEnum getRole() {
        return role;
    }

    public User setRole(RoleTypeEnum role) {
        this.role = role;
        return this;
    }
    public static User mapRow(ResultSet rs) throws SQLException, InvalidEntityException {

        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setMobile(rs.getString("mobile"));
        user.setGender(GenderEnum.valueOf(rs.getString("gender").toUpperCase(Locale.ROOT)));
        user.setEmail(rs.getString("email"));
        user.setLastLogin(rs.getObject("last_login",LocalDateTime.class));
        user.setCreated(rs.getObject("created", LocalDateTime.class));
        user.setModified(rs.getObject("modified",LocalDateTime.class));
        user.setRole(RoleTypeEnum.valueOf(rs.getString("role")));
        return user;

    }

    @Override
    public String toString() {
        return new StringJoiner(" | ", "", "")
                .add(super.toString())
                .add("first_name= " + firstName)
                .add("last_name= " + lastName)
                .add("username= " + username)
                .add("mobile= " + mobile)
                .add("email= " + email)
                .add("gender= " + gender.toString())
                .add("role= " + getRole())
                .add("last_login= "+lastLogin)
                .add("created= " + super.getCreated())
                .add("modified= " + super.getModified())
                .toString();
    }
}
