package geneticmeshgen.geneticalgorithm.custom;
import processing.core.*;
/**
 * Separate window that can be used to control mutation
 * parameters while the program is running
 * @author Vegard
 */
public class ControlWindow extends PApplet{
    Main app;
    public ControlWindow(Main m){
        app = m;
    }
    
    public void settings(){
        size(500,350);
    }
    
    public void setup(){
        textSize(30);
    }
    
    public void draw(){
       text("Hello, I'm a Control Window", 0, height/2);
    }
    
    public void mousePressed(){
        app.exit();
    }
}
