public class Position {
   int x;
   int y;
   
   public Position(int xIn, int yIn) {
      x = xIn;
      y = yIn;
   }
   
   public boolean equals(Position b) {
      return ((x == b.x) && (y == b.y));
   }
 
   public String toString() {
      return ("(" + x + ", " + y + ")");
   }
   
   public Position add(Position p) {
      return new Position(p.x + x, p.y + y);
   }
   
   public Position sub(Position p) {
      return new Position(x - p.x, y - p.y);
   }
   
   public Position scale(double scalar) {
      return new Position((int)(x * scalar), (int)(y * scalar));
   }
}