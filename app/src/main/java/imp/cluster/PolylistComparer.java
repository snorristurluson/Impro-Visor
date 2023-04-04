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

import java.util.Comparator;

import polya.Polylist;

/**
 *
 * @author Jon Gillick
 */
public class PolylistComparer implements Comparator {

    public int compare(Object a, Object b) {

        Polylist p1 = (Polylist) a;
        String first = p1.toString();

        Polylist p2 = (Polylist) b;
        String second = p2.toString();

        return first.compareTo(second);

    }

}
