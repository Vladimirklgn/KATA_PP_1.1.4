package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
        public static void main(String[] args) {
            UserServiceImpl userService = new UserServiceImpl();
            userService.createUsersTable();
            userService.saveUser("Vladimir", "Kalugin", (byte) 32);
            userService.saveUser("Darya", "Vatchanina", (byte) 21);
            userService.saveUser("Dana", "Mnih", (byte) 19);
            userService.saveUser("Mihail", "Bezukladnikov", (byte) 21);
            System.out.println(userService.getAllUsers());
            userService.cleanUsersTable();
            userService.dropUsersTable();
    }
}
