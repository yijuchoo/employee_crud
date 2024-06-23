import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class UpdateEmployee {
    public static void updateEmp(Scanner scan) {
        Connection conn = GetConnection.getConn("employees");

        try {
            System.out.print("Enter employee ID to update: ");
            int empId = scan.nextInt(); // Get employee ID
            scan.nextLine();

            // Retrieve current employee details
            String selectQuery = "select * from emp where empno = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setInt(1, empId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // Display current employee details
                String currentName = rs.getString("ename");
                String currentJob = rs.getString("job");
                int currentMgr = rs.getInt("mgr");
                LocalDate currentHireDate = rs.getDate("hiredate").toLocalDate();
                float currentSal = rs.getFloat("sal");
                float currentComm = rs.getFloat("comm");
                int currentDeptNo = rs.getInt("deptno");

                System.out.println("\nCurrent Employee Details:");
                System.out.println("Name: " + currentName);
                System.out.println("Job: " + currentJob);
                System.out.println("Manager ID: " + currentMgr);
                System.out.println("Hire Date: " + currentHireDate);
                System.out.println("Salary: " + currentSal);
                System.out.println("Commission: " + currentComm);
                System.out.println("Department Number: " + currentDeptNo);

                // Prompt user to update employee details
                System.out.println("\nEnter new values (leave blank to keep current record):");

                System.out.print("New Name (" + currentName + "): ");
                String newName = scan.nextLine().trim().toUpperCase();
                if (newName.isEmpty()) {
                    newName = currentName;
                }

                System.out.print("New Job (" + currentJob + "): ");
                String newJob = scan.nextLine().trim().toUpperCase();
                if (newJob.isEmpty()) {
                    newJob = currentJob;
                }

                System.out.print("New Manager ID (" + currentMgr + "): ");
                String newMgrStr = scan.nextLine().trim();
                int newMgr = newMgrStr.isEmpty() ? currentMgr : Integer.parseInt(newMgrStr);

                System.out.print("New Hire Date (" + currentHireDate + "): ");
                String newHireDateStr = scan.nextLine().trim();
                LocalDate newHireDate = newHireDateStr.isEmpty() ? currentHireDate : LocalDate.parse(newHireDateStr);

                System.out.print("New Salary (" + currentSal + "): ");
                String newSalStr = scan.nextLine().trim();
                float newSal = newSalStr.isEmpty() ? currentSal : Float.parseFloat(newSalStr);

                System.out.print("New Commission (" + currentComm + "): ");
                String newCommStr = scan.nextLine().trim();
                float newComm = newCommStr.isEmpty() ? currentComm : Float.parseFloat(newCommStr);

                System.out.print("New Department Number (" + currentDeptNo + "): ");
                String newDeptNoStr = scan.nextLine().trim();
                int newDeptNo = newDeptNoStr.isEmpty() ? currentDeptNo : Integer.parseInt(newDeptNoStr);

                // SQL query to update employee details
                String updateQuery = "update emp set ename = ?, job = ?, mgr = ?, hiredate = ?, sal = ?, comm = ?, deptno = ? where empno = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, newName);
                updateStmt.setString(2, newJob);
                updateStmt.setInt(3, newMgr);
                updateStmt.setDate(4, Date.valueOf(newHireDate));
                updateStmt.setFloat(5, newSal);
                updateStmt.setFloat(6, newComm);
                updateStmt.setInt(7, newDeptNo);
                updateStmt.setInt(8, empId);

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Employee information updated successfully.");
                    // Display the updated employee details
                    displayUpdatedEmp(conn, empId);
                } else {
                    System.out.println("Employee with ID " + empId + " not found.");
                }
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
                    conn.close(); // close the connection
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void displayUpdatedEmp(Connection conn, int empId) {
        try {
            String query = "SELECT * FROM emp WHERE empno = " + empId;
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            // Print the header
            System.out.println(
                    "\n=================================================================================================================================");
            System.out.printf("%-10s %-20s %-20s %-15s %-15s %-10s %-15s %-20s%n",
                    "ID", "Name", "Job", "Manager ID", "Hire Date", "Salary", "Commission", "Department Number");
            System.out.println(
                    "\n=================================================================================================================================");

            // Print the updated employee's details
            if (rs.next()) {
                System.out.printf("%-10d %-20s %-20s %-15d %-15s %-10.2f %-15.2f %-20d%n",
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString("job"),
                        rs.getInt("mgr"),
                        rs.getDate("hiredate").toLocalDate(),
                        rs.getFloat("sal"),
                        rs.getFloat("comm"),
                        rs.getInt("deptno"));
            } else {
                System.out.println("No employee found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
