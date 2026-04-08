package driver;

import java.util.Scanner;
import model.Solution;

public class Driver2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] data = new int[N];

        for (int i = 0; i < N; i++) {
            data[i] = sc.nextInt();
        }

        int k = sc.nextInt();

        Solution sol = new Solution();
        int result = sol.sumKelompok(data, k);

        System.out.println(result);

        sc.close();
    }
}