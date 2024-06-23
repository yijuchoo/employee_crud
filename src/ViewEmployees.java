import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ViewEmployees {
    // No need to declare connection and lists here as they will be used from
    // CrudApp

    // Method to view all employees
    public static void viewAllEmp() {
        // System.out.println("\n1. View All Employees");
        String title = "\u001b[33m[1] View All Employees";
        CrudApp.employees.clear(); // Clear the list to avoid duplication
        try {
            Statement stmt = CrudApp.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from emp");

            // Collecting employee data from the ResultSet
            while (rs.next()) {
                CrudApp.employees.add(new Employee(
                        rs.getInt(1), // column no. 1
                        rs.getString(2), // column no. 2
                        rs.getString("job"), // can use either column index or column labels, either one
                        rs.getInt("mgr"),
                        rs.getDate("hiredate").toLocalDate(),
                        rs.getFloat("sal"),
                        rs.getFloat("comm"),
                        rs.getInt("deptno")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        displayEmployees(title); // Call method to display employees with title
    }

    // Method to view selected employees by department number or employee number
    public static void viewSelectedEmp(Scanner scan) {
        System.out.println("\n\u001B[35mView employees by:\u001b[0m\n");
        System.out.println("\u001B[32m1. Department Number");
        System.out.println("2. Employee Number\u001b[0m");
        System.out.print("\n\u001b[43m\u001b[34mEnter choice (1 or 2):\u001b[0m ");
        int choice = scan.nextInt();
        scan.nextLine(); // Consume newline

        if (choice == 1) {
            viewByDeptNo(scan); // View by department number
        } else if (choice == 2) {
            viewByEmpNo(scan); // View by employee number
        } else {
            System.out.println("Invalid choice. Please select 1 or 2.");
        }
    }

    // Method to view employees by department number
    public static void viewByDeptNo(Scanner scan) {
        System.out.println("\n1. View Employees by Department Number");
        // Display list of departments
        displayDepartments(); // Display list of departments

        System.out.print("\n\u001b[43m\u001b[34mEnter department number to filter employees:\u001b[0m ");
        int deptNo = scan.nextInt();
        scan.nextLine(); // Consume newline

        CrudApp.employees.clear(); // Clear the list to avoid duplication
        try {
            String query = "SELECT * FROM emp WHERE deptno = ?";
            PreparedStatement pstmt = CrudApp.conn.prepareStatement(query);
            pstmt.setInt(1, deptNo);
            ResultSet rs = pstmt.executeQuery();

            // Collecting employee data from the ResultSet
            while (rs.next()) {
                CrudApp.employees.add(new Employee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString("job"),
                        rs.getInt("mgr"),
                        rs.getDate("hiredate").toLocalDate(),
                        rs.getFloat("sal"),
                        rs.getFloat("comm"),
                        rs.getInt("deptno")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        displayEmployees(); // Call method to display employees
    }

    // Method to view employees by employee number
    public static void viewByEmpNo(Scanner scan) {
        System.out.println("\n1. View Employees by Employee Number");
        System.out.print("\n\u001b[43m\u001b[34mEnter employee number to view record:\u001b[0m ");
        int empNo = scan.nextInt();
        scan.nextLine(); // Consume newline

        CrudApp.employees.clear(); // Clear the list to avoid duplication
        try {
            String query = "SELECT * FROM emp WHERE empno = ?";
            PreparedStatement pstmt = CrudApp.conn.prepareStatement(query);
            pstmt.setInt(1, empNo);
            ResultSet rs = pstmt.executeQuery();

            // Collecting employee data from the ResultSet
            while (rs.next()) {
                CrudApp.employees.add(new Employee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString("job"),
                        rs.getInt("mgr"),
                        rs.getDate("hiredate").toLocalDate(),
                        rs.getFloat("sal"),
                        rs.getFloat("comm"),
                        rs.getInt("deptno")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        displayEmployees(); // Call method to display employees
    }

    // Method to display list of departments
    public static void displayDepartments() {
        CrudApp.departments.clear(); // Clear the list to avoid duplication
        try {
            Statement stmt = CrudApp.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT deptno, dname FROM dept");

            // Collecting department data from the ResultSet
            while (rs.next()) {
                CrudApp.departments.add(new Department(
                        rs.getInt(1), // column no. 1
                        rs.getString(2) // column no. 2
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Print the header for department details
        System.out.println(
                "\n\u001b[35m==========================================\u001b[0m");
        System.out.printf("\u001b[35m%-10s %-20s%n\u001b[0m", "Dept No", "Dept Name");
        System.out.println(
                "\u001b[35m==========================================\u001b[0m");

        // Print each department's details
        for (Department d : CrudApp.departments) {
            System.out.printf("\u001b[32m");
            System.out.printf("%-10d %-20s%n",
                    d.getDeptNo(),
                    d.getDeptName());
            System.out.printf("\u001b[0m");
        }
    }

    // Method to display employee details
    private static void displayEmployees(String title) {
        if (CrudApp.employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        int tableWidth = 125; // Total width of the table (this needs to be adjusted as per your table format)
        int titleLength = title.length();
        int padding = (tableWidth - titleLength) / 2;

        // Print the centered title
        System.out.println("\n" + " ".repeat(padding) + title);

        // Print the header for employee details
        System.out.println(
                "\n\u001B[32m=================================================================================================================================\u001B[0m");

        /*
         * printf - method allows you to specify a format string, followed by a list of
         * arguments to be formatted and printed according to that format.
         */
        System.out.printf("\u001B[32m%-10s %-20s %-20s %-15s %-15s %-10s %-15s %-20s%n\u001B[0m",
                "ID", "Name", "Job", "Manager ID", "Hire Date", "Salary", "Commission", "Department Number");
        System.out.println(
                "\u001B[32m=================================================================================================================================\u001B[0m");

        // Print each employee's details
        for (Employee e : CrudApp.employees) {
            System.out.printf("%-10d %-20s %-20s %-15d %-15s %-10.2f %-15.2f %-20d%n",
                    e.getId(),
                    e.getName(),
                    e.getJob(),
                    e.getMgr(),
                    e.getDoj(),
                    e.getSal(),
                    e.getComm(),
                    e.getDno());
        }
    }

    // Overloaded method to display employee details without a title
    private static void displayEmployees() {
        if (CrudApp.employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        // Print the header for employee details
        System.out.println(
                "\n\u001B[32m=================================================================================================================================\u001B[0m");

        System.out.printf("\u001B[32m%-10s %-20s %-20s %-15s %-15s %-10s %-15s %-20s%n\u001B[0m",
                "ID", "Name", "Job", "Manager ID", "Hire Date", "Salary", "Commission", "Department Number");
        System.out.println(
                "\u001B[32m=================================================================================================================================\u001B[0m");

        // Print each employee's details
        for (Employee e : CrudApp.employees) {
            System.out.printf("%-10d %-20s %-20s %-15d %-15s %-10.2f %-15.2f %-20d%n",
                    e.getId(),
                    e.getName(),
                    e.getJob(),
                    e.getMgr(),
                    e.getDoj(),
                    e.getSal(),
                    e.getComm(),
                    e.getDno());
        }
    }

}
