import java.util.*;

public class LabyrinthTester {
    private static Scanner scanner = new Scanner(System.in);
    private static Labyrinth labyrinth;
    private static boolean[][] grid; // Для отображения состояния лабиринта
    private static int startRow, startCol, endRow, endCol;
    private static boolean startEndSet = false;
    
    public static void main(String[] args) {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n===== Тестирование лабиринта =====");
            System.out.println("1. Создать новый лабиринт");
            System.out.println("2. Установить начальную и конечную точки");
            System.out.println("3. Отметить клетки как проходимые или препятствия");
            System.out.println("4. Показать лабиринт");
            System.out.println("5. Найти путь");
            System.out.println("6. Выход");
            System.out.print("Выберите действие: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Потребляем символ новой строки
            
            switch (choice) {
                case 1:
                    createLabyrinth();
                    break;
                case 2:
                    setStartEnd();
                    break;
                case 3:
                    markCells();
                    break;
                case 4:
                    displayLabyrinth();
                    break;
                case 5:
                    findPath();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте ещё раз.");
            }
        }
        
        scanner.close();
        System.out.println("До свидания!");
    }
    
    /**
     * Создаёт новый лабиринт заданного размера
     */
    private static void createLabyrinth() {
        System.out.print("Введите количество строк: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов: ");
        int cols = scanner.nextInt();
        scanner.nextLine(); // Потребляем символ новой строки
        
        labyrinth = new Labyrinth(rows, cols);
        grid = new boolean[rows][cols];
        startEndSet = false;
        
        // Инициализируем все клетки как препятствия (непроходимые)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                labyrinth.setCell(i, j, false);
                grid[i][j] = false;
            }
        }
        
        System.out.println("Лабиринт создан с размерами " + rows + "×" + cols);
    }
    
    /**
     * Устанавливает начальную и конечную точки пути
     */
    private static void setStartEnd() {
        if (labyrinth == null) {
            System.out.println("Сначала создайте лабиринт.");
            return;
        }
        
        System.out.print("Введите начальную строку: ");
        startRow = scanner.nextInt();
        System.out.print("Введите начальный столбец: ");
        startCol = scanner.nextInt();
        System.out.print("Введите конечную строку: ");
        endRow = scanner.nextInt();
        System.out.print("Введите конечный столбец: ");
        endCol = scanner.nextInt();
        scanner.nextLine(); // Потребляем символ новой строки
        
        // Проверяем корректность координат
        if (startRow < 0 || startRow >= labyrinth.getRows() ||
            startCol < 0 || startCol >= labyrinth.getCols() ||
            endRow < 0 || endRow >= labyrinth.getRows() ||
            endCol < 0 || endCol >= labyrinth.getCols()) {
            System.out.println("Неверные координаты. Попробуйте ещё раз.");
            return;
        }
        
        // Отмечаем начальную и конечную точки как проходимые
        labyrinth.setCell(startRow, startCol, true);
        grid[startRow][startCol] = true;
        labyrinth.setCell(endRow, endCol, true);
        grid[endRow][endCol] = true;
        
        startEndSet = true;
        System.out.println("Начальная точка установлена в (" + startRow + ", " + startCol + ")");
        System.out.println("Конечная точка установлена в (" + endRow + ", " + endCol + ")");
    }
    
    /**
     * Меню для отметки клеток как проходимых или непроходимых
     */
    private static void markCells() {
        if (labyrinth == null) {
            System.out.println("Сначала создайте лабиринт.");
            return;
        }
        
        boolean continueMarking = true;
        
        while (continueMarking) {
            System.out.println("\n----- Меню отметки клеток -----");
            System.out.println("1. Отметить одну клетку");
            System.out.println("2. Отметить прямоугольную область");
            System.out.println("3. Вернуться в главное меню");
            System.out.print("Выберите действие: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Потребляем символ новой строки
            
            switch (choice) {
                case 1:
                    markSingleCell();
                    break;
                case 2:
                    markRectangleArea();
                    break;
                case 3:
                    continueMarking = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте ещё раз.");
            }
        }
    }
    
    /**
     * Отмечает одну клетку как проходимую или непроходимую
     */
    private static void markSingleCell() {
        System.out.print("Введите строку: ");
        int row = scanner.nextInt();
        System.out.print("Введите столбец: ");
        int col = scanner.nextInt();
        System.out.print("Отметить как проходимую (1) или препятствие (0)? ");
        int value = scanner.nextInt();
        scanner.nextLine(); // Потребляем символ новой строки
        
        // Проверяем корректность координат
        if (row < 0 || row >= labyrinth.getRows() || col < 0 || col >= labyrinth.getCols()) {
            System.out.println("Неверные координаты. Попробуйте ещё раз.");
            return;
        }
        
        labyrinth.setCell(row, col, value == 1);
        grid[row][col] = value == 1;
        System.out.println("Клетка (" + row + ", " + col + ") отмечена как " + 
                          (value == 1 ? "проходимая" : "препятствие"));
    }
    
    /**
     * Отмечает прямоугольную область клеток
     */
    private static void markRectangleArea() {
        System.out.print("Введите верхнюю строку: ");
        int topRow = scanner.nextInt();
        System.out.print("Введите левый столбец: ");
        int leftCol = scanner.nextInt();
        System.out.print("Введите нижнюю строку: ");
        int bottomRow = scanner.nextInt();
        System.out.print("Введите правый столбец: ");
        int rightCol = scanner.nextInt();
        System.out.print("Отметить как проходимые (1) или препятствия (0)? ");
        int value = scanner.nextInt();
        scanner.nextLine(); // Потребляем символ новой строки
        
        // Проверяем корректность координат
        if (topRow < 0 || topRow >= labyrinth.getRows() ||
            leftCol < 0 || leftCol >= labyrinth.getCols() ||
            bottomRow < 0 || bottomRow >= labyrinth.getRows() ||
            rightCol < 0 || rightCol >= labyrinth.getCols() ||
            topRow > bottomRow || leftCol > rightCol) {
            System.out.println("Неверные координаты. Попробуйте ещё раз.");
            return;
        }
        
        for (int i = topRow; i <= bottomRow; i++) {
            for (int j = leftCol; j <= rightCol; j++) {
                labyrinth.setCell(i, j, value == 1);
                grid[i][j] = value == 1;
            }
        }
        
        System.out.println("Прямоугольная область отмечена как " + 
                          (value == 1 ? "проходимая" : "препятствия"));
    }
    
    /**
     * Отображает текущее состояние лабиринта
     */
    private static void displayLabyrinth() {
        if (labyrinth == null) {
            System.out.println("Сначала создайте лабиринт.");
            return;
        }
        
        System.out.println("\nКарта лабиринта (П = проходимая, X = препятствие, Н = начало, К = конец):");
        
        // Выводим номера столбцов
        System.out.print("  ");
        for (int j = 0; j < labyrinth.getCols(); j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        
        for (int i = 0; i < labyrinth.getRows(); i++) {
            // Выводим номер строки
            System.out.print(i + " ");
            
            for (int j = 0; j < labyrinth.getCols(); j++) {
                if (startEndSet && i == startRow && j == startCol) {
                    System.out.print("Н ");
                } else if (startEndSet && i == endRow && j == endCol) {
                    System.out.print("К ");
                } else {
                    System.out.print(grid[i][j] ? "П " : "X ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Находит путь в лабиринте и отображает его
     */
    private static void findPath() {
        if (labyrinth == null) {
            System.out.println("Сначала создайте лабиринт.");
            return;
        }
        
        if (!startEndSet) {
            System.out.println("Сначала установите начальную и конечную точки.");
            return;
        }
        
        List<int[]> path = labyrinth.findPath(startRow, startCol, endRow, endCol);
        
        if (path == null) {
            System.out.println("Путь не найден от (" + startRow + ", " + startCol + ") до (" + 
                              endRow + ", " + endCol + ")");
        } else {
            System.out.println("Путь найден:");
            for (int[] cell : path) {
                System.out.println("(" + cell[0] + ", " + cell[1] + ")");
            }
            
            // Отображаем лабиринт с путём
            displayLabyrinthWithPath(path);
        }
    }
    
    /**
     * Отображает лабиринт с найденным путём
     */
    private static void displayLabyrinthWithPath(List<int[]> path) {
        if (labyrinth == null) {
            return;
        }
        
        // Преобразуем путь в множество для быстрого поиска O(1)
        Set<String> pathCells = new HashSet<>();
        for (int[] cell : path) {
            pathCells.add(cell[0] + "," + cell[1]);
        }
        
        System.out.println("\nКарта лабиринта с путём (П = проходимая, X = препятствие, " + 
                          "* = путь, Н = начало, К = конец):");
        
        // Выводим номера столбцов
        System.out.print("  ");
        for (int j = 0; j < labyrinth.getCols(); j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        
        for (int i = 0; i < labyrinth.getRows(); i++) {
            // Выводим номер строки
            System.out.print(i + " ");
            
            for (int j = 0; j < labyrinth.getCols(); j++) {
                if (i == startRow && j == startCol) {
                    System.out.print("Н ");
                } else if (i == endRow && j == endCol) {
                    System.out.print("К ");
                } else if (pathCells.contains(i + "," + j)) {
                    System.out.print("* ");
                } else {
                    System.out.print(grid[i][j] ? "П " : "X ");
                }
            }
            System.out.println();
        }
    }
}