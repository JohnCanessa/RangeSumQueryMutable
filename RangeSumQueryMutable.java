import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


/**
 * LeetCode 307. Range Sum Query - Mutable
 * https://leetcode.com/problems/range-sum-query-mutable/
 * 
 * Runtime: 90 ms, faster than 79.78% of Java online submissions.
 * Memory Usage: 116.9 MB, less than 34.09% of Java online submissions.
 * 
 * 15 / 15 test cases passed.
 * Status: Accepted
 * Runtime: 90 ms
 * Memory Usage: 116.9 MB
 */
public class RangeSumQueryMutable {


    /**
     * 
     */
    static class NumArray {


        /**
         * Class members.
         */
        public int[] arr    = null;
        public int[] nums   = null; 
        public int n        = 0;


        /**
         * Constructor.
         * Initializes the object with the integer array nums.
         */
        public NumArray(int[] nums) {
            
            // **** initialization ****
            this.n      = nums.length;
            this.arr    = new int[this.n + 1];
            this.nums   = new int[this.n];

            // **** populate array ****
            for (int i = 0; i < this.n; i++)
                populate(i, nums[i]);

            // **** make copy of nums ****
            this.nums = Arrays.copyOf(nums, this.n);
        }
        

        /**
         * Populate the arr[] with the value of nums[].
         */
        private void populate(int index, int val) {

            // **** increment index ****
            index += 1;
        
            // **** traverse all ancestors and add 'val' ****
            while (index <= this.n) {

                // **** add 'val' to current node of BIT Tree ****
                this.arr[index] += val;

                // **** update index to that of parent in update View ****
                index += index & (-index);
            }
        }


        /**
         * Updates the value of nums[index] to be val.
         * 
         * Execution: O(log(n)) - Space: O(n)
         */
        public void update(int index, int val) {

            // **** update value ****
            int updateVal = val - nums[index];

            // **** save value in nums[] ****
            this.nums[index] = val;
            
            // **** increment index ****
            index += 1;
        
            // **** traverse all ancestors and add 'val' ****
            while (index <= this.n) {

                // **** add 'val' to current node of the ****
                this.arr[index] += updateVal;

                // **** update index to that of parent in update View ****
                index += index & (-index);
            }
        }
        

        /**
         * Returns the sum of the elements of nums between indices 
         * left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
         * 
         * Execution: O(log(n)) - Space: O(n)
         */
        public int sumRange(int left, int right) {
            return getSum(right) - getSum(left - 1);
        }


        /**
         * Returns sum of BITree[0..index]
         * 
         * Execution: O(log(n)) - Space: O(n)
         */
        private int getSum(int index) {

            // **** initialization ****
            int sum = 0;
            index   += 1;

            // **** traverse nodes ****
            while (index > 0) {

                // **** ****
                sum += this.arr[index];

                // **** update index ****
                index -= index & (-index);
            }

            // **** return sum ****
            return sum;
        }
    }


    /**
     * Test scaffold
     * @throws IOException
     * 
     * !!! NOT PART OF SOLUTION !!!
     */
    public static void main(String[] args) throws IOException {
        
        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read cmdArr ****
        String[] cmdArr = Arrays.stream(br.readLine().trim().split(","))
                                .map(cmd -> cmd.trim())
                                .toArray(size -> new String[size]);

        // **** for ease of use ****
        int m = cmdArr.length;

        // **** declare argArr ****
        int[][] argArr = new int[m][];

        // **** populate argArr ****
        for (int i = 0; i < m; i++)
            argArr[i] = Arrays.stream(br.readLine().trim().split(","))
                                .map(cmd -> cmd.trim())
                                .mapToInt(Integer::parseInt)
                                .toArray();
    
        // **** close buffered reader ****
        br.close();

        // **** compute n ****
        int n = argArr[0].length;
        
        // ???? ????
        System.out.println("main <<<      n: " + n);
        System.out.println("main <<<      m: " + m);
        System.out.println("main <<< cmdArr: " + Arrays.toString(cmdArr));
        System.out.println("main <<< argArr: ");
        for (int i = 0; i < argArr.length; i++)
            System.out.println(Arrays.toString(argArr[i]));

        // **** holds output ****
        Integer[] output = new Integer[m];

        // **** ****
        NumArray tree = null;

        // **** loop calling class methods ****
        for (int i = 0; i < m; i++) {
            switch (cmdArr[i]) {
                case "NumArray":
                    tree = new NumArray(argArr[i]);
                break;

                case "sumRange":
                    output[i] = tree.sumRange(argArr[i][0], argArr[i][1]);
                break;

                case "update":
                    tree.update(argArr[i][0], argArr[i][1]);
                break;

                case "getSum":
                    output[i] = tree.getSum(argArr[i][0]);
                break;

                default:
                    System.out.println("main <<< UNEXPECTED cmdArr[" + i + "] ==>" + cmdArr[i] + "<==");
                break;
            }
        } 

        // **** display output ****
        System.out.println("main <<< output: " + Arrays.toString(output));
    }
}