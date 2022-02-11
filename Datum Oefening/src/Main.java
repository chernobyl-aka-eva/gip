import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner invoer = new Scanner(System.in);
        System.out.println("Eerste Datum: (bv. 07/02/2002)");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("mm/dd/yyyy");
        LocalDate eersteDatum = LocalDate.parse(invoer.next(), dateFormat);
        System.out.println(eersteDatum);
        System.out.println("Tweeede Datum: (bv. 27/09/2002");
        LocalDate tweedeDatum = LocalDate.parse(invoer.next(), dateFormat);
        System.out.println(tweedeDatum);
    }
}
