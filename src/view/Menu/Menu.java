package view.Menu;

import command.impl.LogoutCommand;
import formating.Center;
import model.enums.RoleTypeEnum;
import security.CurrentUser;

import java.util.*;
import java.util.stream.Collectors;

import static model.enums.RoleTypeEnum.*;

public class Menu {
    private String title;
    private List<Option> options = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public Menu(String title, List<Option> options) {
        this.title = title;

        this.options.addAll(options);
        this.options.addAll(List.of(new Option("Logout",
                List.of(ADMINISTRATOR, MANAGER, SERVER),
                new LogoutCommand())));
    }

    public String getTitle() {
        return title;
    }

    public Menu setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Option> getOptions() {
        return options;
    }

    public Menu setOptions(List<Option> options) {
        this.options = options;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Menu{");
        sb.append("title='").append(title).append('\'');
        sb.append(", options=").append(options);
        sb.append(", scanner=").append(scanner);
        sb.append('}');
        return sb.toString();
    }
    public void show(){

        while(true){
            if(CurrentUser.getUsername().equals(CurrentUser.ANONYMOUS)) {
                Optional<Option> login = options.stream().filter(op -> op.getText().equals("Login")).findFirst();
                login.get().getCommand().execute();
            }
            int i = 1;
            if(CurrentUser.isAdmin()){
                System.out.println("-".repeat(100));
                List<Option> adminOptions = options.stream()
                        .filter(option -> option.getRoles().contains(ADMINISTRATOR))
                        .collect(Collectors.toList());
                for (Option option : adminOptions) {
                    System.out.printf("%-49s|%49s%n",option.getText(),"|"+(i++)+".|");
                    System.out.println("-".repeat(100));
                }

                var result = executeCommand(adminOptions);
            }else if(CurrentUser.isManager()) {
                System.out.println("-".repeat(100));

                List<Option> managerOptions = options.stream()
                        .filter(option -> option.getRoles().contains(MANAGER))
                        .collect(Collectors.toList());
                for (Option option : managerOptions) {
                    System.out.printf("%-49s|%49s%n",option.getText(),"|"+(i++)+".|");
                    System.out.println("-".repeat(100));
                }
               var result = executeCommand(managerOptions);
            }else{
                System.out.println("-".repeat(100));
                List<Option> serverOptions = options.stream()
                        .filter(option -> option.getRoles().contains(SERVER))
                        .collect(Collectors.toList());
                for (Option option : serverOptions) {
                    System.out.printf("%-49s|%49s%n",option.getText(),"|"+(i++)+".|");
                    System.out.println("-".repeat(100));
                }
                var result = executeCommand(serverOptions);
            }
        }
    }

    private boolean executeCommand(List<Option> adminOptions) {
        int choice = -1;
        do {
            System.out.println(Center.centerString(100,"Enter your choice:"));
            var choiceStr = scanner.nextLine();
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid choice.Enter valid number from 1 to "+ adminOptions.size());
                return executeCommand(adminOptions);
            }}while (choice < 1 || choice > adminOptions.size());
        return adminOptions.get(choice -1).getCommand().execute();
    }
}
