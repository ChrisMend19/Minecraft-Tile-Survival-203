import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Ghast extends Entity {
    private PathingStrategy strategy = new AStarPathingStrategy();

    public Ghast(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this,world, imageStore),
                getActionPeriod());
    }

    public static Ghast createGhast(String id, Point position, List<PImage> images) {
        return new Ghast(id, position, images, 0, 0);
    }

    public Point nextPositionGhast(WorldModel world, Point destPos)
    {
        List<Point> nextPoints = strategy.computePath(getPosition(), destPos, p -> (world.withinBounds(p) && !world.isOccupied(p)),
                Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        if (nextPoints.size() == 0)
            return getPosition();
        return nextPoints.get(0);
    }

    public static void executeGhastActivity(Entity entity, WorldModel world,
                                              ImageStore imageStore, EventScheduler scheduler) {
        Ghast ghast = (Ghast) entity;
        Optional<Entity> target = world.findNearest(ghast.getPosition(),
                Steve.class);
        if (target.isPresent() && ghast.moveToGhast(ghast, world, target.get(), scheduler))
        {
            target.get().scheduleActions(scheduler, world, imageStore);
            ghast.scheduleActions(scheduler, world, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(ghast,
                    Activity.createActivityAction(ghast,
                            world, imageStore),
                    ghast.getActionPeriod());
        }

    }

    public boolean moveToGhast(Ghast ghast, WorldModel world,
                                 Entity target, EventScheduler scheduler)
    {
        if (ghast.getPosition().adjacent(target.getPosition()))
        {
            world.getSteve().setHealth(world.getSteve().getHealth() - 1);
            return true;
        }
        else
        {
            Point nextPos = nextPositionGhast(world, target.getPosition());
            System.out.println(nextPos);
            if (!ghast.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                world.moveEntity(ghast, nextPos);
            }
            return false;
        }
    }
}