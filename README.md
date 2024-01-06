# Koi Kapers
Koi Kapers is a 2D Java-implemented game that was inspired by the school's koi pond with the pylons and chairs being tossed into it last winter. In this game, the user plays as the white koi fish attempting to escape the maze while avoiding contact with the moving enemy (swimming raccoon).

## Game Demo
[Link to game demo](https://youtu.be/cZQ1i3oe57I)

![](https://github.com/sophiadt/koikapers/blob/main/media/phase4-video.gif)

## How to Run the Game
  To run the game, you will need to have Maven, JAR, and Oracle JDK 21 or higher installed.
  
  In your terminal, cd into the root folder of the project, which is named `koikapers`, and run the following command.
  ### `java -jar my-app/target/koi-kapers-1.0-SNAPSHOT.jar`
  ----
  Alternatively, you can cd into the target directory, `koikapers\my-app\target`, where the JAR file, `koi-kapers-1.0-SNAPSHOT`, is located and run the JAR file or the following command.
  ### ```java -jar koi-kapers-1.0-SNAPSHOT.jar```
  ----
  Another way to run the game is to open your IDE, open ```Main.java```, and click the run file button.

## How to Build the Game
  In your terminal, cd into the root folder of the project, which is named `koikapers`. Then run the following command to create the generate the artifact.
   ### `mvn jar:jar -f my-app/pom.xml`

## How to Test the Game
  In your IDE, open one of the test classes in the test directory and click the run file button to run the test cases.
  
