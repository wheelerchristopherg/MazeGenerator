import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.Color;

public class Solver {
   private Cells[][] maze;
   private Position end;
   private ArrayDeque<Position> stack;
   
   public void start(Cells[][] mazeIn, Position endIn) {
      maze = mazeIn;
      end = endIn;
      stack = new ArrayDeque<>();
      stack.push(new Position(0, 0));
      maze[0][0] = Cells.RAT;
      maze[end.x][end.y] = Cells.END;
      for (int i = 1; i <= 4; i++) {
         if (getNeighborState(end, i) == Cells.FREE) {
            Position neighbor = getNeighbor(end, i);
            maze[neighbor.x][neighbor.y] = Cells.ALMOST_END;
         }
      }
   }
   
   public void next() {
      if ((!stack.isEmpty()) && (!stack.peek().equals(end))) {
         Position current = stack.peek();
         ArrayList<Position> neighbors = getNeighbors(current);
         
         if (!neighbors.isEmpty()) {
            Position next = choose(neighbors);
            stack.push(next);
            maze[current.x][current.y] = Cells.VISITED;
            maze[next.x][next.y] = Cells.RAT;
         } else {
            maze[current.x][current.y] = Cells.DEAD_END;
            stack.pop();
            current = stack.peek();
            maze[current.x][current.y] = Cells.RAT;
         }
      }
   }
   
   public boolean hasNext() {
      return !stack.peek().equals(end);
   }
   
   private ArrayList<Position> getNeighbors(Position p) {
      ArrayList<Position> neighbors = new ArrayList<>();
      for (int i = 1; i <= 4; i++) {
         Cells state = getNeighborState(p, i);
         if ((state == Cells.FREE) || (state == Cells.END) || (state == Cells.ALMOST_END)) {
            if ((state == Cells.END) || (state == Cells.ALMOST_END)) {
               neighbors = new ArrayList<>();
               neighbors.add(getNeighbor(p, i));
               return neighbors;
            }
            neighbors.add(getNeighbor(p, i));
         }
      }
      return neighbors;
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
}