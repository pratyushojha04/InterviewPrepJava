package practice.utilsForDSA;

import java.util.Arrays;

public class comp{
    public static void main(String[] args) {

        int[][] arr = {
            {1, 5},
            {3, 2},
            {4, 9},
            {2, 7}
        };

        // Sorting by second element arr[i][1] in DESC order
        Arrays.sort(arr, (a, b) -> Integer.compare(a[1], b[1]));
        // b[1] - a[1] would also work, but Integer.compare avoids overflow

        // Printing result
        for (int[] x : arr) {
            System.out.println(x[0] + " " + x[1]);
        }
    }
}
