import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Solution {
    public void solveSudoku(char[][] board) {
        final int[] h = new int[9], l = new int[9], d = new int[9];

        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                if (board[i][j] != '.') {
                    final int bitmask = 1 << (board[i][j] - '1');
                    h[i] |= bitmask;
                    l[j] |= bitmask;
                    d[(i / 3) + (j / 3) * 3] |= bitmask;
                }
            }
        }

        backtrack(h, l, d, board, 0, 0);
    }

    private boolean backtrack(int[] h, int[] l, int[] d, char[][] board, int i, int j) {
        if (i == board.length)
            return true;

        if (j == board.length)
            return backtrack(h, l, d, board, i + 1, 0);

        if (board[i][j] != '.')
            return backtrack(h, l, d, board, i, j + 1);

        for (int k = 0; k < 9; ++k) {
            final int bitmask = 1 << k, subgridIndex = (i / 3) + (j / 3) * 3;

            if ((h[i] & bitmask) == 0 && (l[j] & bitmask) == 0 && (d[subgridIndex] & bitmask) == 0) {
                h[i] |= bitmask;
                l[j] |= bitmask;
                d[subgridIndex] |= bitmask;

                board[i][j] = (char) ('1' + k);

                if (backtrack(h, l, d, board, i, j + 1))
                    return true;

                h[i] &= ~bitmask;
                l[j] &= ~bitmask;
                d[subgridIndex] &= ~bitmask;

                board[i][j] = '.';
            }
        }

        return false;
    }

    public char[][] readBoardFromFile(String filename) throws IOException {
        char[][] board = new char[9][9];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for (int i = 0; i < 9; i++) {
                String line = br.readLine();
                if (line != null && line.length() == 9) {
                    board[i] = line.toCharArray();
                }
            }
        }
        return board;
    }

    public void writeBoardToFile(char[][] board, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (char[] row : board) {
                writer.write(new String(row) + "\n");
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        try {
            char[][] board = solution.readBoardFromFile("C:\\Users\\gupta\\IdeaProjects\\suduko\\src\\input.txt");
            solution.solveSudoku(board);
            solution.writeBoardToFile(board, "C:\\Users\\gupta\\IdeaProjects\\suduko\\src\\solved.txt");
            System.out.println("Sudoku puzzle solved and saved to solved.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

