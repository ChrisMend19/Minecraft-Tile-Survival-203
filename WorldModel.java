import processing.core.PImage;

import java.util.*;

/*
WorldModel ideally keeps track of the actual size of our grid world and what is in that world
in terms of entities and background elements
 */

final class WorldModel
{
   private int numRows;
   private int numCols;
   private Background background[][];
   private Entity occupancy[][];
   private Set<Entity> entities;

   private Steve steve; //set equal 2 parse
   private Ghast ghast;
   private Diamond diamond;


//   final String QUAKE_ID = "quake";
//   final int QUAKE_ACTION_PERIOD = 1100;
//   final int QUAKE_ANIMATION_PERIOD = 100;

   final int Zombie_REACH = 1;

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public void setSteve(Steve steve) {
      this.steve = steve;
   }

   public Steve getSteve() {
      return steve;
   }

   public Diamond getDiamond() {
      return diamond;
   }

   public void setDiamond(Diamond diamond) {
      this.diamond = diamond;
   }

   public Ghast getGhast(Point p) {
      return (Ghast)findNearest(p, Ghast.class).get();

   }

   public void setGhast(Ghast ghast) {
      this.ghast = ghast;
   }

   public int getNumRows() {
      return numRows;
   }

   public int getNumCols() {
      return numCols;
   }

   public Background[][] getBackground() {
      return background;
   }

   public Entity[][] getOccupancy() {
      return occupancy;
   }

   public Set<Entity> getEntities() {
      return entities;
   }

   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (withinBounds(pos))
      {
         return Optional.of((getBackgroundCell(pos)).getCurrentImage(getBackgroundCell(pos)));
      }
      else
      {
         return Optional.empty();
      }
   }

   public Background getBackgroundCell(Point pos)
   {
      return background[pos.getY()][pos.getX()];
   }

   public boolean withinBounds(Point pos)
   {
      return pos.getY() >= 0 && pos.getY() < numRows &&
              pos.getX() >= 0 && pos.getX() < numCols;
   }

   public void setBackground(Point pos,
                                    Background background)
   {
      if (withinBounds(pos)) {
         setBackgroundCell(this, pos, background);
      }
   }

   public void setBackgroundCell(WorldModel world, Point pos,
                                        Background background)
   {
      world.background[pos.getY()][pos.getX()] = background;
   }

//   public Entity createNether(String id, Point position,
//                                       List<PImage> images)
//   {
//      return new Entity(EntityKind.Nether, id, position, images,
//              0, 0);
//   }

//   public Entity createSkeletonFull(String id, int resourceLimit,
//                                       Point position, int actionPeriod, int animationPeriod,
//                                       List<PImage> images)
//   {
//      return new Entity(EntityKind.Skeleton_FULL, id, position, images,
//              actionPeriod, animationPeriod);
//   }

//   public Skeleton createSkeletonNotFull(String id, int resourceLimit,
//                                          Point position, int actionPeriod, int animationPeriod,
//                                          List<PImage> images)
//   {
//      return new Skeleton(EntityKind.Skeleton_NOT_FULL, id, position, images,
//              actionPeriod, animationPeriod);
//   }

//   public Entity createObstacle(String id, Point position,
//                                       List<PImage> images)
//   {
//      return new Entity(EntityKind.OBSTACLE, id, position, images,
//              0, 0);
//   }

//   public Entity createZombie(String id, Point position, int actionPeriod,
//                                   List<PImage> images)
//   {
//      return new Entity(EntityKind.Zombie, id, position, images,
//              actionPeriod, 0);
//   }

//   public Entity createCrab(String id, Point position,
//                                   int actionPeriod, int animationPeriod, List<PImage> images)
//   {
//      return new Entity(EntityKind.CRAB, id, position, images,
//              actionPeriod, animationPeriod);
//   }

//   public Entity createQuake(Point position, List<PImage> images)
//   {
//      return new Entity(EntityKind.QUAKE, QUAKE_ID, position, images,
//              QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
//   }


