/**
 * This Java Class is part of the Impro-Visor Application.
 * <p>
 * Copyright (C) 2015-2016 Robert Keller and Harvey Mudd College.
 * <p>
 * Impro-Visor is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * <p>
 * Impro-Visor is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of merchantability or fitness
 * for a particular purpose. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * Impro-Visor; if not, write to the Free Software Foundation, Inc., 51 Franklin
 * St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package imp.trading.tradingResponseModes;

import imp.trading.TradingResponseInfo;
import imp.data.MelodyPart;

/**
 *
 * @author Zach Kondak
 */
public class RepeatTRM extends BlockResponseMode {

    public RepeatTRM(String message) {
        super(message);
    }

    @Override
    public MelodyPart generateResponse() {
        return responseInfo.getResponse();
    }
}