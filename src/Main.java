/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vegard
 */

import processing.core.*;

public class Main extends PApplet{
    PShape p;
    
    @Override
    public void settings(){
        size(1000, 1000, P3D);
    }
    
    @Override
    public void setup(){
        p = createShape();
        p.beginShape();
        p.fill(0, 0, 255);
        p.noStroke();
        p.vertex(0, 0);
        p.vertex(0, 50);
        p.vertex(50, 50);
        p.vertex(50, 0);
        p.vertex(80,-40);
        p.endShape(CLOSE);
}
    
    @Override
    public void draw(){
        background(0);
        translate(width/2.0f, height/2.0f);
        shape(p);
    }
    
    
    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "Main" });
    }
}
