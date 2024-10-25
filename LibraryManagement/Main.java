import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String DB_URL="jdbc:mysql://localhost:3306/librarydb";
    private static final String DB_USER="root";
    private static final String DB_PASSWORD="root";
    public static void main(String[] args){
        try(Connection conn =DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)){
            System.out.println("We have a database connection");
            Scanner scanner=new Scanner(System.in);
            while (true) {
                printMenu();
                try{
                    int choice=scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            System.out.println("Please enter your information to register.");
                            addMember(conn, scanner);
                            break;
                        case 2:
                            System.out.println("Menu 2");
                            borrowBook(conn,scanner);
                            break;
                        case 3:
                            System.out.println("Menu 3");
                            returnBook(conn,scanner);
                            break;
                        case 4:
                            System.out.println("Menu 4");
                            displayAvailableBooks(conn,scanner);
                            break;
                        case 5:
                            System.out.println("Menu 5");
                            displayCurrentLoan(conn,scanner);
                            break;
                        case 6:
                            System.out.println("Bye B*****s! Looking forward to seeing you soon.");
                            return;
                        default:
                            break;
                    }
                }catch(InputMismatchException e){
                    System.out.println("You're a naughty baby. Please choose 1-6.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }
        }catch(SQLException e){
            System.out.println("This is not good");
        }
    }


private static void printMenu(){
    System.out.println("--------Welcome to BB Library--------");
    System.out.println("---------Be an educated b***h--------");
    System.out.println("1. Add a new member");
    System.out.println("2. Borrow a book");
    System.out.println("3. Return a book");
    System.out.println("4. Display available books");
    System.out.println("5. Current loan record");
    System.out.println("6. Exit");
}

private static void addMember(Connection conn, Scanner scanner) throws SQLException {
    System.out.println("Enter first name");
    String firstName=scanner.nextLine();
    System.out.println("Enter last name");
    String lastName=scanner.nextLine();
    System.out.println("Enter your email");
    String email=scanner.nextLine();
    System.out.println("Enter your password");
    String passowrd=scanner.nextLine();
    System.out.println("Enter your phone number");
    String phoneNumber=scanner.nextLine();
    System.out.println("Enter your address");
    String address=scanner.nextLine();
    System.out.println("Enter your date of birth in the format of yyyy-mm-dd");
    String dateOfBirth=scanner.nextLine();

    String sql="INSERT INTO Members (First_Name, Last_Name, Email, Phone_Number, Address, DOB, Password_hash, Loan_Limit, Current_Loans, Status) VALUES (?, ?, ?, ?, ?, ?, ?, 5, 0, True)";
    try(PreparedStatement pstmt=conn.prepareStatement(sql)){
        pstmt.setString(1, firstName);
        pstmt.setString(2, lastName);
        pstmt.setString(3, email);
        pstmt.setString(4, phoneNumber);
        pstmt.setString(5, address);
        pstmt.setString(6, dateOfBirth);
        pstmt.setString(7, passowrd);
        int affectedRows=pstmt.executeUpdate();
        if(affectedRows>0){
            System.out.println("Member added successfully");
        }
    }
}

private static void borrowBook(Connection conn, Scanner scanner) throws SQLException{
    String[] displayBookResult=displayBook(conn, scanner);
    String bookISBN=displayBookResult[0];
    String bookLocation=displayBookResult[1];
    if(!bookISBN.equals("Error")){
        System.out.println("Is it the book you're borrowing? Enter y or n.");
        String confirmBorrow=scanner.nextLine();
        if (confirmBorrow.equals("y")) {
            if (bookLocation.equals("on loan")){
                System.out.println("This book is currently unavailable.");
            }else{
                int currentLoanLimit=displayLoanLimit(conn, scanner, 2);
                System.out.println("Your current loan limit is:" +currentLoanLimit);
                if(currentLoanLimit>0){
                    try{
                        postCurrentTransaction(conn, scanner, bookISBN,2);
                        int newLoanLimit=currentLoanLimit-1;
                        System.out.println("Your new loan limit is "+newLoanLimit);
                        updateLoanLimit(conn, scanner, 1,newLoanLimit);
                        System.out.println("After updating loan limit");
                        updateBookLocation(conn, scanner, bookISBN, "on loan");
                    }catch(SQLException e){
                        System.out.println("Error during transaction: " + e.getMessage());
                    }
                }else{
                    System.out.println("Exceeding loan limit, please return previous loaned books before borrowing new books.");
                }
            }
        }else{
            System.out.println("Error happened while trying to borrow the book.");
        }
    }
}

private static String[] displayBook(Connection conn, Scanner scanner) throws SQLException{
    System.out.println("Please enter the ISBN of the book");
    String ISBN=scanner.nextLine();
    String[] result={"Error","Error"};

    String sql="SELECT * FROM books WHERE ISBN=?";
    try(PreparedStatement pstmt=conn.prepareStatement(sql)){
        pstmt.setString(1, ISBN);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()) {
            // Assuming the books table has these columns, adjust as needed
            String title = rs.getString("Title");
            String author = rs.getString("Author(s)");
            String publisher = rs.getString("Publisher");
            int year = rs.getInt("Publication_Year");
            String location=rs.getString("Location");

            // Print the book details
            System.out.println("Book Details:");
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
            System.out.println("Publisher: " + publisher);
            System.out.println("Year: " + year);

            result[0]=ISBN;
            result[1]=location;

            return result;
        } else {
            System.out.println("No book found with ISBN: " + ISBN);
        }
    }catch (SQLException e){
        System.out.println("Error retrieving book: " + e.getMessage());
    }
    return result;
}

