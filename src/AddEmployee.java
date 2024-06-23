
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;



public class AddEmployee {
    public static void addEmp(Scanner scan) {
        Connection conn = GetConnection.getConn("employees");

        try {
            System.out.print("\n\u001b[32mEnter employee name:\u001b[0m ");
            String name = scan.nextLine().toUpperCase();

            System.out.print("\u001b[32mEnter job:\u001b[0m ");
            String job = scan.nextLine().toUpperCase();

            System.out.print("\u001b[32mEnter manager ID:\u001b[0m ");
            int mgr = scan.nextInt();
            scan.nextLine();
            
            System.out.print("\u001b[32mEnter hire date (YYYY-MM-DD):\u001b[0m ");
            LocalDate doj = getDateInput(scan);
            
            System.out.print("\u001b[32mEnter salary:\u001b[0m ");
            float sal = scan.nextFloat();
            scan.nextLine();
            
            System.out.print("\u001b[32mEnter commission:\u001b[0m ");
            float comm = scan.nextFloat();
            scan.nextLine();
            
            System.out.print("\u001b[32mEnter department number:\u001b[0m ");
            int dno = scan.nextInt();
            scan.nextLine();

            conn = GetConnection.getConn("employees");
            String query = "insert into emp (ename, job, mgr, hiredate, sal, comm, deptno) values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, job);
            pstmt.setInt(3, mgr);
            pstmt.setDate(4, Date.valueOf(doj));
            pstmt.setFloat(5, sal);
            pstmt.setFloat(6, comm);
            pstmt.setInt(7, dno);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee added successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL statement: " + e.getMessage());

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");

        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }

    private static LocalDate getDateInput(Scanner scan) {
        LocalDate doj = null;
        boolean validDate = false;
        while (!validDate) {
            // System.out.print("Enter hire date (YYYY-MM-DD): ");
            String dateString = scan.nextLine();
            try {
                doj = LocalDate.parse(dateString);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }
        return doj;
    }
}
