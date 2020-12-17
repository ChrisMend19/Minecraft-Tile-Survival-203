import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Creeper extends Entity {
    private PathingStrategy strategy = new AStarPathingStrategy();

    public Creeper(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this,world, imageStore),
                getActionPeriod());
    }

    public static Creeper createCreeper(String id, Point position, List<PImage> images) {
        return new Creeper(id, position, images, 0, 0);
    }

    public Point nextPositionCreeper(WorldModel world, Point destPos)
    {
        List<Point> nextPoints = strategy.computePath(getPosition(), destPos, p -> (world.withinBounds(p) && !world.isOccupied(p)),
                Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        if (nextPoints.size() == 0)
            return getPosition();
        return nextPoints.get(0);
    }

    public static void executeCreeperActivity(Entity entity, WorldModel world,
                                              ImageStore imageStore, EventScheduler scheduler) {
        Creeper creeper = (Creeper) entity;
        Optional<Entity> target = world.findNearest(creeper.getPosition(),
                Steve.class);
        if (target.isPresent() && creeper.moveToCreeper(creeper, world, target.get(), scheduler))
        {
            target.get().scheduleActions(scheduler, world, imageStore);
            creeper.scheduleActions(scheduler, world, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(creeper,
                    Activity.createActivityAction(creeper,
                            world, imageStore),
                    creeper.getActionPeriod());
        }

    }

    public boolean moveToCreeper(Creeper creeper, WorldModel world,
                                 Entity target, EventScheduler scheduler)
    {
        if (creeper.getPosition().adjacent(target.getPosition()))
        {
            world.getSteve().setHealth(world.getSteve().getHealth() - 1);
            return true;
        }
        else
        {
            Point nextPos = nextPositionCreeper(world, target.getPosition());
            System.out.println(nextPos);
            if (!creeper.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                world.moveEntity(creeper, nextPos);
            }
            return false;
        }
    }
}