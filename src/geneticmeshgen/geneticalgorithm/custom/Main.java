package geneticmeshgen.geneticalgorithm.custom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vegard
 */

import geneticmeshgen.geneticalgorithm.EvaluationCallback;
import geneticmeshgen.geneticalgorithm.Organism;
import geneticmeshgen.geneticalgorithm.custom.OrganismMeshGen;
import geneticmeshgen.geneticalgorithm.OrganismTest;
import geneticmeshgen.geneticalgorithm.Parameters;
import geneticmeshgen.geneticalgorithm.Population;
import geneticmeshgen.geneticalgorithm.custom.ParametersMeshGen;
import java.util.Random;
import processing.core.*;

public class Main extends PApplet implements EvaluationCallback {
    private PShape p;
    public Main m; 
    public PImage[] sprites;
    int[][] spritePixels;
    private final String spriteName = "Admiral_large";
    
    Population pop;
    ParametersMeshGen params;
    OrganismMeshGen test;
    
    String testString = "Hello World!";
    
    
    private boolean newEpoch = false;
    float previewRot;
    int previewSprite;
    @Override
    public void settings(){
        sprites = new PImage[4];
        
        sprites[0] = loadImage("sprites/" + spriteName + "_ne.bmp");
        sprites[1] = loadImage("sprites/" + spriteName + "_nw.bmp");
        sprites[2] = loadImage("sprites/" + spriteName + "_sw.bmp");
        sprites[3] = loadImage("sprites/" + spriteName + "_se.bmp");
        spritePixels = new int[4][sprites[0].height * sprites[0].width];
       
        for(int i = 0; i < 4; i++){
            sprites[i].loadPixels();
            System.arraycopy(sprites[i].pixels, 0, spritePixels[i], 0, spritePixels[i].length);
        }
        
        
        size(sprites[0].width, sprites[0].height, P3D);
        
        m = this;
        
        params = new ParametersMeshGen();
        
        params.populationSize = 150;
        params.evolutionMutationChance = 0.2f;
        params.evolutionCrossoverChance = 0.3f;
        params.evolutionKeepBest = true;
        params.evolutionElitismCount = 5;
        
        params.organismColorMutationRate = 10;
        params.organismTextureWidth = 128;
        params.organismTextureHeight = 128;
        params.organismUVMutationRate = 1.0f;
        params.organismVertexMutationRate = 10.0f;
        
        params.organismNumVertexes = 100;
        
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
    }
    
    @Override
    public void draw(){
        clear();
        newEpoch = false;
        do {
            float rot = 0, fitness = 0;
            for(int i = 0; i < 4; i++){
                test = (OrganismMeshGen) pop.getNextValidationOrganism();
                p = test.getShape(this);

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
        
        // Render best organism
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
        PApplet.main(new String[] { "--present", "geneticmeshgen.geneticalgorithm.custom.Main" });
    }

    @Override
    public void finishedEvaluation(int generation, Organism best) {
        System.out.println("#" + generation + " | Fitness: " + best.getFitness());
        newEpoch = true;
    }
    
    private void drawDebugText(){
        textSize(32);
        fill(255, 0, 0);
        text("Vert.Mut : " + (params.mutateVertexes ? "ON" : "OFF"), width/2.0f, height/2.0f, 0);
        text("UV.Mut : " + (params.mutateUVs ? "ON" : "OFF"), 15, 5, 0);
        text("Text.Mut : " + (params.mutateVertexes ? "ON" : "OFF"), 25, 5, 0);
        
    }
    
    
    
    @Override
    public void keyReleased(){
        if(key == 'u' || key == 'U'){
            params.mutateUVs = !params.mutateUVs;
        }
        else if(key == 'v' || key == 'V'){
            params.mutateVertexes = !params.mutateVertexes;
        }
        else if(key == 't' || key == 'T'){
            params.mutateTexture = !params.mutateTexture;
        }
        
    }
}
