package geneticmeshgen.geneticalgorithm.custom;

/**
 * Takes 4 images taken at 90 degree intervals and attempts to generate a 3D Model that
 * fits the supplied images.
 * The fitness is calculated as the difference between the supplied picture and the generated model,
 * this means that fitness will start at a negative number and will work itself up to the maximum of 0.
 * 
 * @author Vegard
 */

import geneticmeshgen.geneticalgorithm.EvaluationCallback;
import geneticmeshgen.geneticalgorithm.Organism;
import geneticmeshgen.geneticalgorithm.Population;
import java.util.Random;
import processing.core.*;

public class Main extends PApplet implements EvaluationCallback {
    private PShape p;
    public Main m; 
    public PImage[] sprites;
    int[][] spritePixels;
    private final String spriteName = "Admiral_large"; //The base name of the images
    
    Population pop;
    ParametersMeshGen params;
    OrganismMeshGen test;
    
    ControlWindow control;
    
    private boolean newEpoch = false;
    float previewRot;
    int previewSprite;
    @Override
    public void settings(){
        sprites = new PImage[4];
        
        //Load the images
        sprites[0] = loadImage("sprites/" + spriteName + "_ne.bmp"); 
        sprites[1] = loadImage("sprites/" + spriteName + "_nw.bmp");
        sprites[2] = loadImage("sprites/" + spriteName + "_sw.bmp");
        sprites[3] = loadImage("sprites/" + spriteName + "_se.bmp");
        spritePixels = new int[4][sprites[0].height * sprites[0].width];
       
        //Load the pixels of the original images and store them for later comparison.
        for(int i = 0; i < 4; i++){
            sprites[i].loadPixels();
            System.arraycopy(sprites[i].pixels, 0, spritePixels[i], 0, spritePixels[i].length);
        }
        
        //Set up the window size
        size(sprites[0].width, sprites[0].height, P3D);
        
        m = this;
        
        //GA Parameters
        params = new ParametersMeshGen();
        
        params.populationSize = 150;
        params.evolutionMutationChance = 0.6f;
        params.evolutionCrossoverChance = 0.4f;
        params.evolutionKeepBest = true;
        params.evolutionElitismCount = 5;
        
        params.organismColorMutationRate = 10;
        params.organismTextureWidth = 128;
        params.organismTextureHeight = 128;
        params.organismUVMutationRate = 1.0f;
        params.organismVertexMutationRate = 5.0f;
        
        params.organismNumVertexes = 50;
        
        params.mutateTexture = true;
        params.mutateUVs = true;
        params.mutateVertexes = true;
        
        params.random = new Random();
        
        pop = new Population(params,OrganismMeshGen.template(params));
        pop.setCallback(this);
        
        previewRot = 0.0f;
        previewSprite = 0;
        
    }
    
    
    @Override
    public void setup(){
        frameRate(1000);
        textureMode(NORMAL);
        noStroke();
        //String[] args = {"Control Window"};
        // control = new ControlWindow(this);
         //PApplet.runSketch(args, control);
    }
    
    @Override
    public void draw(){
        clear();
        newEpoch = false;
        
        //
        do {
            float rot = 0, fitness = 0;
            test = (OrganismMeshGen) pop.getNextValidationOrganism();
            p = test.getShape(this);
            for(int i = 0; i < 4; i++){

                pushMatrix();
                
                background(255, 255, 0);
                ortho();
                
                translate(width/2.0f, height/2.0f, 0);
                rotateX(-atan(0.5f));
                rotateY(rot);
                shape(p);
                
                popMatrix();
                
                fitness += compareScreenToSprite(i);
                rot += PI/2;
            }
            pop.finishValidationOrganism(fitness);
        }
        while(!newEpoch);
        
        // Render best organism from this generation
        {
            test = (OrganismMeshGen)pop.getBestOrganism();
            background(sprites[previewSprite]);

            pushMatrix();
            ortho();
            translate(width/2.0f, height/2.0f, 0);
            rotateX(-atan(0.5f));
            rotateY(previewRot);
            shape(p);
            popMatrix();
            
        }
        if(previewSprite < 3){
         previewRot += PI/2; 
         previewSprite++;
        }
        else{
            previewRot = 0;
            previewSprite = 0;
        }
        
    }
    
    
    /**
     * Calculate fitness of the 
     * @param sprite
     * @return 
     */
    public float compareScreenToSprite(int sprite){
        float fitness = 0;
        loadPixels();
        for(int i = 0; i < spritePixels[sprite].length; i++){
            int x = spritePixels[sprite][i];
            int y = pixels[i];
            fitness -= Math.abs((x & 0xFF) - (y & 0xFF))
                + Math.abs(((x & 0xFF00) >> 8) - ((y & 0xFF00) >> 8))
                + Math.abs(((x & 0xFF0000) >> 16) - ((y & 0xFF0000) >> 16));
        }
        
        return fitness;
    }
    
    
    public static void main(String args[]) {
        PApplet.main(new String[] {"--present", "geneticmeshgen.geneticalgorithm.custom.Main" });
    }

    /**
     * Finished evaluating a population, print gen number and best fitness
     * @param generation
     * @param best 
     */
    @Override
    public void finishedEvaluation(int generation, Organism best) {
        System.out.println("#" + generation + " | Fitness: " + best.getFitness()); //TODO:  Print average fitness
        newEpoch = true;
    }
    
    /**
     * Draws debug text
     */
    private void drawDebugText(){
        textSize(32);
        fill(255, 0, 0);
        text("Vert.Mut : " + (params.mutateVertexes ? "ON" : "OFF"), width/2.0f, height/2.0f, 0);
        text("UV.Mut : " + (params.mutateUVs ? "ON" : "OFF"), 15, 5, 0);
        text("Text.Mut : " + (params.mutateVertexes ? "ON" : "OFF"), 25, 5, 0);
        
    }
    
    
    /**
     * Toggle mutation of certain values on/off
     */
    @Override
    public void keyReleased(){
        if(key == 'u' || key == 'U'){
            params.mutateUVs = !params.mutateUVs;
            System.out.println("UV mutation is now " + (params.mutateUVs ? "on" : "off"));
        }
        else if(key == 'v' || key == 'V'){
            params.mutateVertexes = !params.mutateVertexes;
            System.out.println("Vertex mutation is now " + (params.mutateVertexes ? "on" : "off"));
        }
        else if(key == 't' || key == 'T'){
            params.mutateTexture = !params.mutateTexture;
            System.out.println("Texture mutation is now " + (params.mutateTexture ? "on" : "off"));
        }
        
    }
}
