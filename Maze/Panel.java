import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;

public class Panel extends JPanel implements Runnable {
   private Maze mazeGenerator;
   private Position end;
   private Solver solver;
   private ShortestPath path;
   private Thread t;
   private Cells[][] maze;
   private int cellSize;
   private int wallSize;
   private int width;
   private int height;
   private int count;
   
   private static final long speed = 60; // miliseconds
   
   public Panel(int widthIn, int heightIn, int cellSizeIn, int wallSizeIn, int countIn) {
      cellSize = cellSizeIn;
      wallSize = wallSizeIn;
      count = countIn;
      width = (widthIn % 2 == 1) ? widthIn : widthIn - 1;
      height = (heightIn % 2 == 1) ? heightIn : heightIn - 1;
      end = new Position(width - 1, height - 1);
      mazeGenerator = new Maze(width, height, end);
      mazeGenerator.generateMaze();
      maze = mazeGenerator.getMaze();
      solver = new Solver();
      //path = new ShortestPath(maze);
      t = new Thread(this);
      t.start();
   }
   
   public void run() {
      sleep(1000);
      for (int i = 1; i <= count; i++) {
         
         System.out.print(i + "" + ((i % 5 == 0) ? "\n" : ", "));
         
         
         solver.start(maze, end);
         repaint();
         sleep(1000);
      
         while (solver.hasNext()) {
            solver.next();
            solver.next();
            repaint();
            sleep(speed);
         }
         Color ratColor = Cells.RAT.getColor();
         for (int j = 0; j < 4; j++) {
            sleep(750);
            Cells.RAT.setColor(Color.BLUE);
            repaint();
            sleep(750);
            Cells.RAT.setColor(ratColor);
            repaint();
         }
         sleep(1000);
         
         mazeGenerator.generateMaze();
         maze = mazeGenerator.getMaze();
      }
      System.exit(0);
   }
   
   private void sleep(long time) {
      try {
         Thread.currentThread();
         Thread.sleep(time);
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   private void draw(Graphics2D g) {
      g.setColor(Cells.WALL.getColor());
      g.fillRect(0, 0, (width + 1) * ((cellSize + wallSize) / 2) + wallSize, wallSize);
      g.fillRect(0, (height + 1) * ((cellSize + wallSize) / 2), (width + 1) * ((cellSize + wallSize) / 2) + wallSize, wallSize);
      
      g.fillRect(0, wallSize, wallSize, (height + 1) * ((cellSize + wallSize) / 2));
      g.fillRect((width + 1) * ((cellSize + wallSize) / 2), wallSize, wallSize, (height + 1) * ((cellSize + wallSize) / 2));
      
      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            fillCell(g, i, j, maze[i][j].getColor());
         }
      }
   }
   
   private void fillCell(Graphics2D g, int x, int y, Color c) {
      g.setColor(c);
      if ((x % 2 == 1) && (y % 2 == 1)) {
         g.fillRect((x + 1) * ((cellSize + wallSize) / 2), (y + 1) * ((cellSize + wallSize) / 2), wallSize, wallSize);
      } else if (x % 2 == 1) {
         g.fillRect((x + 1) * ((cellSize + wallSize) / 2), (y * ((cellSize + wallSize) / 2)) + wallSize, wallSize, cellSize);
      } else if (y % 2 == 1) {
         g.fillRect((x * ((cellSize + wallSize) / 2)) + wallSize, (y + 1) * ((cellSize + wallSize) / 2), cellSize, wallSize);
      } else {
         g.fillRect(x * ((cellSize + wallSize) / 2) + wallSize, y * ((cellSize + wallSize) / 2) + wallSize, cellSize, cellSize);
      }
   }
   
   public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      //g2d.clearRect(0, 0, getWidth(), getHeight());
      draw(g2d);
   }
}