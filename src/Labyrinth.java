import java.util.*;

public class Labyrinth {
    private int rows;
    private int cols;
    private boolean[][] grid;

    public Labyrinth(int rows, int cols) {
        setRows(rows);
        setCols(cols);
        setGrid(new boolean[rows][cols]);
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    private void setGrid(boolean[][] grid) {
        this.grid = grid;
    }

    public void setCell(int row, int col, boolean value) {
        if(row >= 0 && row < rows && col >= 0 && col < cols) {
         grid[row][col] = value;
        }
    }

    public boolean isCellPassable(int row, int col) {
        if(row >= 0 && row < rows && col >= 0 && col < cols) {
           return grid[row][col];
        }
        return false;
    }

    public List<int[]> findPath(int startRow, int startCol, int endRow, int endCol) {
        // TODO: нужно создать массив для отметки посещенных клеток
        boolean[][] visited = new boolean[rows][cols];

        Queue<int[]> queue = new LinkedList<>();

        // Массив для хранения предков (откуда мы пришли чтобы восставновить путь)
        int[][][] parents = new int[rows][cols][2];
        // parents[row][col] = {parentRow, parentCol}

        visited[startRow][startCol] = true; // {0,0}

        queue.add(new int[]{startRow, startCol});

        // Перебор соседей: вверх, вниз, влево, вправо
        // {-1, 0}
        // {1, 0}
        // {0, -1}
        // {0, 1}
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Запускаем BFS
        while(!queue.isEmpty()) {

            // Если мы дошли до конечной клетки, восстанавливаем путь
            // если currentRow === endRow И currentCol === endCol
            int[] current = queue.poll();
            int currentRow = current[0];
            int currentCol = current[1];
            if(currentRow == endRow  &&  currentCol == endCol){
                 return reconstructPath(parents, startRow, startCol, endRow, endCol);
            }

            // Иначе смотрим соседей
            for (int[] direction : directions) {
                int newRow = currentRow + direction[0]; // -1 вверх
                int newCol = currentCol + direction[1]; // 0
                // Проверяем что новая клетка:
                // 1. в пределах лабиринта и проходима + еще не посещена
                if(isCellPassable(newRow, newCol) && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    queue.add(new int[]{newRow, newCol});
                    // Запоминаем откуда пришли в эту клетку
                    parents[newRow][newCol][0] = currentRow;
                    parents[newRow][newCol][1] = currentCol;

                }


            }


        }

        // если очередь пустая но так и не нашли - null
        return null;
    }

    /**
     * Вспомонательный метод для восстановленрия пути из массива  родителей
     */
    private List<int[]> reconstructPath(int[][][]parents,  int startRow, int startCol, int endRow, int endCol) {

        List<int[]> path = new ArrayList<>();

        int currentRow = endRow;
        int currentCol = endCol;

        // Двигаемся назад пока не дойдем до старта
        while (!(currentRow == startRow && currentCol == startCol)) {
            path.add(new int[]{currentRow, currentCol});
            int tempRow = parents[currentRow][currentCol][0];
            int tempCol = parents[currentRow][currentCol][1];
            currentRow = tempRow;
            currentCol = tempCol;
        }
        // Добавить нач клетку
        path.add(new int[]{startRow, startCol});


        // Развернуть список
        Collections.reverse(path);

        return path;





    }



}
