package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args){
        UserService userService = new UserServiceImpl();
        User user1 = new User("Natasha", "Romanoff", (byte) 35);
        User user2 = new User("Tony", "Stark", (byte) 46);
        User user3 = new User("Bryce", "Banner", (byte) 50);
        User user4 = new User("Peter", "Parker", (byte) 16);

        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
