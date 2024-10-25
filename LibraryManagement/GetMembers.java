import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class GetMembers {

    private static final String DB_URL="jdbc:mysql://localhost:3306/librarydb";
    private static final String DB_USER="root";
    private static final String DB_PASSWORD="root";

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        System.out.println(members);
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            System.out.println("We have a database connection");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM members");

            while (rs.next()) {
                int id = rs.getInt("Member_ID");
                String email = rs.getString("Email");
                members.add(new Member(id, email));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }        
}