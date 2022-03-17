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

Maze_Wall_Peg = new RoundedCube(80,80,10).cornerRadius(2).toCSG()

def Post_Height 	= new LengthParameter("Height(in)",8,[24.0,1.0])
def Base_Size		= new LengthParameter("Size(cm)",8,[24.0,1.0])
def Base_Height	= new LengthParameter("Height(cm)",8,[24.0,1.0])
//Peg size roughtly 4mm
Maze_Wall_Post.setParameter(Post_Height)
Maze_Wall_Peg.setParameter(Base_Size).setParameter(Base_Height)

def current_height = Maze_Wall_Post.getTotalZ()
def desired_height = Post_Height.getMM() *25.4

def scalar = desired_height / current_height

Maze_Wall_Post = Maze_Wall_Post.scalez(scalar)



//String filename =ScriptingEngine.getWorkspace().getAbsolutePath()+"/CopiedStl.stl";
//FileUtil.write(Paths.get(filename),
//		servo.toStlString());
//println "STL EXPORT to "+filename
//ScriptingEngine.pushCodeToGit("git@github.com:Gregory-Marshall/Modular-Maze.git",
//	 "main", 
//	 "Maze-Wall-Post-Printable.stl", 
//	 Maze_Wall_Post.toStlString(), 
//	 "Printable 11inch demo version")

BowlerStudioController.addCsg(Maze_Wall_Post)
BowlerStudioController.addCsg(Maze_Wall_Peg)
BowlerKernel.speak("Build Completed")
return null