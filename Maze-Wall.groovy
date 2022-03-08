//Your code here
// Load first STL file from the git repo
File servoFile = ScriptingEngine.fileFromGit(
	"https://github.com/WPIRoboticsEngineering/Modular-Maze.git",
	"Maze_Wall_Post.stl");
// Load the .CSG from the disk and cache it in memory
CSG Maze_Wall_Post  = Vitamins.get(servoFile)

// code goes here


return Maze_Wall_Post