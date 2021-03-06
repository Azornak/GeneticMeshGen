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

import geneticmeshgen.geneticalgorithm.Parameters;

/**
 *
 * @author Vegard
 */
public class ParametersMeshGen extends Parameters {
    public int organismTextureWidth,organismTextureHeight;
    
    public float organismVertexMutationRate;
    public float organismUVMutationRate;
    public int organismColorMutationRate;
    
    public int organismNumVertexes;
}
