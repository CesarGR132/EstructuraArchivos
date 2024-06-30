import Repository.*;

public class Main {
    public static void main(String[] args) {
        RepositorUnivesity repository = new operations();
        System.out.println(repository.login("kevin345", "atlas123"));
    }
}