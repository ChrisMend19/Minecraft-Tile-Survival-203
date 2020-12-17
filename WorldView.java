import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

/*
WorldView ideally mostly controls drawing the current part of the whole world
that we can see based on the viewport
*/

final class WorldView
{
   private PApplet screen;
   private WorldModel world;
   private int tileWidth;
   private int tileHeight;
   private Viewport viewport;

   private boolean portalTaken = false;

   public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
      int tileWidth, int tileHeight)
   {
      this.screen = screen;
      this.world = world;
      this.tileWidth = tileWidth;
      this.tileHeight = tileHeight;
      this.viewport = new Viewport(numRows, numCols);
   }

   public void drawBackground()
   {
      for (int row = 0; row < this.viewport.getNumRows(); row++)
      {
         for (int col = 0; col < this.viewport.getNumCols(); col++)
         {
            Point worldPoint = viewport.viewportToWorld(viewport, col, row);
            Optional<PImage> image = this.world.getBackgroundImage(
                    worldPoint);
            if (image.isPresent())
            {this.screen.image(image.get(), col * this.tileWidth,
                       row * this.tileHeight);
            }
         }
      }
   }

   public void drawEntities()
   {
      for (Entity entity : this.world.getEntities())
      {
         Point pos = entity.getPosition();

         if (this.viewport.contains(pos))
         {
            Point viewPoint = worldToViewport(this.viewport, pos.getX(), pos.getY());
            this.screen.image(entity.getCurrentImage(entity),
                    viewPoint.getX() * this.tileWidth, viewPoint.getY() * this.tileHeight);
         }
      }
   }

