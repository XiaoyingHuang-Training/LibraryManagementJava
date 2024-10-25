import java.sql.*;

public class LibraryManagement {

    private static final String DB_URL="jdbc:mysql://localhost:3306/librarydb";
    private static final String DB_USER="root";
    private static final String DB_PASSWORD="root";

    public static void main(String[] args){
        try(Connection conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)){
            System.out.println("We have a database connection");
              // Create a Statement object
              
            Statement stmt=null;
            ResultSet rs=null;
            try{
                stmt=conn.createStatement();
                rs=stmt.executeQuery("SELECT * FROM books");
                if (stmt.execute("SELECT * FROM books")) {
                    rs=stmt.getResultSet();
                    while (rs.next()) {
                        String id = rs.getString("ISBN"); // Change "id" to your actual column name
                        String title = rs.getString("Title"); // Change "title" to your actual column name
                        // Add more fields as needed
                        System.out.println("ISBN: " + id + ", Title: " + title);
                    }
                    System.out.println("I've got books");
                }
            }
            catch(SQLException ex){
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException sqlEx) { } // ignore
            
                    rs = null;
                }
            
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) { } // ignore
            
                    stmt = null;
                }
            }
        }catch(SQLException e){
            System.out.println("This is not good");
        }
    }
}