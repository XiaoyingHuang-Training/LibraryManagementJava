public class Member {
    private int id;
    private String email;

    public Member(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // toString() method (optional)
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}