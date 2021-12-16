import java.util.Arrays;

public final class Rules {
    public final int[][] positionsForTable(int movementLength) {
        int[][] matrix = new int[movementLength][movementLength];
        for (int i = 0; i < movementLength; i++) {
            Arrays.fill(matrix[i], -1);
        }
        for (int i = 0; i < movementLength; i++) {
            int count = 0;
            for (int j = i; count <= movementLength / 2; ) {
                matrix[i][j] = 1;
                if (j + 1 == movementLength) {
                    j = 0;
                    count++;
                    continue;
                }
                count++;
                j++;
            }
            matrix[i][i] = 0;
        }
        return matrix;
    }
}
