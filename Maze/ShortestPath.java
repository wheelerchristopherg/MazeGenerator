import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Iterator;

public class ShortestPath {
   private boolean[][] maze;
   private boolean[][] visited;
   private Position end;
   private ArrayDeque<Node> queue;
   private LinkedList<Position> path;
   private Iterator<Position> pathIterator;
   private Position pathPosition;
   private Position previous;
   
   public ShortestPath(boolean[][] mazeIn) {
      maze = mazeIn.clone();
      visited = new boolean[maze.length][maze[0].length];
      end = new Position(maze.length - 1, maze[0].length - 1);
      queue = new ArrayDeque<>();
      queue.add(new Node(new Position(0, 0), null));
      visited[0][0] = true;
      pathPosition = new Position(0, 0);
   }
   
   public void solve() {
      Node current = null;
      while (!queue.peek().cell.equals(end)) {
         current = queue.remove();
         for (Position p : getNeighbors(current.cell)) {
            queue.add(new Node(p, current));
            visited[p.x][p.y] = true;
         }
      }
      current = queue.remove();
      path = new LinkedList<>();
      while (current != null) {
         path.addFirst(current.cell);
         current = current.parent;
      }
      pathIterator = path.iterator();
   }
   
   public void next() {
      if (hasNext()) {
         previous = pathPosition;
         pathPosition = pathIterator.next();
      }
   }
   
   public boolean hasNext() {
      return pathIterator.hasNext();
   }
   
   public void draw(Graphics2D g, int size) {
      if (previous != null) {
         g.setColor(Color.CYAN);
         g.fillRect(size + (previous.x * size), size + (previous.y * size), size, size);
      }
      g.setColor(Color.RED);
      g.fillRect(size + (pathPosition.x * size), size + (pathPosition.y * size), size, size);
   }
   
   private ArrayList<Position> getNeighbors(Position p) {
      ArrayList<Position> neighbors = new ArrayList<>();
      for (int i = 1; i <= 4; i++) {
         if (!getNeighborState(p, i)) {
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
   
   private boolean getNeighborState(Position p, int neighbor) {
      if (neighbor > 4) {
         neighbor -= 4;
      }
      if (neighbor < 1) {
         neighbor += 4;
      }
      try {
         switch (neighbor) {
            case 1:
               return maze[p.x + 1][p.y] || visited[p.x + 1][p.y];
            case 2:
               return maze[p.x][p.y + 1] || visited[p.x][p.y + 1];
            case 3:
               return maze[p.x - 1][p.y] || visited[p.x - 1][p.y];
            case 4:
               return maze[p.x][p.y - 1] || visited[p.x][p.y - 1];
            default:
               return true;
         }
      }
      catch (ArrayIndexOutOfBoundsException e) {
         return true;
      }
   }
   
   private class Node {
      Position cell;
      Node parent;
      
      public Node(Position p, Node parentIn) {
         cell = p;
         parent = parentIn;
      }
      
      public String toString() {
         return cell.toString();
      }
   }
}