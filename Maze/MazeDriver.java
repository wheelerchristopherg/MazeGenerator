import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.SwingUtilities;

public class MazeDriver implements Runnable {
   
   public static int cellSize = 15;
   public static int wallSize = 5;
   public static int count = 5;
   
   public static void main(String[] args) {
      if (args.length > 1) {
         cellSize = Integer.parseInt(args[0]);
         wallSize = Integer.parseInt(args[1]);
         count = Integer.parseInt(args[2]);
      }
      SwingUtilities.invokeLater(new MazeDriver());
   }
   
   public void run() {      
      if (cellSize + wallSize % 2 == 1) {
         wallSize--;
      }
      
      int fullWidth = 1900 / ((cellSize + wallSize) / 2);
      int fullHeight = 980 / ((cellSize + wallSize) / 2);
      
      int width = fullWidth;
      int height = fullHeight;
      
      if (width % 2 == 0) {
         width--;
      }
      
      if (height % 2 == 0) {
         height--;
      }
      
      JFrame window = new JFrame("Maze Solver");
      window.setSize(new Dimension((width + 1) * ((cellSize + wallSize) / 2) + wallSize + 8, (height + 1) * ((cellSize + wallSize) / 2) + wallSize + 37));
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setResizable(false);
      
      Panel panel = new Panel(width, height, cellSize, wallSize, count);
      panel.setPreferredSize(new Dimension(cellSize * (width + 2), cellSize * (height + 2)));
      window.add(panel);
      
      window.setVisible(true);
   }
}