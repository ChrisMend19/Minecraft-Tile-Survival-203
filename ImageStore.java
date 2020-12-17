import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

/*
ImageStore: to ideally keep track of the images used in our virtual world
 */

final class ImageStore
{
   private Map<String, List<PImage>> images;
   private List<PImage> defaultImages;

   static final int KEYED_IMAGE_MIN = 5;
   static final int KEYED_RED_IDX = 2;
   static final int KEYED_GREEN_IDX = 3;
   static final int KEYED_BLUE_IDX = 4;
   static final int COLOR_MASK = 0xffffff;

   static final String Skeleton_KEY = "skeleton";
   static final int Skeleton_NUM_PROPERTIES = 7;
   static final int Skeleton_ID = 1;
   static final int Skeleton_COL = 2;
   static final int Skeleton_ROW = 3;
   static final int Skeleton_LIMIT = 4;
   static final int Skeleton_ACTION_PERIOD = 5;
   static final int Skeleton_ANIMATION_PERIOD = 6;

   static final String CREEPER_KEY = "creeper";
   static final int CREEPER_NUM_PROPERTIES = 4;
   private final int CREEPER_ID = 1;
   private final int CREEPER_COL = 2;
   private final int CREEPER_ROW = 3;

   static final String OBSTACLE_KEY = "obstacle";
   static final int OBSTACLE_NUM_PROPERTIES = 4;
   static final int OBSTACLE_ID = 1;
   static final int OBSTACLE_COL = 2;
   static final int OBSTACLE_ROW = 3;

   static final String DIAMOND_KEY = "diamondCollect";
   static final int DIAMOND_NUM_PROPERTIES = 4;
   static final int DIAMOND_ID = 1;
   static final int DIAMOND_COL = 2;
   static final int DIAMOND_ROW = 3;

   static final String GHAST_KEY = "ghast";
   static final int GHAST_NUM_PROPERTIES = 4;
   static final int GHAST_ID = 1;
   static final int GHAST_COL = 2;
   static final int GHAST_ROW = 3;

   static final String Zombie_KEY = "zombie";
   static final int Zombie_NUM_PROPERTIES = 5;
   static final int Zombie_ID = 1;
   static final int Zombie_COL = 2;
   static final int Zombie_ROW = 3;
   static final int Zombie_ACTION_PERIOD = 4;

   static final String NETHER_KEY = "nether";
   static final int NETHER_NUM_PROPERTIES = 4;
   static final int NETHER_ID = 1;
   static final int NETHER_COL = 2;
   static final int NETHER_ROW = 3;
   static final int NETHER_ANIMATION_PERIOD = 70;

   static final String Village_KEY = "village";
   static final int Village_NUM_PROPERTIES = 5;
   static final int Village_ID = 1;
   static final int Village_COL = 2;
   static final int Village_ROW = 3;
   static final int Village_ACTION_PERIOD = 4;

   static final String BGND_KEY = "background";
   static final int BGND_NUM_PROPERTIES = 4;
   static final int BGND_ID = 1;
   static final int BGND_COL = 2;
   static final int BGND_ROW = 3;

   static final String STEVE_KEY = "steve";
   static final int STEVE_NUM_PROPERTIES = 4;
   private final int STEVE_ID = 1;
   private final int STEVE_COL = 2;
   private final int STEVE_ROW = 3;

   public ImageStore(PImage defaultImage)
   {
      this.images = new HashMap<>();
      defaultImages = new LinkedList<>();
      defaultImages.add(defaultImage);
   }

