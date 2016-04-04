/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmeshgen.geneticalgorithm.custom;

import geneticmeshgen.geneticalgorithm.Organism;
import geneticmeshgen.geneticalgorithm.OrganismTest;
import geneticmeshgen.geneticalgorithm.Parameters;
import geneticmeshgen.utils.MathUtils;
import java.util.ArrayList;
import processing.core.*;

/**
 *
 * @author JPiolho
 */
public class OrganismMeshGen extends Organism {
    private ArrayList<Float> vertexes;
    private ArrayList<Float> uvs;
    private int[] texture;
    
    private float fitness;
    
    protected ParametersMeshGen parameters;
    
    
    public OrganismMeshGen(Parameters params) {
        super(params);
        this.parameters = (ParametersMeshGen)params;
        
        this.vertexes = new ArrayList<>();
        this.uvs = new ArrayList<>();
        
        
        this.texture = new int[parameters.organismTextureWidth * parameters.organismTextureHeight];
    }
    
    public static OrganismMeshGen template(ParametersMeshGen parameters) {
        OrganismMeshGen org = new OrganismMeshGen(parameters);
        
        for(int i=0;i<parameters.organismNumVertexes;i++) {
            org.vertexes.add(parameters.random.nextFloat() * 2 - 1);
            org.vertexes.add(parameters.random.nextFloat() * 2 - 1);
            org.vertexes.add(parameters.random.nextFloat() * 2 - 1);
            
            org.uvs.add(0.0f);
            org.uvs.add(0.0f);
        }
        
        for(int i=0;i<org.texture.length;i++) {
            org.texture[i] = 0xFF000000;
        }
        
        return org;
    }
    
    public Float[] getVertexes() {
        return vertexes.toArray(new Float[vertexes.size()]);
    }
    
    public Float[] getUVs() {
        return uvs.toArray(new Float[uvs.size()]);
    }
    
    public int[] getTextureRGB() {
        return texture.clone();
    }

    @Override
    public Organism mutate() {
        OrganismMeshGen newOrg = this.clone();
        
        
        if(parameters.mutateVertexes){
            for(int i=0;i<newOrg.vertexes.size();i++) {
                if(parameters.random.nextFloat() < parameters.evolutionMutationChance) {
                    int pos = i;
                    newOrg.vertexes.set(pos, newOrg.vertexes.get(pos) + ((parameters.random.nextFloat() * 2f - 1f) * parameters.organismVertexMutationRate));
                }
            }
        }
        
        if(parameters.mutateUVs){
            for(int i=0;i<newOrg.uvs.size();i++) {
                if(parameters.random.nextFloat() < parameters.evolutionMutationChance) {
                    int pos = i;
                    newOrg.uvs.set(pos, newOrg.uvs.get(pos) + ((parameters.random.nextFloat() * 2f - 1f) * parameters.organismUVMutationRate));
                }
            }
        }
        
        if(parameters.mutateTexture){
            for(int i=0;i<newOrg.texture.length;i++) {

                if(parameters.random.nextFloat() < parameters.evolutionMutationChance) {
                    int pos = i;

                    int argb = newOrg.texture[pos];
                    int alpha = 0xFF & (argb >> 24);
                    int red = 0xFF & ( argb >> 16);
                    int green = 0xFF & (argb >> 8 );
                    int blue = 0xFF & (argb >> 0 );

                    switch(parameters.random.nextInt(3)) {
                        case 0: red = MathUtils.clamp(red + (parameters.random.nextInt(parameters.organismColorMutationRate*2) - parameters.organismColorMutationRate),0,255); break;
                        case 1: green = MathUtils.clamp(green + (parameters.random.nextInt(parameters.organismColorMutationRate*2) - parameters.organismColorMutationRate),0,255); break;
                        case 2: blue = MathUtils.clamp(blue + (parameters.random.nextInt(parameters.organismColorMutationRate*2) - parameters.organismColorMutationRate),0,255); break;
                    }

                    newOrg.texture[pos] = (alpha << 24) | (red << 16 ) | (green<<8) | blue;
                }
            }
        }
        
        return newOrg;
    }

    @Override
    public Organism[] crossover(Organism other) {
        OrganismMeshGen mate = (OrganismMeshGen)other;

        OrganismMeshGen a = this.clone();
        OrganismMeshGen b = mate.clone();
        
        // 1 point crossover
        
        // Vertexes
        int max = Math.min(a.vertexes.size(),b.vertexes.size());
        int p = parameters.random.nextInt(max);
        
        a.vertexes.clear();
        b.vertexes.clear();

        
        for(int i=0;i<p;i++) {
            a.vertexes.add(this.vertexes.get(i));
        }
        for(int i=p;i<max;i++) {
            a.vertexes.add(mate.vertexes.get(i));
        }
        
        for(int i=0;i<p;i++) {
            b.vertexes.add(mate.vertexes.get(i));
        }
        for(int i=p;i<max;i++) {
            b.vertexes.add(this.vertexes.get(i));
        }
        
        // UVs
        max = Math.min(a.uvs.size(),b.uvs.size());
        p = parameters.random.nextInt(max);
        
        a.uvs.clear();
        b.uvs.clear();
        
        for(int i=0;i<p;i++) {
            a.uvs.add(this.uvs.get(i));
        }
        for(int i=p;i<max;i++) {
            a.uvs.add(mate.uvs.get(i));
        }
        
        for(int i=0;i<p;i++) {
            b.uvs.add(mate.uvs.get(i));
        }
        for(int i=p;i<max;i++) {
            b.uvs.add(this.uvs.get(i));
        }
        
        // Texture
        max = Math.min(a.texture.length,b.texture.length);
        p = parameters.random.nextInt(max);
        
        for(int i=0;i<p;i++) {
            a.texture[i] = this.texture[i];
        }
        for(int i=p;i<max;i++) {
            a.texture[i] = mate.texture[i];
        }
        
        for(int i=0;i<p;i++) {
            b.texture[i] = mate.texture[i];
        }
        for(int i=p;i<max;i++) {
            b.texture[i] = this.texture[i];
        }
       
        return new Organism[] { a, b };
    }

    

    @Override
    public OrganismMeshGen clone() {
        OrganismMeshGen newOrg = new OrganismMeshGen(parameters);
        
        newOrg.vertexes = new ArrayList<>();
        for(int i=0;i<this.vertexes.size();i++) newOrg.vertexes.add(this.vertexes.get(i));
        
        newOrg.uvs = new ArrayList<>();
        for(int i=0;i<this.uvs.size();i++) newOrg.uvs.add(this.uvs.get(i));
        
        newOrg.texture = new int[parameters.organismTextureWidth * parameters.organismTextureHeight];
        for(int i=0;i<this.texture.length;i++) newOrg.texture[i] = this.texture[i];
        
        return newOrg;
    }
    
    public PShape getShape(Main m){
        
        PShape tmp = m.createShape();
        
        PImage tmpImg = m.createImage(parameters.organismTextureWidth, parameters.organismTextureHeight, m.ARGB);
        tmpImg.loadPixels();
        System.arraycopy(texture, 0, tmpImg.pixels, 0, tmpImg.pixels.length);
        tmpImg.updatePixels();
        
        tmp.beginShape(m.TRIANGLES);
        for(int i = 0, n = 0; i < vertexes.size(); i += 3, n += 2){
            tmp.vertex(vertexes.get(i), vertexes.get(i+1), vertexes.get(i+2), uvs.get(n), uvs.get(n + 1));
        }
        tmp.endShape(m.CLOSE);
        tmp.setTexture(tmpImg);
        
        return tmp;
    }
            
    
}