private static int displayLoanLimit(Connection conn, Scanner scanner,int memberID) throws SQLException{
    String sql="SELECT Loan_Limit FROM Members WHERE Member_ID=?";
    try(PreparedStatement pstmt=conn.prepareStatement(sql)){
        pstmt.setInt(1, memberID);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()) {
            int loanLimit=rs.getInt("Loan_Limit");
            return loanLimit;
        }else{
            System.out.println("Not existing member, please register to borrow books.");
        }
    }catch (SQLException e){
        System.out.println("Error retrieving loan limit: " + e.getMessage());
    }
    return -1;
}

private static void postCurrentTransaction(Connection conn, Scanner scanner, String ISBN, int memberID) throws SQLException{

    String sql="INSERT INTO Current_Transactions(ISBN, Member_ID, Renewals_Left) VALUES(?,?,(SELECT Max_renewals FROM Books WHERE ISBN=?))";
    try(PreparedStatement pstmt=conn.prepareStatement(sql)){
        pstmt.setString(1, ISBN);
        pstmt.setInt(2, memberID);
        pstmt.setString(3, ISBN);
        int affectedRows=pstmt.executeUpdate();
        if(affectedRows>0){
            System.out.println("Book borrowed successfully");
        }
    }catch (SQLException e){
        System.out.println("Error posting current transaction: " + e.getMessage());
    }
    return;
}

private static void updateLoanLimit(Connection conn, Scanner scanner, int memberID, int newLoanLimit) throws SQLException{
    String sql="UPDATE Members SET Loan_Limit=? WHERE Member_ID=?";
    try(PreparedStatement pstmt=conn.prepareStatement(sql)){
        pstmt.setInt(1, newLoanLimit);
        pstmt.setInt(2, memberID);
        int affectedRows=pstmt.executeUpdate();
        if(affectedRows>0){
            System.out.println("Updated member loan limit successfully");
        }
        else{
            System.out.println("No rows affected.");
        }
    }catch (SQLException e){
        System.out.println("Error updating member loan limit: " + e.getMessage());
    }
}

private static void updateBookLocation(Connection conn, Scanner scanner, String ISBN, String newLocation) throws SQLException{
    String sql="UPDATE Books SET Location=? WHERE ISBN=?";
    try(PreparedStatement pstmt = conn.prepareStatement(sql)){
        pstmt.setString(1, newLocation);
        pstmt.setString(2, ISBN);
        int affectedRows=pstmt.executeUpdate();
        if (affectedRows>0) {
            System.out.println("Book location successfully updated to "+newLocation);            
        }else{
            System.out.println("No rows affected");
        }
    }catch(SQLException e){
        System.out.println("Error updating book location: "+ e.getMessage());
    }
}

private static void returnBook(Connection conn, Scanner scanner) throws SQLException{
    System.out.println("Looks like you're returning a book. Hope you've enjoyed it!");
    String[] displayBookResult=displayBook(conn, scanner);
    String bookISBN=displayBookResult[0];
    String bookLocation=displayBookResult[1];
    if(!bookISBN.equals("on loan")){
        System.out.println("Is it the book you're returning? Enter y or n.");
        String confirmReturn=scanner.nextLine();
        if (confirmReturn.equals("y")) {
            if (bookLocation.equals("on loan")){
                System.out.println("This book is currently unavailable.");
            }else{
                int currentLoanLimit=displayLoanLimit(conn, scanner, 2);
                System.out.println("Your current loan limit is:" +currentLoanLimit);
                if(currentLoanLimit>0){
                    try{
                        postCurrentTransaction(conn, scanner, bookISBN,2);
                        int newLoanLimit=currentLoanLimit-1;
                        System.out.println("Your new loan limit is "+newLoanLimit);
                        updateLoanLimit(conn, scanner, 1,newLoanLimit);
                        System.out.println("After updating loan limit");
                        updateBookLocation(conn, scanner, bookISBN, "on loan");
                    }catch(SQLException e){
                        System.out.println("Error during transaction: " + e.getMessage());
                    }
                }else{
                    System.out.println("Exceeding loan limit, please return previous loaned books before borrowing new books.");
                }
            }
        }else{
            System.out.println("Error happened while trying to borrow the book.");
        }
    }

}

private static void displayAvailableBooks(Connection conn, Scanner scanner) throws SQLException{

}

private static void displayCurrentLoan(Connection conn, Scanner scanner) throws SQLException{

}


}