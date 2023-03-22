import java.util.Arrays;

public class Sort {
    public static void main(String[] args) {
        int[] firstNums = new int[]{16,12,10,8,4,1};
        int[] secondNums = new int[]{1,4,8,10,12,16};

        // using nano time to measure program runtime
        long startTime = System.nanoTime();
        int[] result = selection(firstNums);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.

        // Arrays.toString called outside timer, so it doesn't affect the timing of the loop
        System.out.println(Arrays.toString(result));
        System.out.printf("Duration: %d nano seconds\n", duration);

        startTime = System.nanoTime();
        result = descendSelection(secondNums);
        endTime = System.nanoTime();
        duration = (endTime - startTime);  // divide by 1000000 to get milliseconds.

        System.out.println(Arrays.toString(result));
        System.out.printf("Duration: %d nano seconds\n", duration);
    }

    public static int[] selection(int[] arr) {
        int comparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            int min = arr[i];
            int minPos = i;
            for (int j = i; j < arr.length; j++) {
                comparisons++;
                if (arr[j] < min) {
                    min = arr[j];
                    minPos = j;
                }
            }
            int tmp = arr[i];
            arr[i] = min;
            arr[minPos] = tmp;
        }
        System.out.println("Comparisons: " + comparisons);
        return arr;
    }

    public static int[] descendSelection(int[] arr) {
        int comparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            int max = arr[i];
            int maxPos = i;
            for (int j = i; j < arr.length; j++) {
                comparisons++;
                if (arr[j] > max) {
                    max = arr[j];
                    maxPos = j;
                }
            }
            int tmp = arr[i];
            arr[i] = max;
            arr[maxPos] = tmp;
        }
        System.out.println("Comparisons: " + comparisons);
        return arr;
    }
}
