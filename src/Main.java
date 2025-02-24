public class Main {
    public static void main(String[] args) {

      var labyrinth  = new Labyrinth(3, 3);

        for (int i = 0; i < labyrinth.getRows(); i++) {
            for (int j = 0; j < labyrinth.getCols(); j++) {
                labyrinth.setCell(i, j, false);
            }
        }

        // Сделаем дорожку: (0,0) -> (0,1) -> (0,2) -> (0,3) -> (1,3) -> (2, 3) -> (3,3)
        // (1,0) -> (1,1) -> (2,1) -> (3, 1) -> (3,2)

        /**
         *
         *
         *                 {0, 0, 0, 0},
         *                 {0, 0, 1, 0},
         *                 {1, 0, 1, 0},
         *                 {1, 0, 0, 0},
         *
         *
         *
         *
         *
         *
         */
        labyrinth.setCell(0,0,true);
        labyrinth.setCell(0,1,true);
        labyrinth.setCell(0,2,true);
        labyrinth.setCell(0,3,true);
        labyrinth.setCell(1,3,true);
        labyrinth.setCell(2,3,true);
        labyrinth.setCell(3,3,true);

        labyrinth.setCell(1,0,true);
        labyrinth.setCell(1,1,true);
        labyrinth.setCell(2,1,true);
        labyrinth.setCell(3,1,true);
        labyrinth.setCell(3,2,true);

        var path = labyrinth.findPath(0, 0, 2, 3);

        if(path != null) {
            System.out.println("Найден путь: Координаты по шагам:");
            for (int[] cell : path) {
                System.out.println("(" + cell[0] + ", " + cell[1] + ")");
            }
        }


    }
}