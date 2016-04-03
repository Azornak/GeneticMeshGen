/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmeshgen.geneticalgorithm;

import java.util.ArrayList;

/**
 *
 * @author JPiolho
 */
public class Organism {
    private ArrayList<Float> vertexes;
    private ArrayList<Float> uvs;
    private int[] texture;
    
    public Organism(Parameters params) {
        this.vertexes = new ArrayList<>();
        this.uvs = new ArrayList<>();
        
        this.texture = new int[params.organismTextureWidth * params.organismTextureHeight * 3];
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
}
