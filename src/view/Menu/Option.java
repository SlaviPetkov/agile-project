package view.Menu;

import command.Command;
import model.enums.RoleTypeEnum;

import java.util.List;

public class Option {
    private static int nextId = 0;
    private int id;
    private String text;
    private List<RoleTypeEnum> roles;
    private Command command;

    public Option(String text, List<RoleTypeEnum> roles, Command command) {
        this.id = ++nextId;
        this.text = text;
        this.roles = roles;
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public Option setText(String text) {
        this.text = text;
        return this;
    }

    public List<RoleTypeEnum> getRoles() {
        return roles;
    }

    public Option setRoles(List<RoleTypeEnum> roles) {
        this.roles = roles;
        return this;
    }

    public Command getCommand() {
        return command;
    }

    public Option setCommand(Command command) {
        this.command = command;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        return id == option.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Option{");
        sb.append("id=").append(id);
        sb.append(", text='").append(text).append('\'');
        sb.append(", roles='").append(roles).append('\'');
        sb.append(", command=").append(command);
        sb.append('}');
        return sb.toString();
    }
}
