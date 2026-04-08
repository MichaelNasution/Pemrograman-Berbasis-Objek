package driver;

import java.util.Scanner;
import model.Solution;

public class Driver1 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Solution sol = new Solution();

        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            String kode = sc.next();
            int porsiButet = sc.nextInt();
            sol.addOrder(kode, porsiButet);
        }

        sol.printStruk();
        sc.close();
    }
}