/**
 * This Java Class is part of the Impro-Visor Application
 * <p>
 * Copyright (C) 2005-2012 Robert Keller and Harvey Mudd College
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

package imp.neuralnet;

/**
 * Created in June 2013
 * @author Hayden Blauzvern
 */
public interface ActivationFunction {

    public double act(double arg);

    public double use(double arg);

    public double deriv(double arg, double out);

    public String getName();
}
