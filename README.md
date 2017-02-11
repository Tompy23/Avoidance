# Avoidance

When the application starts, the "ships" will be flying around at random.  Between each "epoch" there is a learning phase where the best "ships" gvie their knowledge to the other ships.  Some genetic tweaking happens, and the next epoch starts with the same parameters (same starting positions and velocities).

Hit the "f" key, and a graphical representation happens (and the epochs go much faster).  The green line indicates the number of collisions for the "best ship" and the red the "worst ship".  The blue line is an average.  Hit "s" to go back and watch the mayhem.  After about 150 epochs, the ships being to slow down and you can see some improvement.  The ships change color as they gain collisions, starting at green and move to red then black-ish.  A "Golden ship" is one that has attained a certain perfection (not really, but don't tell that to its face).  Once all the ships are gold, no more learning takes place.

Dependent Jars: (see pom.xml)

Avoidance.jar  commons-logging.jar  log4j.jar  spring-aop.jar  spring-beans.jar  spring-context.jar  spring-core.jar  spring-expression.jar

java com.tuc.ai.Main Avoid_ship_only.properties log.dir=<location where you want log dir>

