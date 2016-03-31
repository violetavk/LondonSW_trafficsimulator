############## IntelliJ Setup #################
1. Open IntelliJ.
2. File -> New -> Project from Existing Sources...
3. Navigate to the root directory of this project (the parent of this readme file "team_londonsw")
4. Choose "Create project from existing sources" option and hit Next
5. Keep "Project name" and "Project location" as is, hit Next
6. If it asks to overwrite .idea files, it OK, do overwrite
7. For source files, keep /team_londonsw/src ticked, untick /Demo/src
8. For the libraries, untick "jung-2_0_1" and "jung2-2_0_1-sources" (they are for the Demo src), keep "lib" ticked. "lib" contains libraries such as rxjava, rxjavafx, etc.
9. In the review screen, you will see "team_londonsw" in the modules, and "lib" in the Module dependencies. Hit Next.
10. Select a project SDK. Make sure it is at least Java 1.8 update 40.
11. No frameworks detected. Hit Finish.
12. Run the main method in src/londonsw/SystemApp.java.  Done!
##############################################

############## GitHub Setup ##################
1. Open IntelliJ.
2. File -> New -> Project from Version Control -> GitHub
3. Paste this link into the Git Repository URL: https://github.com/violetavk/LondonSW_trafficsimulator.git and hit Clone
4. Go into project settings "Project Structure" (File -> Project Structure)
5. Look at the Project SDK in the Project tab in Project Settings. Make sure it is at least Java 1.8 update 40.
6. Run the main method in src/londonsw/SystemApp.java.  Done!
##############################################

############## Jar Execution #################
1. Download a jar file from our releases page on https://github.com/violetavk/LondonSW_trafficsimulator/releases.
2. Double click on the downloaded Jar file -OR- run this command from the command line:
java -jar "name of jar file"
##############################################
