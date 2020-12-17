import java.util.List;
import java.util.Optional;

import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */


abstract public class Entity
{
   //private EntityKind kind;
   protected String id;
   protected Point position;
   protected List<PImage> images;
   protected int imageIndex;
   protected int actionPeriod;
   protected int animationPeriod;

   final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

   final int Nether_ANIMATION_REPEAT_COUNT = 7;

   public Entity(String id, Point position,
                 List<PImage> images,
                 int actionPeriod, int animationPeriod)
   {
      //this.kind = kind;
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.actionPeriod = actionPeriod;
      this.animationPeriod = animationPeriod;
   }

//   public EntityKind getKind() {
//      return kind;
//   }

   public String getId() {
      return id;
   }

   public Point getPosition() {
      return position;
   }

   public void setPosition(Point p){
      this.position = p;
   }

   public List<PImage> getImages() {
      return images;
   }

   public int getImageIndex() {
      return imageIndex;
   }

   public int getActionPeriod() {
      return actionPeriod;
   }

//   public int getAnimationPeriod()
//   {
//      switch (kind)
//      {
//         case Skeleton_FULL:
//         case Skeleton_NOT_FULL:
//         case CRAB:
//         case QUAKE:
//         case Nether:
//            return animationPeriod;
//         default:
//            throw new UnsupportedOperationException(
//                    String.format("getAnimationPeriod not supported for %s",
//                            kind));
//      }
//   }

   public int getAnimationPeriod()
   {
      return animationPeriod;
   }

   public void nextImage()
   {
      imageIndex = (imageIndex + 1) % images.size();
   }

   public abstract void scheduleActions(EventScheduler scheduler,
                                      WorldModel world, ImageStore imageStore);

//   public void scheduleActions(EventScheduler scheduler,
//                                      WorldModel world, ImageStore imageStore) // abstract method
//   {
//      switch (kind)
//      {
//         case Skeleton_FULL:
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    actionPeriod);
//            scheduler.scheduleEvent(this, createAnimationAction( 0),
//                    getAnimationPeriod());
//            break;
//
//         case Skeleton_NOT_FULL:
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    this.actionPeriod);
//            scheduler.scheduleEvent(this,
//                    createAnimationAction(0), getAnimationPeriod());
//            break;
//
//         case Zombie:
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    actionPeriod);
//            break;
//
//         case CRAB:
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    this.actionPeriod);
//            scheduler.scheduleEvent(this,
//                    createAnimationAction(0), this.getAnimationPeriod());
//            break;
//
//         case QUAKE:
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    actionPeriod);
//            scheduler.scheduleEvent(this,
//                    createAnimationAction(QUAKE_ANIMATION_REPEAT_COUNT),
//                    getAnimationPeriod());
//            break;
//
//         case Village:
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    actionPeriod);
//            break;
//         case Nether:
//            scheduler.scheduleEvent(this,
//                    createAnimationAction(Nether_ANIMATION_REPEAT_COUNT),
//                    getAnimationPeriod());
//            break;
//
//         default:
//      }
//   }

//   public Action createAnimationAction(int repeatCount)
//   {
//      return new Action(ActionKind.ANIMATION, this, null, null, repeatCount);
//   }

//   public Action createActivityAction(WorldModel world,
//                                             ImageStore imageStore)
//   {
//      return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
//   }

   public PImage getCurrentImage(Object entity) // breakup into two one in entity and background
   {
      if (entity instanceof Entity)
      {
         return ((Entity)entity).images.get(((Entity)entity).imageIndex);
      }
      else
      {
         throw new UnsupportedOperationException(
                 String.format("getCurrentImage not supported for %s",
                         entity));
      }
   }

//   public Point nextPositionCrab(Entity entity, WorldModel world,
//                                 Point destPos)
//   {
//      int horiz = Integer.signum(destPos.getX() - entity.position.getX());
//      Point newPos = new Point(entity.position.getX() + horiz,
//              entity.position.getY());
//
//      Optional<Entity> occupant = world.getOccupant(newPos);
//
//      if (horiz == 0 ||
//              (occupant.isPresent() && !(occupant.get().kind == EntityKind.Zombie)))
//      {
//         int vert = Integer.signum(destPos.getY() - entity.position.getY());
//         newPos = new Point(entity.position.getY(), entity.position.getY() + vert);
//         occupant = world.getOccupant(newPos);
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

//   public boolean moveToCrab(Entity crab, WorldModel world,
//                             Entity target, EventScheduler scheduler)
//   {
//      if (crab.position.adjacent(target.position))
//      {
//         world.removeEntity(target);
//         scheduler.unscheduleAllEvents(target);
//         return true;
//      }
//      else
//      {
//         Point nextPos = crab.nextPositionCrab(crab, world, target.position);
//
//         if (!crab.position.equals(nextPos))
//         {
//            Optional<Entity> occupant = world.getOccupant(nextPos);
//            if (occupant.isPresent())
//            {
//               scheduler.unscheduleAllEvents(occupant.get());
//            }
//
//            world.moveEntity(crab, nextPos);
//         }
//         return false;
//      }
//   }




}
