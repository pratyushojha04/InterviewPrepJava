package practice.Todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class ToDoApp {

    private String name;
    private HashMap<String, ArrayList<String>> mp;

    public ToDoApp() {
        this.name = null;
        this.mp = new HashMap<>();
    }

    static Scanner sc = new Scanner(System.in);

    public void addSchedule(String date, String task) {
        mp.putIfAbsent(date, new ArrayList<>());
        mp.get(date).add(task);
    }

    public HashMap<String, ArrayList<String>> getSchedule() {
        return mp;
    }

    public void welcomeUser() {
        System.out.println("Welcome, " + this.name + "! to our to-do app");
    }

    public static void main(String[] args) {
        System.out.println("Welcome to ToDo App");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        ToDoApp app = new ToDoApp();
        app.name = name;
        app.welcomeUser();

        while (true) {
            System.out.println("\nMENU:");
            System.out.println("1. Add Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();  // FIX input buffer issue

            if (choice == 1) {
                System.out.print("Enter date (YYYY-MM-DD): ");
                String date = sc.nextLine();

                System.out.print("Enter task: ");
                String task = sc.nextLine();

                app.addSchedule(date, task);
                System.out.println("Task added!");
            }

            else if (choice == 2) {
                System.out.println("\nYour Schedule:");
                var schedules = app.getSchedule();

                if (schedules.isEmpty()) {
                    System.out.println("No tasks added yet.");
                } else {
                    for (String date : schedules.keySet()) {
                        System.out.println(date + " : " + schedules.get(date));
                    }
                }
            }

            else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            }

            else {
                System.out.println("Invalid choice! Try again.");
            }
        }
    }
}