//   public Entity createVillage(String id, Point position, int actionPeriod,
//                                     List<PImage> images)
//   {
//      return new Entity(EntityKind.Village, id, position, images,
//              actionPeriod, 0);
//   }

   public boolean isOccupied(Point pos)
   {
      return withinBounds(pos) &&
              getOccupancyCell(pos) != null;
   }

   public Entity getOccupancyCell(Point pos)
   {
      return occupancy[pos.getY()][pos.getX()];
   }

   public void setOccupancyCell(Point pos,
                                       Entity entity)
   {
      occupancy[pos.getY()][pos.getX()] = entity;
   }

   public Optional<Entity> getOccupant(Point pos)
   {
      if (isOccupied(pos))
      {
         return Optional.of(getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   public Optional<Entity> findNearest( Point pos,
                                              Class type)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : entities)
      {
         if (entity.getClass().equals(type))
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }

   public Optional<Entity> nearestEntity(List<Entity> entities,
                                                Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = (nearest.getPosition().distanceSquared(pos));

         for (Entity other : entities)
         {
            int otherDistance = (other.getPosition().distanceSquared(pos));

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   public Set<Entity> getEntity(){
      return this.entities;
   }



//   public boolean moveToFull(Entity Skeleton, WorldModel world,
//                                    Entity target, EventScheduler scheduler)
//   {
//      if (Skeleton.position.adjacent(target.position))
//      {
//         return true;
//      }
//      else
//      {
//         Point nextPos = target.nextPositionSkeleton(Skeleton, world, target.position);
//
//         if (!Skeleton.position.equals(nextPos))
//         {
//            Optional<Entity> occupant = getOccupant(nextPos);
//            if (occupant.isPresent())
//            {
//               scheduler.unscheduleAllEvents(occupant.get());
//            }
//
//            moveEntity(Skeleton, nextPos);
//         }
//         return false;
//      }
//   }

//   public Point nextPositionSkeleton(Entity entity,
//                                        Point destPos)
//   {
//      int horiz = Integer.signum(destPos.x - entity.position.x);
//      Point newPos = new Point(entity.position.x + horiz,
//              entity.position.y);
//
//      if (horiz == 0 || isOccupied(newPos))
//      {
//         int vert = Integer.signum(destPos.y - entity.position.y);
//         newPos = new Point(entity.position.x,
//                 entity.position.y + vert);
//
//         if (vert == 0 || isOccupied(newPos))
//         {
//            newPos = entity.position;
//         }
//      }
//
//      return newPos;
//   }
//
//   public Point nextPositionCrab(Entity entity,
//                                        Point destPos)
//   {
//      int horiz = Integer.signum(destPos.x - entity.position.x);
//      Point newPos = new Point(entity.position.x + horiz,
//              entity.position.y);
//
//      Optional<Entity> occupant = getOccupant(newPos);
//
//      if (horiz == 0 ||
//              (occupant.isPresent() && !(occupant.get().kind == EntityKind.Zombie)))
//      {
//         int vert = Integer.signum(destPos.y - entity.position.y);
//         newPos = new Point(entity.position.x, entity.position.y + vert);
//         occupant = getOccupant(newPos);
//
//         if (vert == 0 ||
//                 (occupant.isPresent() && !(occupant.get().kind == EntityKind.Zombie)))
//         {
//            newPos = entity.position;
//         }
//      }
//
//      return newPos;
//   }

   public void addEntity(Entity entity)
   {
      if (withinBounds(entity.getPosition()))
      {
         setOccupancyCell(entity.getPosition(), entity);
         entities.add(entity);
      }
   }

   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (withinBounds(pos) && !pos.equals(oldPos))
      {
         setOccupancyCell(oldPos, null);
         removeEntityAt(pos);
         setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public void removeEntity(Entity entity)
   {
      removeEntityAt(entity.getPosition());
   }

   public void removeEntityAt(Point pos)
   {
      if (withinBounds(pos)
              && getOccupancyCell(pos) != null)
      {
         Entity entity = getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         entities.remove(entity);
         setOccupancyCell(pos, null);
      }
   }

//   public boolean transformNotFull(Entity entity,
//                                          EventScheduler scheduler, ImageStore imageStore)
//   {
//      if (entity.resourceCount >= entity.resourceLimit)
//      {
//         Entity Skeleton = createSkeletonFull(entity.id, entity.resourceLimit,
//                 entity.position, entity.actionPeriod, entity.animationPeriod,
//                 entity.images);
//
//         removeEntity(entity);
//         scheduler.unscheduleAllEvents(entity);
//
//         addEntity(Skeleton);
//         Skeleton.scheduleActions(scheduler, this, imageStore);
//
//         return true;
//      }
//
//      return false;
//   }
//
//   public void transformFull(Entity entity,
//                                    EventScheduler scheduler, ImageStore imageStore)
//   {
//      Entity Skeleton = createSkeletonNotFull(entity.id, entity.resourceLimit,
//              entity.position, entity.actionPeriod, entity.animationPeriod,
//              entity.images);
//
//      removeEntity(entity);
//      scheduler.unscheduleAllEvents(entity);
//
//      addEntity(Skeleton);
//      Skeleton.scheduleActions(scheduler, this, imageStore);
//   }

//   public boolean moveToNotFull(Entity Skeleton, WorldModel world,
//                                       Entity target, EventScheduler scheduler)
//   {
//      if ((Skeleton.position.adjacent(target.position)))
//      {
//         Skeleton.resourceCount += 1;
//         removeEntity(target);
//         scheduler.unscheduleAllEvents(target);
//
//         return true;
//      }
//      else
//      {
//         Point nextPos = Skeleton.nextPositionSkeleton(Skeleton, world, target.position);
//
//         if (!Skeleton.position.equals(nextPos))
//         {
//            Optional<Entity> occupant = getOccupant(nextPos);
//            if (occupant.isPresent())
//            {
//               scheduler.unscheduleAllEvents(occupant.get());
//            }
//
//            moveEntity(Skeleton, nextPos);
//         }
//         return false;
//      }
//   }
//
//   public boolean moveToCrab(Entity crab, WorldModel world,
//                                    Entity target, EventScheduler scheduler)
//   {
//      if (crab.position.adjacent(target.position))
//      {
//         removeEntity(target);
//         scheduler.unscheduleAllEvents(target);
//         return true;
//      }
//      else
//      {
//         Point nextPos = crab.nextPositionCrab(crab, world, target.position);
//
//         if (!crab.position.equals(nextPos))
//         {
//            Optional<Entity> occupant = getOccupant(nextPos);
//            if (occupant.isPresent())
//            {
//               scheduler.unscheduleAllEvents(occupant.get());
//            }
//
//            moveEntity(crab, nextPos);
//         }
//         return false;
//      }
//   }

   public Optional<Point> findOpenAround(Point pos)
   {
      for (int dy = -Zombie_REACH; dy <= Zombie_REACH; dy++)
      {
         for (int dx = -Zombie_REACH; dx <= Zombie_REACH; dx++)
         {
            Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
            if (withinBounds(newPt) &&
                    !isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }

   public void tryAddEntity(Entity entity)
   {
      if (isOccupied(entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      addEntity(entity);
   }

//   public PImage getCurrentImage(Object entity) // breakup into two one in entity and background
//   {
//      if (entity instanceof Background)
//      {
//         return ((Background)entity).images
//                 .get(((Background)entity).imageIndex);
//      }
//      else if (entity instanceof Entity)
//      {
//         return ((Entity)entity).images.get(((Entity)entity).imageIndex);
//      }
//      else
//      {
//         throw new UnsupportedOperationException(
//                 String.format("getCurrentImage not supported for %s",
//                         entity));
//      }
//   }
}
