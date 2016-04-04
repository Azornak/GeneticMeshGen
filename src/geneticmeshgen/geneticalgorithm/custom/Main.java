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
    
    @Override
    public void settings(){
        sprites = new PImage[4];
        
        sprites[0] = loadImage("sprites/" + spriteName + "_nw.bmp");
        sprites[1] = loadImage("sprites/" + spriteName + "_ne.bmp");
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
        params.evolutionCrossoverChance = 0.4f;
        params.evolutionKeepBest = true;
        params.evolutionElitismCount = 5;
        
        params.organismColorMutationRate = 100;
        params.organismTextureWidth = 128;
        params.organismTextureHeight = 128;
        params.organismUVMutationRate = 0.1f;
        params.organismVertexMutationRate = 0.5f;
        
        params.organismNumVertexes = 100;
        
        params.random = new Random();
        
        pop = new Population(params,OrganismMeshGen.template(params));
        pop.setCallback(this);
        
    }
    
    @Override
    public void setup(){
                frameRate(1000);
                textureMode(NORMAL);
    }
    
    @Override
    public void draw(){
        test = (OrganismMeshGen) pop.getNextValidationOrganism();
        p = test.getShape(this);
        background(255, 255, 0);
        noStroke();
       // camera(70.0f, 65.0f, 70.0f, 0.0f, 0.0f, 0.0f,  0.0f, 1.0f, 0.0f);
        ortho(-5.0f, 5.0f, -5.0f, 5.0f, 0.001f, 1000);
        
        translate(width/2.0f, height/2.0f, 0);
        shape(p);
        pop.finishValidationOrganism(compareScreenToSprite(0));
        
        /*pop.epoch(new EvaluationCallback() {

            @Override
            public float evaluateOrganism(Organism organism) {
                OrganismTest test = (OrganismTest)organism;
                
                String str = test.getString();
                
                float fitness = 0.0f;
                for(int i=0;i<str.length();i++) {
                    fitness -= Math.abs(testString.charAt(i) - str.charAt(i));
                }
                
                return fitness;
            }
        });*/
        

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
    }
}
