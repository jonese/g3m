package org.glob3.mobile.generated; 
public enum GPUUniformKey
{
  UNRECOGNIZED_UNIFORM(-1),
  FLAT_COLOR(0),
  MODELVIEW(1),
  TEXTURE_EXTENT(2),
  VIEWPORT_EXTENT(3),
  TRANSLATION_TEXTURE_COORDS(4),
  SCALE_TEXTURE_COORDS(5),
  POINT_SIZE(6),
  AMBIENT_LIGHT(7),
  LIGHT_DIRECTION(8),
  LIGHT_COLOR(9),
  PROJECTION(10),
  CAMERA_MODEL(11),
  MODEL(12);

   private int intValue;
   private static java.util.HashMap<Integer, GPUUniformKey> mappings;
   private static java.util.HashMap<Integer, GPUUniformKey> getMappings()
   {
      if (mappings == null)
      {
         synchronized (GPUUniformKey.class)
         {
            if (mappings == null)
            {
               mappings = new java.util.HashMap<Integer, GPUUniformKey>();
            }
         }
      }
      return mappings;
   }

   private GPUUniformKey(int value)
   {
      intValue = value;
      GPUUniformKey.getMappings().put(value, this);
   }

   public int getValue()
   {
      return intValue;
   }

   public static GPUUniformKey forValue(int value)
   {
      return getMappings().get(value);
   }
}