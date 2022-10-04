package prog01;

import java.util.*;

public class Hello {
    public static void main(String[] args) {
        System.out.println("hello");
        System.out.println("hello again");
        System.out.println("hello again again");
        int [] array = {1,2};
    }
    public int[] twoSum(int[] nums, int target) {
        int array[] = new int[2];
        boolean done = false;
        while (!done) {
            for (int i = 0; i < nums.length; i++) {
                for (int j = 0; j < nums.length; j++) {
                    if (i != j && nums[i] + nums[j] == target) {
                        array[0] = j;
                        array[1] = i;
                        done = true;
                    }

                }

            }

        }
        return(array);
    }

    }
}
