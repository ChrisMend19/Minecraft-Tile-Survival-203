import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Skeleton extends Entity{

    private boolean isFull;
    private int resourceLimit;
    private int resourceCount;
    private PathingStrategy strategy = new AStarPathingStrategy(); // req: world event, ghast,
// extra: end/win screen, health bar, obstacle reformation, random generated stuff

    public Skeleton(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, boolean isFull) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.isFull = isFull;
    }

    public boolean getisFull() {
        return this.isFull;
    }

    public int getResourceLimit() {
       return resourceLimit;
    }

    public int getResourceCount() {
       return resourceCount;
    }

    public static Skeleton createSkeletonNotFull(String id, int resourceLimit,
                                             Point position, int actionPeriod, int animationPeriod,
                                             List<PImage> images)
    {
        return new Skeleton(id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod, false);
    }

    public static Skeleton createSkeletonFull(String id, int resourceLimit,
                                          Point position, int actionPeriod, int animationPeriod,
                                          List<PImage> images)
    {
        return new Skeleton(id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod, true);
    }

    public boolean transformNotFull(WorldModel world,
                                     EventScheduler scheduler, ImageStore imageStore)
    {
       if (resourceCount >= resourceLimit)
       {
          Skeleton skeleton = createSkeletonFull(getId(), resourceLimit,
                  getPosition(), getActionPeriod(), getAnimationPeriod(),
                  getImages());

          world.removeEntity(skeleton);
          scheduler.unscheduleAllEvents(skeleton);

          world.addEntity(skeleton);
          skeleton.scheduleActions(scheduler, world, imageStore);

          return true;
       }

       return false;
    }

    public void transformFull(WorldModel world,
                               EventScheduler scheduler, ImageStore imageStore)
    {
       Skeleton skeleton = createSkeletonNotFull(getId(), resourceLimit,
               getPosition(), getActionPeriod(), getAnimationPeriod(),
               getImages());

       world.removeEntity(this);
       scheduler.unscheduleAllEvents(this);

       world.addEntity(skeleton);
       skeleton.scheduleActions(scheduler, world, imageStore);
    }

    public Point nextPositionSkeleton(WorldModel world,
                                   Point destPos)
    {
//       int horiz = Integer.signum(destPos.getX() - getPosition().getX());
//       Point newPos = new Point(getPosition().getX() + horiz,
//               getPosition().getY());
//
//       if (horiz == 0 || world.isOccupied(newPos))
//       {
//          int vert = Integer.signum(destPos.getY() - getPosition().getY());
//          newPos = new Point(getPosition().getX(),
//                  getPosition().getY() + vert);
//
//          if (vert == 0 || world.isOccupied(newPos))
//          {
//             newPos = getPosition();
//          }
//       }
        List<Point> nextPoints = strategy.computePath(getPosition(), destPos, p -> (world.withinBounds(p) && !world.isOccupied(p)),
        Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        if (nextPoints.size() == 0)
            return getPosition();
        return nextPoints.get(0);

        //return newPos;


    }

    public boolean moveToNotFull(Skeleton skeleton, WorldModel world,
                                 Entity target, EventScheduler scheduler)
    {
       if ((skeleton.getPosition().adjacent(target.getPosition())))
       {
          resourceCount += 1;
          world.removeEntity(target);
          scheduler.unscheduleAllEvents(target);

          return true;
       }
       else
       {
          Point nextPos = nextPositionSkeleton(world, target.getPosition());

          if (!skeleton.getPosition().equals(nextPos))
          {
             Optional<Entity> occupant = world.getOccupant(nextPos);
             if (occupant.isPresent())
             {
                scheduler.unscheduleAllEvents(occupant.get());
             }

             world.moveEntity(skeleton, nextPos);
          }
          return false;
       }
    }

    public boolean moveToFull(Skeleton skeleton, WorldModel world,
                              Entity target, EventScheduler scheduler)
    {
       if (skeleton.getPosition().adjacent(target.getPosition()))
       {
          return true;
       }
       else
       {
          Point nextPos = nextPositionSkeleton(world, target.getPosition());

          if (!skeleton.getPosition().equals(nextPos))
          {
             Optional<Entity> occupant = world.getOccupant(nextPos);
             if (occupant.isPresent())
             {
                scheduler.unscheduleAllEvents(occupant.get());
             }

             world.moveEntity(skeleton, nextPos);
          }
          if(skeleton.getPosition().adjacent(world.getSteve().position) && (world.getSteve().getHealth() > 0)){
              if(VirtualWorld.netherBool){
                  if(world.getSteve().getHealth() - 2 > 0)
                  world.getSteve().setHealth(world.getSteve().getHealth() - 2);
                  else{
                      world.getSteve().setHealth(world.getSteve().getHealth() - 1);
                  }
              }
              else
              world.getSteve().setHealth(world.getSteve().getHealth() - 1);
          }
          return false;
       }
    }

    public static void executeSkeletonFullActivity(Skeleton skeleton, WorldModel world,
                                               ImageStore imageStore, EventScheduler scheduler) //put in child
    {
        Optional<Entity> fullTarget = world.findNearest(skeleton.getPosition(),
                Steve.class);

        if (fullTarget.isPresent() &&
                skeleton.moveToFull(skeleton, world, fullTarget.get(), scheduler))
        {
            //at Nether trigger animation
            fullTarget.get().scheduleActions(scheduler, world, imageStore);

            //transform to unfull
            skeleton.transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(skeleton,
                    Activity.createActivityAction(skeleton,
                            world, imageStore),
                    skeleton.getActionPeriod());
        }
    }

    public static void executeSkeletonNotFullActivity(Skeleton skeleton,
                                                  WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(skeleton.getPosition(),
                Zombie.class);

        if (!notFullTarget.isPresent() ||
                !skeleton.moveToNotFull(skeleton, world, notFullTarget.get(), scheduler) ||
                !skeleton.transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(skeleton,
                    Activity.createActivityAction(skeleton, world, imageStore),
                    skeleton.getActionPeriod());
        }
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        if(isFull) {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    getActionPeriod());
            scheduler.scheduleEvent(this,  Animation.createAnimationAction( this,0),
                    getAnimationPeriod());
        }
        if(!isFull) {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    getActionPeriod());
            scheduler.scheduleEvent(this,
                    Animation.createAnimationAction(this,0), getAnimationPeriod());
        }
    }
}
