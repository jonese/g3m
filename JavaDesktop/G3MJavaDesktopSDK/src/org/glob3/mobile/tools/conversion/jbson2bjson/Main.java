

package org.glob3.mobile.tools.conversion.jbson2bjson;

import java.io.File;

import org.glob3.mobile.generated.ILogger;


public class Main {
   /**
    * @param args
    */


   public static void main(final String[] args) {
      System.out.println("Batch JBSON2BJSON Parser Desktop 0.1");
      System.out.println("-----------------------------\n");

      if (args.length != 2) {
         System.out.println("Arguments must be properly specified.");
         System.exit(1);
      }

      // Inicializando
      final File firstFile = new File(args[0]);
      final File secondFile = new File(args[1]);
      try {
         JBson2BJson.instance().transform(firstFile, secondFile, true);
      }
      catch (final JBson2BJsonException e) {
         ILogger.instance().logError(e.getMessage());
      }
   }
}
