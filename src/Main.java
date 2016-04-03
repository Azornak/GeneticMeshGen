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
import geneticmeshgen.geneticalgorithm.OrganismMeshGen;
import geneticmeshgen.geneticalgorithm.OrganismTest;
import geneticmeshgen.geneticalgorithm.Parameters;
import geneticmeshgen.geneticalgorithm.Population;
import java.util.Random;
import processing.core.*;

public class Main extends PApplet{
    PShape p;
    
    Population pop;
    Parameters params;
    
    String testString = "Hello World!";
    
    @Override
    public void settings(){
        size(1000, 1000, P3D);
        
        params = new Parameters();
        
        params.populationSize = 100;
        params.evolutionMutationChance = 0.2f;
        params.evolutionCrossoverChance = 0.4f;
        
        params.evolutionKeepBest = true;
        params.evolutionElitismCount = 5;
        
        params.random = new Random();
        
        pop = new Population(params,OrganismTest.template(testString.length()));
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
        
        pop.epoch(new EvaluationCallback() {

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
        });
        
        Organism best = pop.getBestOrganism();
        System.out.println("#" + pop.getGeneration() + " | Fitness: " + best.getFitness() + " | " + ((OrganismTest)best).getString());
    }
    
    
    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "Main" });
    }
}
