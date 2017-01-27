import java.awt.Color;

public enum Cells 
{
   FREE (new Color(73, 45, 44)),
   WALL (new Color(0, 100, 0)),
   VISITED (new Color(199, 168, 46)),
   DEAD_END (Color.BLACK),
   RAT (Color.CYAN.darker()),
   ALMOST_END (FREE.getColor()),
   END (Color.RED);
   
   private Color color;
   
   Cells(Color c) {
      color = c;
   }
   
   public Color getColor() {
      return color;
   }
   
   public void setColor(Color c) {
      color = c;
   }
}