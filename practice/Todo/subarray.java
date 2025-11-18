package practice.Todo;

import java.util.ArrayList;

public class subarray {

    public static int max(int[] arr) {
        int maxi = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxi) maxi = arr[i];
        }
        return maxi;
    }

    public static int sum(ArrayList<Integer> nums) {
        int total = 0;
        for (int num : nums) total += num;
        return total;
    }

    static ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
    static int[] arr;
    static int n;

    public static void helper(int ind, ArrayList<Integer> cur) {
        if (ind == n) {
            ans.add(new ArrayList<>(cur)); 
            return;
        }

        // take
        cur.add(arr[ind]);
        helper(ind + 1, cur);

        // not take
        cur.remove(cur.size() - 1);
        helper(ind + 1, cur);
    }

    public static void main(String[] args) {
        arr = new int[]{2, 1, 3, 34, 3};
        int target = 9;
        n = arr.length;

        System.out.println("Maximum: " + max(arr));

        helper(0, new ArrayList<>());
        System.out.println(ans);

        boolean found = false;
        for (ArrayList<Integer> subset : ans) {
            if (sum(subset) == target) {
                found = true;
                break;
            }
        }

        System.out.println("Target Exists: " + found);
    }
}
