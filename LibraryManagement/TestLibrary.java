import java.util.List;

public class TestLibrary {
    public static void main(String[] args) {
        GetMembers getMembers = new GetMembers();
        List<Member> members = getMembers.getAllMembers();

        System.out.println("Members in the library:");
        for (Member member : members) {
            System.out.println(member);
        }
    }
}
