/*
Action: ideally what our various entities might do in our virutal world
 */

import java.util.Optional;
import java.util.Random;

abstract class Action
{
   //private ActionKind kind;
   private Entity entity;
   private WorldModel world;

//   public void executeActivityAction(EventScheduler scheduler) //goes to act
//   {
//      switch (getEntity().getKind())
//      {
//         case Skeleton_FULL:
//            if(this.getEntity() instanceof Skeleton && ((Skeleton)this.getEntity()).getisFull())
//            Skeleton.executeSkeletonFullActivity((Skeleton) getEntity(), getWorld(),
//                    getImageStore(), scheduler);
//            break;
//
//         case Skeleton_NOT_FULL:
//            if(this.getEntity() instanceof Skeleton && !((Skeleton)this.getEntity()).getisFull())
//            Skeleton.executeSkeletonNotFullActivity((Skeleton) getEntity(), getWorld(),
//                    getImageStore(), scheduler);
//            break;
//
//         case Zombie:
//            Zombie.executeZombieActivity(getEntity(), getWorld(), getImageStore(),
//                    scheduler);
//            break;
//
//         case CRAB:
//            Crab.executeCrabActivity(getEntity(), getWorld(),
//                    getImageStore(), scheduler);
//            break;
//
//         case QUAKE:
//            Quake.executeQuakeActivity(getEntity(), getWorld(), getImageStore(),
//                    scheduler);
//            break;
//
//         case Village:
//            Village.executeVillageActivity(getEntity(), getWorld(), getImageStore(),
//                    scheduler);
//            break;
//
//         case Nether:
//            Nether.executeNetherActivity(getEntity(), getWorld(), getImageStore(),
//                    scheduler);
//            break;
//
//         default:
//            throw new UnsupportedOperationException(
//                    String.format("executeActivityAction not supported for %s",
//                            getEntity().getKind()));
//      }
//   }

//   public void executeActivityAction(EventScheduler scheduler) //goes to act
//   {
//      switch (getEntity().getKind())
//      {
//         case Skeleton_FULL:
//            if(this.getEntity() instanceof Skeleton && ((Skeleton)this.getEntity()).getisFull())
//               Skeleton.executeSkeletonFullActivity((Skeleton) getEntity(), getWorld(),
//                       getImageStore(), scheduler);
//            break;
//
//         case Skeleton_NOT_FULL:
//            if(this.getEntity() instanceof Skeleton && !((Skeleton)this.getEntity()).getisFull())
//               Skeleton.executeSkeletonNotFullActivity((Skeleton) getEntity(), getWorld(),
//                       getImageStore(), scheduler);
//            break;
//
//         case Zombie:
//            Zombie.executeZombieActivity(getEntity(), getWorld(), getImageStore(),
//                    scheduler);
//            break;
//
//         case CRAB:
//            Crab.executeCrabActivity(getEntity(), getWorld(),
//                    getImageStore(), scheduler);
//            break;
//
//         case QUAKE:
//            Quake.executeQuakeActivity(getEntity(), getWorld(), getImageStore(),
//                    scheduler);
//            break;
//
//         case Village:
//            Village.executeVillageActivity(getEntity(), getWorld(), getImageStore(),
//                    scheduler);
//            break;
//
//         case Nether:
//            Nether.executeNetherActivity(getEntity(), getWorld(), getImageStore(),
//                    scheduler);
//            break;
//
//         default:
//            throw new UnsupportedOperationException(
//                    String.format("executeActivityAction not supported for %s",
//                            getEntity().getKind()));
//      }
//   }

   public ImageStore getImageStore() {
      return imageStore;
   }

   private ImageStore imageStore;
//   private int repeatCount;

//   static final String CRAB_ID_SUFFIX = " -- crab";
//   static final int CRAB_PERIOD_SCALE = 4;
//   static final int CRAB_ANIMATION_MIN = 50;
//   static final int CRAB_ANIMATION_MAX = 150;


//   static final String Zombie_ID_PREFIX = "Zombie -- ";
//   static final int Zombie_CORRUPT_MIN = 20000;
//   static final int Zombie_CORRUPT_MAX = 30000;

   static final Random rand = new Random();


//   static final String Zombie_KEY = "Zombie";
//   static final String Nether_KEY = "Nether";
//   static final String Village_KEY = "seaGrass";
//   static final String CRAB_KEY = "crab";
//   static final String QUAKE_KEY = "quake";
//   static final String BGND_KEY = "background";

   public Action(Entity entity, WorldModel world,
                 ImageStore imageStore)
   {
      //this.kind = kind;
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
      //this.repeatCount = repeatCount;
   }

//   public ActionKind getKind() {
//      return kind;
//   }

   public Entity getEntity() {
      return entity;
   }

   public WorldModel getWorld() {
      return world;
   }

//   public int getRepeatCount() {
//      return repeatCount;
//   }


//   public void executeAction(Action action, EventScheduler scheduler)
//   {
//      switch (kind)
//      {
//         case ACTIVITY:
//            action.executeActivityAction(scheduler);
//            break;
//
//         case ANIMATION:
//            executeAnimationAction(scheduler);
//            break;
//      }
//   }

