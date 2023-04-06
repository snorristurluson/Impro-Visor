/**
 * This Java Class is part of the Impro-Visor Application
 * <p>
 * Copyright (C) 2005-2009 Robert Keller and Harvey Mudd College
 * <p>
 * Impro-Visor is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * Impro-Visor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * merchantability or fitness for a particular purpose.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Impro-Visor; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package imp.cluster;

import java.util.*;

/**
 *
 * @author Jon Gillick
 */

public class ChainComparer implements Comparator<float[]> {

    public int compare(float[] a, float[] b) {

        boolean first = false;

        for (int i = a.length - 1; i >= 0; i--) {
            if (((float[]) a)[i] < ((float[]) b)[i]) first = true;
            if (((float[]) a)[i] > ((float[]) b)[i]) first = false;
        }

        if (first) return -1;
        else return 1;


    }
}