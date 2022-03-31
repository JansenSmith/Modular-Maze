import com.neuronrobotics.bowlerstudio.scripting.ScriptingEngine
import com.neuronrobotics.bowlerstudio.vitamins.Vitamins

import eu.mihosoft.vrl.v3d.CSG

// Load stls
File servoFile = ScriptingEngine.fileFromGit(
	"https://github.com/WPIRoboticsEngineering/Modular-Maze.git",
	"Maze_Wall_Post.stl");
DEFAULT_POST  = Vitamins.get(servoFile)
						.toXMin()
						.toYMin()
						
//Setup Params
Post_Height 	= new LengthParameter("Post Height(in)",8,[24.0,1.0])
Base_Size		= new LengthParameter("Base Radius(mm)",40,[240.0,10.0])
Base_Height	= new LengthParameter("Base Height(mm)",1,[20.0,0.1])
Peg_Radius	= new LengthParameter("Peg Radius(mm)",2,[8.0,0.1])

// Object Generation Functions
CSG Make_Post(){
	//Creates a centered version of the default post
	Maze_Wall_Post = DEFAULT_POST.movex(-DEFAULT_POST.getCenterX())
	Maze_Wall_Post = Maze_Wall_Post.movey(-Maze_Wall_Post.getCenterY())
	Maze_Wall_Post = Maze_Wall_Post.movey(-Maze_Wall_Post.getMinZ())
220
	def current_height = Maze_Wall_Post.getTotalZ()//get post height
	def desired_height = Post_Height.getMM() *25.4// get scale (Post_Height.getMM() returns a value in inches)

	//scales post
	def scalar = desired_height / current_height
	Maze_Wall_Post = Maze_Wall_Post.scalez(scalar)
	
	//attach params and set up regenerate function* 
	//*(function used to regenerate the object when the params are changed, in this case this function)
	return Maze_Wall_Post
			.setParameter(Post_Height)
			.setRegenerate({Make_Post()})
}

CSG Make_Peg(){
	//get parameter values
	h = Base_Height.getMM()
	w = Base_Size.getMM()
	r = Peg_Radius.getMM()
	//make a rounded cube
	Maze_Wall_Peg = new Cylinder(w,w,h).toCSG()
	//make a cylinder
	Peg = new Cylinder(r,r,Post_Height.getMM() *25.4-h,(int)30).toCSG()
	//combine them, and translase below xy plane
	Maze_Wall_Peg  = Maze_Wall_Peg.movez(-Maze_Wall_Peg.getMaxZ())
	Peg = Peg.movez(-Maze_Wall_Peg.getMaxZ())
	Maze_Wall_Peg  = Maze_Wall_Peg.union(Peg)
	

	//attach params and regenerate function
	return Maze_Wall_Peg
			.setParameter(Base_Size)
			.setParameter(Base_Height)
			.setParameter(Peg_Radius)
			.setRegenerate({Make_Peg()})
	}

//Object Generation
Maze_Wall_Post = Make_Post()
Maze_Wall_Peg = Make_Peg()

//Object Placement
try{
	//BowlerStudioController.addCsg(Maze_Wall_Post.intersect(Maze_Wall_Peg)) //sanity check for peg size
	//BowlerStudioController.addCsg(Maze_Wall_Post)
	//BowlerStudioController.addCsg(Maze_Wall_Peg)
	BowlerStudioController.addCsg(Maze_Wall_Post.union(Maze_Wall_Peg)) 
}catch(e){}
/* 
String filename =ScriptingEngine.getWorkspace().getAbsolutePath()+"/CopiedStl.stl";
FileUtil.write(Paths.get(filename),
		servo.toStlString());
println "STL EXPORT to "+filename
ScriptingEngine.pushCodeToGit("https://github.com/WPIRoboticsEngineering/Modular-Maze.git",
	 "main", 
	 "Maze-Wall-Post-Printable.stl", 
	 Maze_Wall_Post.toStlString(), 
	 "Printable 11inch demo version")
*/
return [Maze_Wall_Post.union(Maze_Wall_Peg)]