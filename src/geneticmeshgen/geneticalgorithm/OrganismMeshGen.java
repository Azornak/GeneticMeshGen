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
public class OrganismMeshGen extends Organism {
    private ArrayList<Float> vertexes;
    private ArrayList<Float> uvs;
    private int[] texture;
    
    private float fitness;
    
    
    
    public OrganismMeshGen(Parameters params) {
        super(params);
        
        this.vertexes = new ArrayList<>();
        this.uvs = new ArrayList<>();
        
        this.texture = new int[params.organismTextureWidth * params.organismTextureHeight * 3];
    }
    
    public OrganismMeshGen(OrganismMeshGen copy,Parameters params)
    {
        super(params);
        
        this.vertexes = new ArrayList<>();
        for(int i=0;i<copy.vertexes.size();i++) this.vertexes.add(copy.vertexes.get(i));
        
        this.uvs = new ArrayList<>();
        for(int i=0;i<copy.uvs.size();i++) this.uvs.add(copy.uvs.get(i));
        
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
