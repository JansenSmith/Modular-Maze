import com.neuronrobotics.bowlerstudio.scripting.ScriptingEngine
import com.neuronrobotics.bowlerstudio.vitamins.Vitamins

import eu.mihosoft.vrl.v3d.CSG

//Your code here
// Load first STL file from the git repo
File servoFile = ScriptingEngine.fileFromGit(
	"https://github.com/WPIRoboticsEngineering/Modular-Maze.git",
	"Maze_Wall_Post.stl");
// Load the .CSG from the disk and cache it in memory
CSG Maze_Wall_Post  = Vitamins.get(servoFile)
						.toXMin()
						.toYMin()
					
						
// code goes here
Maze_Wall_Post = Maze_Wall_Post.movex(-Maze_Wall_Post.getCenterX())
Maze_Wall_Post = Maze_Wall_Post.movey(-Maze_Wall_Post.getCenterY())

def current_height = Maze_Wall_Post.getTotalZ()
def desired_height = 11*25.4

def scalar = desired_height / current_height

Maze_Wall_Post = Maze_Wall_Post.scalez(scalar)

//String filename =ScriptingEngine.getWorkspace().getAbsolutePath()+"/CopiedStl.stl";
//FileUtil.write(Paths.get(filename),
//		servo.toStlString());
//println "STL EXPORT to "+filename
ScriptingEngine.pushCodeToGit("https://github.com/WPIRoboticsEngineering/Modular-Maze.git",
	 "main", 
	 "Maze-Wall-Post-Printable.stl", 
	 Maze_Wall_Post.toStlString(), 
	 "Printable 11inch demo version")

return Maze_Wall_Post