import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class CrudApp {
    static ArrayList<Employee> employees;
    static ArrayList<Department> departments;
    static Connection conn;
    static Statement stmt;

    // Static block to initialize connection and array lists
    static {
        employees = new ArrayList<>();
        departments = new ArrayList<>();
        conn = GetConnection.getConn("employees");
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int ch = 0;
        ch = showMenu(scan);
        // It will keep executing the Display options unless option 5 is chosen -- to
        // Exit Application
        while (ch != 6) {
            executeMenu(ch, scan);
            ch = showMenu(scan);
        }
        System.out.println("\nExiting Application. Thank you and have a nice day!\n");
        scan.close();
    }

    public static int showMenu(Scanner scan) {
        System.out.println("\n\u001b[35m============================================");
        System.out.println("\tEmployee Management System");
        System.out.println("============================================\u001b[0m");
        System.out.println("\u001b[32m1. View All Employees");
        System.out.println("2. View Selected Employees");
        System.out.println("3. Add an Employee");
        System.out.println("4. Update Employee Info");
        System.out.println("5. Delete Employee");
        System.out.println("6. Exit Application\u001b[0m");
        System.out.println("\n\u001b[35m------------------------------------------\u001b[0m");
        System.out.print("\u001b[43m\u001b[34mSelect an option:\u001b[0m ");

        int ch = -1; // Default value to handle invalid input
        if (scan.hasNextInt()) {
            ch = scan.nextInt();
        } else {
            scan.next(); // Consume the invalid input
            System.out.println("Invalid input. Please enter a number between 1 and 6.");
        }
        return ch;
    }

    public static void executeMenu(int choice, Scanner scan) {
        if (choice == 1) {
            // System.out.println("\033[H\033[2J");
            ViewEmployees.viewAllEmp();
        } else if (choice == 2) {
            ViewEmployees.viewSelectedEmp(scan);
        } else if (choice == 3) {
            scan.nextLine();
            AddEmployee.addEmp(scan);
        } else if (choice == 4) {
            UpdateEmployee.updateEmp(scan);
        } else if (choice == 5) {
            DeleteEmployee.deleteEmp(scan);
        } else if (choice == 6) {
            System.out.println("\nExiting Application...");
        } else {
            System.out.println("\nInvalid option. Please select a valid option.");
        }

    }
}
