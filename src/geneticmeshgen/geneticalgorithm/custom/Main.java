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
    
    Population pop;
    ParametersMeshGen params;
    
    String testString = "Hello World!";
    
    @Override
    public void settings(){
        size(1000, 1000, P3D);
        
        m = this;
        
        params = new ParametersMeshGen();
        
        params.populationSize = 100;
        params.evolutionMutationChance = 0.2f;
        params.evolutionCrossoverChance = 0.4f;
        params.evolutionKeepBest = true;
        params.evolutionElitismCount = 5;
        
        params.organismColorMutationRate = 100;
        params.organismTextureWidth = 128;
        params.organismTextureHeight = 128;
        params.organismUVMutationRate = 0.01f;
        params.organismVertexMutationRate = 0.05f;
        
        params.organismNumVertexes = 100;
        
        params.random = new Random();
        
        pop = new Population(params,OrganismMeshGen.template(params));
        pop.setCallback(this);
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
    
    
    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "Main" });
    }

    @Override
    public void finishedEvaluation(int generation, Organism best) {
        System.out.println("#" + generation + " | Fitness: " + best.getFitness());
    }
}
