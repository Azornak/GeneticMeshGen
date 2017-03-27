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

import geneticmeshgen.geneticalgorithm.Organism;
import geneticmeshgen.geneticalgorithm.Parameters;
import geneticmeshgen.utils.MathUtils;
import java.util.ArrayList;
import processing.core.*;

/**
 * Represents an organism, which in this case is a model and texture.
 * @author Vegard
 */
public class OrganismMeshGen extends Organism {
    private ArrayList<Float> vertexes;
    private ArrayList<Float> uvs;
    private int[] texture;
    
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
        
        //Initialize organism vertexes
        for(int i=0;i<parameters.organismNumVertexes;i++) {
            org.vertexes.add(parameters.random.nextFloat() * 2 - 1);
            org.vertexes.add(parameters.random.nextFloat() * 2 - 1);
            org.vertexes.add(parameters.random.nextFloat() * 2 - 1);
            
            org.uvs.add(0.0f);
            org.uvs.add(0.0f);
        }
        //Initialize organism texture
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

    /**
     * Mutate the organism
     * This mutates both Vertexes, UVs and Texture
     * @return The mutated organism
     */
    @Override
    public Organism mutate() {
        OrganismMeshGen newOrg = this.clone();
        
        //Mutate vertexes
        if(parameters.mutateVertexes){
            for(int i=0;i<newOrg.vertexes.size();i++) {
                if(parameters.random.nextFloat() < parameters.evolutionMutationChance) {
                    int pos = i;
                    newOrg.vertexes.set(pos, newOrg.vertexes.get(pos) + ((parameters.random.nextFloat() * 2f - 1f) * parameters.organismVertexMutationRate));
                }
            }
        }
        
        //Mutate UVs
        if(parameters.mutateUVs){
            for(int i=0;i<newOrg.uvs.size();i++) {
                if(parameters.random.nextFloat() < parameters.evolutionMutationChance) {
                    int pos = i;
                    newOrg.uvs.set(pos, newOrg.uvs.get(pos) + ((parameters.random.nextFloat() * 2f - 1f) * parameters.organismUVMutationRate));
                }
            }
        }
        
        //Mutate texture
        if(parameters.mutateTexture){
            for(int i=0;i<newOrg.texture.length;i++) {

                if(parameters.random.nextFloat() < parameters.evolutionMutationChance) {
                    int pos = i;

                    int argb = newOrg.texture[pos];
                    int alpha = 0xFF & (argb >> 24);
                    int red = 0xFF & ( argb >> 16);
                    int green = 0xFF & (argb >> 8 );
                    int blue = 0xFF & (argb);

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

    /**
     * Mate this organism with another organism
     * 
     * @param other The organism to mate with this one
     * @return The child organism
     * TODO:    Enable more than 1 point crossover
     */
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
        
        System.arraycopy(this.texture, 0, a.texture, 0, p);
        System.arraycopy(mate.texture, p, a.texture, p, max - p);
        
        System.arraycopy(mate.texture, 0, b.texture, 0, p);
        System.arraycopy(this.texture, p, b.texture, p, max - p);
       
        return new Organism[] { a, b };
    }

    
    /**
     * Clone this organism
     * @return 
     */
    @Override
    public OrganismMeshGen clone() {
        OrganismMeshGen newOrg = new OrganismMeshGen(parameters);
        
        newOrg.vertexes = new ArrayList<>();
        for(int i=0;i<this.vertexes.size();i++) newOrg.vertexes.add(this.vertexes.get(i));
        
        newOrg.uvs = new ArrayList<>();
        for(int i=0;i<this.uvs.size();i++) newOrg.uvs.add(this.uvs.get(i));
        
        newOrg.texture = new int[parameters.organismTextureWidth * parameters.organismTextureHeight];
        System.arraycopy(this.texture, 0, newOrg.texture, 0, this.texture.length);
        
        return newOrg;
    }
    
    /**
     * Generate the model as a shape we can display
     * @param m Reference to the main loop
     * @return  The shape
     */
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