   public abstract void executeAction(Action action, EventScheduler scheduler);


//   public void executeAnimationAction(
//                                             EventScheduler scheduler) //animation
//   {
//      this.entity.nextImage();
//
//      if (this.repeatCount != 1)
//      {
//         scheduler.scheduleEvent(this.entity, createAnimationAction(
//                         Math.max(this.repeatCount - 1, 0)),
//                 this.entity.getAnimationPeriod());
//      }
//   }

//   public void executeActivityAction(
//                                            EventScheduler scheduler)
//   {
//      switch (this.entity.kind)
//      {
//         case Skeleton_FULL:
//            executeSkeletonFullActivity(entity, world,
//                    imageStore, scheduler);
//            break;
//
//         case Skeleton_NOT_FULL:
//            executeSkeletonNotFullActivity(entity, world,
//                    imageStore, scheduler);
//            break;
//
//         case Zombie:
//            executeZombieActivity(entity, world, imageStore,
//                    scheduler);
//            break;
//
//         case CRAB:
//            executeCrabActivity(entity, world,
//                    imageStore, scheduler);
//            break;
//
//         case QUAKE:
//            executeQuakeActivity(entity, world, imageStore,
//                    scheduler);
//            break;
//
//         case Village:
//            executeVillageActivity(entity, world, imageStore,
//                    scheduler);
//            break;
//
//         case Nether:
//            executeNetherActivity(entity, world, imageStore,
//                    scheduler);
//            break;
//
//         default:
//            throw new UnsupportedOperationException(
//                    String.format("executeActivityAction not supported for %s",
//                            this.entity.kind));
//      }
//   }

//   public void executeSkeletonFullActivity(Skeleton Skeleton, WorldModel world,
//                                              ImageStore imageStore, EventScheduler scheduler) //put in child
//   {
//      Optional<Entity> fullTarget = world.findNearest(entity.getPosition(),
//              EntityKind.Nether);
//
//      if (fullTarget.isPresent() &&
//              Skeleton.moveToFull(Skeleton, world, fullTarget.get(), scheduler))
//      {
//         //at Nether trigger animation
//         fullTarget.get().scheduleActions(scheduler, world, imageStore);
//
//         //transform to unfull
//         Skeleton.transformFull(world, scheduler, imageStore);
//      }
//      else
//      {
//         scheduler.scheduleEvent(entity,
//                 entity.createActivityAction(
//                         world, imageStore),
//                 entity.getActionPeriod());
//      }
//   }
//
//   public void executeSkeletonNotFullActivity(Skeleton Skeleton,
//                                                 WorldModel world, ImageStore imageStore, EventScheduler scheduler)
//   {
//      Optional<Entity> notFullTarget = world.findNearest(entity.getPosition(),
//              EntityKind.Zombie);
//
//      if (!notFullTarget.isPresent() ||
//              !Skeleton.moveToNotFull(Skeleton, world, notFullTarget.get(), scheduler) ||
//              !Skeleton.transformNotFull(world, scheduler, imageStore))
//      {
//         scheduler.scheduleEvent(entity,
//                 entity.createActivityAction(world, imageStore),
//                 entity.getActionPeriod());
//      }
//   }

//   public static void executeZombieActivity(Entity entity, WorldModel world,
//                                          ImageStore imageStore, EventScheduler scheduler)
//   {
//      Point pos = entity.getPosition();  // store current position before removing
//
//      world.removeEntity(entity);
//      scheduler.unscheduleAllEvents(entity);
//
//      Crab crab = Crab.createCrab(entity.getId() + CRAB_ID_SUFFIX,
//              pos, entity.getActionPeriod() / CRAB_PERIOD_SCALE,
//              CRAB_ANIMATION_MIN +
//                      rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
//              imageStore.getImageList(CRAB_KEY));
//
//      world.addEntity(crab);
//      crab.scheduleActions(scheduler, world, imageStore);
//   }

//   public static void executeCrabActivity(Entity entity, WorldModel world,
//                                          ImageStore imageStore, EventScheduler scheduler)
//   {
//      Optional<Entity> crabTarget = world.findNearest(
//              entity.getPosition(), EntityKind.Village);
//      long nextPeriod = entity.getActionPeriod();
//
//      if (crabTarget.isPresent())
//      {
//         Point tgtPos = crabTarget.get().getPosition();
//
//         if (entity.moveToCrab(entity, world ,crabTarget.get(), scheduler))
//         {
//            Quake quake = Quake.createQuake(tgtPos,
//                    imageStore.getImageList(QUAKE_KEY));
//
//            world.addEntity(quake);
//            nextPeriod += entity.getActionPeriod();
//            quake.scheduleActions(scheduler, world, imageStore);
//         }
//      }
//
//      scheduler.scheduleEvent(entity,
//              entity.createActivityAction(world, imageStore),
//              nextPeriod);
//   }

//   public static void executeQuakeActivity(Entity entity, WorldModel world,
//                                           ImageStore imageStore, EventScheduler scheduler)
//   {
//      scheduler.unscheduleAllEvents(entity);
//      world.removeEntity(entity);
//   }

//   public static void executeNetherActivity(Entity entity, WorldModel world,
//                                              ImageStore imageStore, EventScheduler scheduler)
//   {
//      scheduler.unscheduleAllEvents(entity);
//      world.removeEntity(entity);
//   }


//   public static void executeVillageActivity(Entity entity, WorldModel world,
//                                            ImageStore imageStore, EventScheduler scheduler)
//   {
//      Optional<Point> openPt = world.findOpenAround(entity.getPosition());
//
//      if (openPt.isPresent())
//      {
//         Zombie Zombie = Zombie.createZombie(Zombie_ID_PREFIX + entity.getId(), //changed Entity to Zombie
//                 openPt.get(), Zombie_CORRUPT_MIN +
//                         rand.nextInt(Zombie_CORRUPT_MAX - Zombie_CORRUPT_MIN),
//                 imageStore.getImageList(Zombie_KEY)); //used in functions aswell
//         world.addEntity(Zombie);
//         Zombie.scheduleActions(scheduler, world, imageStore);
//      }
//
//      scheduler.scheduleEvent(entity,
//              entity.createActivityAction(world, imageStore),
//              entity.getActionPeriod());
//   }

}
