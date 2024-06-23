import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteEmployee {
    public static void deleteEmp(Scanner scan) {
        Connection conn = GetConnection.getConn("employees");
        
        try {
            System.out.print("Enter employee ID to delete: ");
            int empId = scan.nextInt(); // Get the employee ID

            // SQL query to delete the employee with the given ID
            String query = "DELETE FROM emp WHERE empno = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, empId);

            int rowsAffected = pstmt.executeUpdate(); // Execute the update
            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee with ID " + empId + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL statement: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // Close the connection
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
