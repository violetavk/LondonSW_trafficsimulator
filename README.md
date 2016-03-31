# LondonSW Traffic Simulator
Group Project for KCL

This traffic simulator is a group project aimed to build a traffic simulation software based on a Cellular Automaton Model

##Pre-requisites for running the project:
- You must have at least Java8u40 running on your machine

##Building and running the project:
### GitHub Setup
1. Open IntelliJ.
2. File -> New -> Project from Version Control -> GitHub
3. Paste this link into the Git Repository URL: `git clone https://github.com/violetavk/LondonSW_trafficsimulator.git` and hit Clone
4. Go into project settings "Project Structure" (File -> Project Structure)
5. Look at the Project SDK in the Project tab in Project Settings. Make sure it is at least Java 1.8 update 40.
6. Run the main method in `src/londonsw/SystemApp.java`. Done!

### IntelliJ Setup
1. Download a tar.gz file from [our release page](https://github.com/violetavk/LondonSW_trafficsimulator/releases) and open IntelliJ.
2. File -> New -> Project from Existing Sources...
3. Navigate to the root directory of this project
4. Choose "Create project from existing sources" option and hit Next
5. Keep "Project name" and "Project location" as is, hit Next
6. If it asks to overwrite .idea files, it OK, do overwrite
7. For source files, keep `/team_londonsw/src` ticked, untick `/Demo/src`
8. For the libraries, untick "jung-2_0_1" and "jung2-2_0_1-sources" (they are for the Demo src), keep "lib" ticked. "lib" contains libraries such as rxjava, rxjavafx, etc.
9. In the review screen, you will see "team_londonsw" in the modules, and "lib" in the Module dependencies. Hit Next.
10. Select a project SDK. Make sure it is at least Java 1.8 update 40.
11. No frameworks detected. Hit Finish.
12. Go into project settings "Project Structure" (File -> Project Structure)
13. Click the Modules tab on the left under Project Structure.
14. Click the Sources tab on the right.
15. Locate the `resources` directory in the root of the project structure. Click on it and mark as "Resources".
16. Locate the `test` directory in `src/londonsw/`. Click on it and mark it as "Tests".
17. Click OK.
18. Run the main method in `src/londonsw/SystemApp.java`.  Done!

### Jar File Execution
1. Download the latest release from [our release page](https://github.com/violetavk/LondonSW_trafficsimulator/releases)
2. Double-click on the jar file or run the command `java -jar name-of-release-`
