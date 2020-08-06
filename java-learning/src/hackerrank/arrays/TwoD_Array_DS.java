package hackerrank.arrays;

public class TwoD_Array_DS {

	static int hourglassSum(int[][] arr) {
		int sum = 0;
		boolean init = true;
		for (int i = 1; i < arr.length - 1; i++) {
			int[] js = arr[i];
			for (int j = 1; j < js.length - 1; j++) {
				int localsum = arr[i - 1][j - 1] + arr[i - 1][j] + arr[i - 1][j + 1] + arr[i][j] + arr[i + 1][j + 1]
						+ arr[i + 1][j] + arr[i + 1][j - 1];
				if (init) {
					sum = localsum;
					init = false;
					continue;
				}
				if (localsum > sum) {
					sum = localsum;
				}
			}
		}
		return sum;
	}

	static void print2DArray(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			int[] js = arr[i];
			for (int j = 0; j < js.length; j++) {
				System.out.print(arr[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int[][] arr = { { -9, -9, -9, 1, 1, 1 }, { 0, -9, 0, 4, 3, 2 }, { -9, -9, -9, 1, 2, 3 }, { 0, 0, 8, 6, 6, 0 },
				{ 0, 0, 0, -2, 0, 0 }, { 0, 0, 1, 2, 4, 0 } };
		System.out.println(String.format("Total=%s", hourglassSum(arr)));
	}

}