   public boolean parseVillage(String[] properties, WorldModel world)
   {
      if (properties.length == Village_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[Village_COL]),
                 Integer.parseInt(properties[Village_ROW]));
         Village village = Village.createVillage(properties[Village_ID],
                 pt,
                 Integer.parseInt(properties[Village_ACTION_PERIOD]),
                 getImageList(Village_KEY));
         world.tryAddEntity(village);
      }

      return properties.length == Village_NUM_PROPERTIES;
   }

   public boolean parseNether(String[] properties, WorldModel world)
   {
      if (properties.length == NETHER_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[NETHER_COL]),
                 Integer.parseInt(properties[NETHER_ROW]));
         Nether nether = Nether.createNether(properties[NETHER_ID],
                 pt, getImageList(NETHER_KEY));
         world.tryAddEntity(nether);
      }

      return properties.length == NETHER_NUM_PROPERTIES;
   }

   public boolean parseZombie(String[] properties, WorldModel world)
   {
      if (properties.length == Zombie_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[Zombie_COL]),
                 Integer.parseInt(properties[Zombie_ROW]));
         Zombie zombie = Zombie.createZombie(properties[Zombie_ID],
                 pt, Integer.parseInt(properties[Zombie_ACTION_PERIOD]),
                 getImageList(Zombie_KEY));
         world.tryAddEntity(zombie);
      }

      return properties.length == Zombie_NUM_PROPERTIES;
   }

   public boolean parseObstacle(String[] properties, WorldModel world)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Obstacle obstacle = Obstacle.createObstacle(properties[OBSTACLE_ID],
                 pt, getImageList(OBSTACLE_KEY));
         world.tryAddEntity(obstacle);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   public boolean parseDiamond(String[] properties, WorldModel world)
   {
      if (properties.length == DIAMOND_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[DIAMOND_COL]),
                 Integer.parseInt(properties[DIAMOND_ROW]));
         Diamond diamond = Diamond.createDiamond(properties[DIAMOND_ID],
                 pt, getImageList(DIAMOND_KEY));
         world.tryAddEntity(diamond);
      }

      return properties.length == DIAMOND_NUM_PROPERTIES;
   }

   public boolean parseSteve(String[] properties, WorldModel world)
   {

      if (properties.length == STEVE_NUM_PROPERTIES) {

         Point pt = new Point(Integer.parseInt(properties[STEVE_COL]),
                                  Integer.parseInt(properties[STEVE_ROW]));
         Steve steve = Steve.createSteve(properties[STEVE_ID],
                 pt, getImageList(STEVE_KEY));
         world.tryAddEntity(steve);
         world.setSteve(steve);
      }


         //if (properties.length == OBSTACLE_NUM_PROPERTIES)
      //      {
      //         Point pt = new Point(
      //                 Integer.parseInt(properties[LION_COL]),
      //                 Integer.parseInt(properties[LION_ROW]));
      //         Lion entity = new Lion(properties[LION_ID],
      //                 pt, imageStore.getImageList(LION_KEY),0);
      //         world.tryAddEntity(entity);
      //         world.setLion(entity);
      //      }
      //      return properties.length == LION_NUM_PROPERTIES;
      //

      return properties.length == STEVE_NUM_PROPERTIES;
   }

   public boolean parseGhast(String[] properties, WorldModel world)
   {
      if (properties.length == GHAST_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[GHAST_COL]),
                 Integer.parseInt(properties[GHAST_ROW]));
         Ghast ghast = Ghast.createGhast(properties[GHAST_ID],
                 pt, getImageList(GHAST_KEY));
         world.tryAddEntity(ghast);
      }

      return properties.length == GHAST_NUM_PROPERTIES;
   }

   public boolean parseSkeleton(String[] properties, WorldModel world)
   {
      if (properties.length == Skeleton_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[Skeleton_COL]),
                 Integer.parseInt(properties[Skeleton_ROW]));
         Skeleton skeleton = Skeleton.createSkeletonNotFull(properties[Skeleton_ID],
                 Integer.parseInt(properties[Skeleton_LIMIT]),
                 pt,
                 Integer.parseInt(properties[Skeleton_ACTION_PERIOD]),
                 Integer.parseInt(properties[Skeleton_ANIMATION_PERIOD]),
                 getImageList(Skeleton_KEY));
         world.tryAddEntity(skeleton);
      }

      return properties.length == Skeleton_NUM_PROPERTIES;
   }

   public boolean parseBackground(String[] properties,
                                  WorldModel world)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         world.setBackground(pt,
                 new Background(id, getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   public boolean parseCreeper(String[] properties, WorldModel world)
   {
      //System.out.println("parseCreeper");
      if (properties.length == CREEPER_NUM_PROPERTIES) {

         Point pt = new Point(Integer.parseInt(properties[CREEPER_COL]),
                 Integer.parseInt(properties[CREEPER_ROW]));
         Creeper creeper = Creeper.createCreeper(properties[CREEPER_ID],
                 pt, getImageList(CREEPER_KEY));
         world.tryAddEntity(creeper);
      }
      return properties.length == CREEPER_NUM_PROPERTIES;
   }


   public List<PImage> getImageList(String key)
   {
      return images.getOrDefault(key, defaultImages);
   }

   public void loadImages(Scanner in,
                                 PApplet screen)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            processImageLine(images, in.nextLine(), screen);
         }
         catch (NumberFormatException e)
         {
            System.out.println(String.format("Image format error on line %d",
                    lineNumber));
         }
         lineNumber++;
      }
   }

   public static void processImageLine(Map<String, List<PImage>> images,
                                       String line, PApplet screen)
   {
      String[] attrs = line.split("\\s");
      if (attrs.length >= 2)
      {
         String key = attrs[0];
         PImage img = screen.loadImage(attrs[1]);
         if (img != null && img.width != -1)
         {
            List<PImage> imgs = getImages(images, key);
            imgs.add(img);

            if (attrs.length >= KEYED_IMAGE_MIN)
            {
               int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
               int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
               int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
               setAlpha(img, screen.color(r, g, b), 0);
            }
         }
      }
   }

   public static List<PImage> getImages(Map<String, List<PImage>> images,
                                        String key)
   {
      List<PImage> imgs = images.get(key);
      if (imgs == null)
      {
         imgs = new LinkedList<>();
         images.put(key, imgs);
      }
      return imgs;
   }

   public static void setAlpha(PImage img, int maskColor, int alpha)
   {
      int alphaValue = alpha << 24;
      int nonAlpha = maskColor & COLOR_MASK;
      img.format = PApplet.ARGB;
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
         {
            img.pixels[i] = alphaValue | nonAlpha;
         }
      }
      img.updatePixels();
   }
}
