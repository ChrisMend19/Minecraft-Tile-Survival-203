import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Village extends Entity {

    public Village(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                getActionPeriod());
    }

    public static Village createVillage(String id, Point position, int actionPeriod,
                                       List<PImage> images)
    {
        return new Village(id, position, images,
                actionPeriod, 0);
    }

    public static void executeVillageActivity(Entity entity, WorldModel world,
                                             ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(entity.getPosition());

        if (openPt.isPresent())
        {
            Zombie zombie = Zombie.createZombie(Zombie.Zombie_ID_PREFIX + entity.getId(), //changed Entity to Zombie
                    openPt.get(), Zombie.Zombie_CORRUPT_MIN +
                            Action.rand.nextInt(Zombie.Zombie_CORRUPT_MAX - Zombie.Zombie_CORRUPT_MIN),
                    imageStore.getImageList(Zombie.Zombie_KEY)); //used in functions aswell
            world.addEntity(zombie);
            zombie.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(entity,
                Activity.createActivityAction(entity, world, imageStore),
                entity.getActionPeriod());
    }
}
