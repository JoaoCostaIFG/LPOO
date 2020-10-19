import java.util.Scanner;

public class Application {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        int a = scanner.nextInt();
        int b = scanner.nextInt();
        String str = scanner.nextLine();

        System.out.printf("Result: %d\n", a + b);
        System.out.println(str);
    }
}