//   public Point viewportToWorld(Viewport viewport, int col, int row)
//   {
//      return new Point(col + viewport.col, row + viewport.row);
//   }

   public Point worldToViewport(Viewport viewport, int col, int row)
   {
      return new Point(col - viewport.getCol(), row - viewport.getRow());
   }

   public void drawViewport()
   {
      drawBackground();
      drawEntities();
   }

   public void shiftView(int colDelta, int rowDelta)
   {
      //System.out.println(world.getSteve().getPosition().x + colDelta);
//      int newCol = clamp(world.getSteve().getPosition().x + colDelta, 0,
//              this.world.getNumCols() - this.viewport.getNumCols());
//      int newRow = clamp(world.getSteve().getPosition().y + rowDelta, 0,
//              this.world.getNumRows() - this.viewport.getNumRows());
      int newCol = clamp(world.getSteve().getPosition().x + colDelta, 0 , 39);
      int newRow = clamp(world.getSteve().getPosition().y + rowDelta, 0,  29);





            if(VirtualWorld.netherBool) {
               if (new Point(newCol, newRow).equals(new Point(0, 11)) && world.getSteve().getPortalCount() > 0) {
                  world.getSteve().setPosition(new Point(20, 1));
                  world.getSteve().setPortalCount(world.getSteve().getPortalCount() - 1);
                  portalTaken = true;
               }
               if (new Point(newCol, newRow).equals(new Point(20, 0)) && world.getSteve().getPortalCount() > 0) {
                  world.getSteve().setPosition(new Point(1, 11));
                  world.getSteve().setPortalCount(world.getSteve().getPortalCount() - 1);
                  portalTaken = true;
               }
               if (new Point(newCol, newRow).equals(new Point(0, 29)) && world.getSteve().getPortalCount() > 0) {
                  world.getSteve().setPosition(new Point(39, 15));
                  world.getSteve().setPortalCount(world.getSteve().getPortalCount() - 1);
                  portalTaken = true;
               }
               if (new Point(newCol, newRow).equals(new Point(39, 14)) && world.getSteve().getPortalCount() > 0) {
                  world.getSteve().setPosition(new Point(0, 28));
                  world.getSteve().setPortalCount(world.getSteve().getPortalCount() - 1);
                  portalTaken = true;
               }
               if (new Point(newCol, newRow).equals(new Point(19, 29)) && world.getSteve().getPortalCount() > 0) {
                  world.getSteve().setPosition(new Point(19, 15));
                  world.getSteve().setPortalCount(world.getSteve().getPortalCount() - 1);
                  portalTaken = true;
               }
               if (new Point(newCol, newRow).equals(new Point(19, 14)) && world.getSteve().getPortalCount() > 0) {
                  world.getSteve().setPosition(new Point(19, 28));
                  world.getSteve().setPortalCount(world.getSteve().getPortalCount() - 1);
                  portalTaken = true;
               }
               if (new Point(newCol, newRow).equals(new Point(39, 29)) && world.getSteve().getPortalCount() > 0) {
                  world.getSteve().setPosition(new Point(39, 1));
                  world.getSteve().setPortalCount(world.getSteve().getPortalCount() - 1);
                  portalTaken = true;
               }
               if (new Point(newCol, newRow).equals(new Point(39, 0)) && world.getSteve().getPortalCount() > 0) {
                  world.getSteve().setPosition(new Point(39, 28));
                  world.getSteve().setPortalCount(world.getSteve().getPortalCount() - 1);
                  portalTaken = true;
               }
            }

               if (!world.isOccupied(new Point(newCol, newRow)))
               world.getSteve().setPosition(new Point(newCol, newRow));



               //world.getSteve().setPosition(new Point(newCol, newRow));



            //System.out.println("X: " + world.getSteve().getPosition().x + "Y: " + world.getSteve().getPosition().y );
//         if(world.getSteve().getPosition().x == 31 || world.getSteve().getPosition().x == 0){
//            world.getSteve().setPosition(new Point(0,newRow));
//         }

            //world.getSteve().setPosition(new Point(newCol, newRow));





      double counter = 0;
      for(Entity entity: world.getEntity()) {
         if (entity instanceof Creeper && counter % 2 == 0) {
            Optional<Entity> target = world.findNearest(entity.getPosition(), Steve.class);
            Point nextPos = ((Creeper)entity).nextPositionCreeper(world, target.get().getPosition());
            world.moveEntity(entity, nextPos);
//            if(world.findNearest(nextPos, Ghast.class).get() != null && world.findNearest(nextPos, Ghast.class).get().getPosition().adjacent(nextPos)){
//               world.removeEntity(entity);
//            }
         }
         if (entity instanceof Ghast && counter % 3 == 0) {
            Optional<Entity> target = world.findNearest(entity.getPosition(), Steve.class);
            Point nextPos = ((Ghast)entity).nextPositionGhast(world, target.get().getPosition());
            world.moveEntity(entity, nextPos);
         }

         if(entity.getPosition().adjacent(world.getSteve().position) && (world.getSteve().getHealth() > 0) && entity instanceof Creeper){
            world.getSteve().setHealth(world.getSteve().getHealth() - 1);
         }
         if(entity.getPosition().adjacent(world.getSteve().position) && (world.getSteve().getHealth() > 0) && entity instanceof Zombie){
            world.getSteve().setHealth(world.getSteve().getHealth() - 1);
         }
         if(entity.getPosition().adjacent(world.getSteve().position) && (world.getSteve().getHealth() > 0) && entity instanceof Ghast){
            if((world.getSteve().getHealth() - 3) >=  0)
            world.getSteve().setHealth(world.getSteve().getHealth() - 3);
            else if((world.getSteve().getHealth() - 2) >=  0)
               world.getSteve().setHealth(world.getSteve().getHealth() - 2);
            else if((world.getSteve().getHealth() - 1) >=  0)
               world.getSteve().setHealth(world.getSteve().getHealth() - 1);
            else{
               world.getSteve().setHealth(world.getSteve().getHealth());
            }
         }

         counter++ ;
      }

      //if(world.getSteve().getPosition())
      if(world.findNearest(world.getSteve().getPosition(), Diamond.class) != null) {
         Optional<Entity> steveTarget = world.findNearest(world.getSteve().getPosition(), Diamond.class);
         if (world.getSteve().getPosition().adjacent(steveTarget.get().getPosition())) {

            world.removeEntity(steveTarget.get());
            world.getSteve().setDiamondCount((world.getSteve().getDiamondCount()) + 1);
         }
//      if(world.findNearest(world.getSteve().getPosition(), Skeleton.class) != null) {
//         Optional<Entity> skellyTarget = world.findNearest(world.getSteve().getPosition(), Skeleton.class);
//         if (world.getSteve().getPosition().adjacent(skellyTarget.get().getPosition())) {
//            world.getSteve().setHealth((world.getSteve().getHealth()) - 1);
//         }
//      }

      }

      //viewport.shift(newCol, newRow);
      //int newCol = clamp(world.getLion().getPosition().x + colDelta, 0,
      //              this.world.getNumCols() - this.viewport.getNumCols());
      //      int newRow = clamp(world.getLion().getPosition().y + rowDelta, 0,
      //              this.world.getNumRows() - this.viewport.getNumRows());
      //      if(!world.isOccupied(new Point(newCol, newRow))){
      //         world.getLion().setPosition(new Point(newCol, newRow));
      //
      //      }
   }

   public static int clamp(int value, int low, int high)
   {
      return Math.min(high, Math.max(value, low));
   }




}
