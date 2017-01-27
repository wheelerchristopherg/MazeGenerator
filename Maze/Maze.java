import java.util.Deque;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Maze {
   private Cells[][] maze;
   private int width;
   private int height;
   private Position end;
   
   /*
      All of these constants are possible values for each position in maze.
      VISITED means the cell is part of the path.
      HARD_WALL means the cell is a wall that cannot be overriden to be part of the path.
      WALL means the cell is a wall but can be overriden as part of the path.
      FREE means that the cell has not yet been touched by the algorithm
   */
   
   public Maze(int widthIn, int heightIn, Position endIn) {
      width = widthIn;
      height = heightIn;
      maze = new Cells[width][height];
      end = endIn;
   }
   
   public void generateMaze() {
      resetMaze();
      
      Deque<Position> stack = new ArrayDeque<>();
      Position start = new Position(0, 0);
      stack.push(start);
      Position previous = null;
      Position current = null;
      Position direction = null;
      
      while (!stack.isEmpty()) {
         previous = current;
         current = stack.peek();
         if (previous != null) {
            direction = current.sub(previous);
            Position lastWall = current.sub(direction.scale(0.5));
            maze[lastWall.x][lastWall.y] = Cells.FREE;
         }
         
         maze[current.x][current.y] = Cells.FREE;
         buildWalls(current);
         
         ArrayList<Position> freeNeighbors = getFreeNeighbors(current);
         if (freeNeighbors.isEmpty() || current.equals(end)) {
            stack.pop();
         } else {
            stack.push(choose(freeNeighbors));
         }
      }
   }
   
   /*
      neighbors are the + signs:
        +
      + 0 +
        +
      returns only those of the four that are not blocked
   */
   private ArrayList<Position> getFreeNeighbors(Position p) {
      ArrayList<Position> neighbors = new ArrayList<>();
      for (int i = 1; i < 5; i++) {
         if (getNeighborState(getNeighbor(p, i), i) == null) {
            neighbors.add(getNeighbor(getNeighbor(p, i), i));
         }
      }
      return neighbors;
   }
   
   private Position choose(ArrayList<Position> list) {
      double size = list.size();
      double choice = Math.random();
      for (double i = 1; i <= size; i++) {
         if (choice < (i / size)) {
            return list.get((int) (i - 1));
         }
      }
      return null;
   }
   
   private void buildWalls(Position current) {
      for (int i = 1; i < 5; i++) {
         Position n = getNeighbor(current, i);
         Cells state = getNeighborState(current, i);
         if (state == null) {
            maze[n.x][n.y] = Cells.WALL;
         }
      }
   }
   
   private Position getNeighbor(Position p, int neighbor) {
      if (neighbor > 4) {
         neighbor -= 4;
      }
      if (neighbor < 1) {
         neighbor += 4;
      }
      switch (neighbor) {
         case 1:
             return new Position(p.x + 1, p.y);
         case 2:
            return new Position(p.x, p.y + 1);
         case 3:
            return new Position(p.x - 1, p.y);
         case 4:
            return new Position(p.x, p.y - 1);
         default:
            return null;
      }
   }
   
   private Cells getNeighborState(Position p, int neighbor) {
      if (neighbor > 4) {
         neighbor -= 4;
      }
      if (neighbor < 1) {
         neighbor += 4;
      }
      try {
         switch (neighbor) {
            case 1:
               return maze[p.x + 1][p.y];
            case 2:
               return maze[p.x][p.y + 1];
            case 3:
               return maze[p.x - 1][p.y];
            case 4:
               return maze[p.x][p.y - 1];
            default:
               return Cells.VISITED;
         }
      }
      catch (ArrayIndexOutOfBoundsException e) {
         return Cells.VISITED;
      }
   }
   
   private void resetMaze() {
      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            if ((i % 2 == 1) && (j % 2 == 1)) {
               maze[i][j] = Cells.WALL;
            } else {
               maze[i][j] = null;
            }
         }
      }
   }
   
   public Cells[][] getMaze() {
      Cells[][] mazeOut = new Cells[width][height];
      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            mazeOut[i][j] = maze[i][j];
         }
      }
      return mazeOut;
   }
   
   public String toString() {
      String output = "";
      for (int i = 0; i < width + 2; i++) {
         output += "[]";
      }
      output += "\n";
      for (int y = 0; y < height; y++) {
         output += "[]";
         for (int x = 0; x < width; x++) {
            if (maze[x][y] == Cells.WALL) {
               output += "[]";
            } else {
               output += "  ";
            }
         }
         output += "[]\n";
      }
      for (int i = 0; i < width + 2; i++) {
         output += "[]";
      }
      output += "\n";
      return output;
   }
}