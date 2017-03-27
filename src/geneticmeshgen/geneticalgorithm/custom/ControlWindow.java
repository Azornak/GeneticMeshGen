/*
 * Copyright (C) 2017 Vegard Vatn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


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
