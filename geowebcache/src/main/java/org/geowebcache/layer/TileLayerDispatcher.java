/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Arne Kepp, The Open Planning Project, Copyright 2008
 */
package org.geowebcache.layer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geowebcache.GeoWebCacheException;
import org.geowebcache.cache.CacheException;
import org.geowebcache.util.Configuration;

public class TileLayerDispatcher {
	private static Log log = LogFactory.getLog(org.geowebcache.layer.TileLayerDispatcher.class);
	private HashMap layers = new HashMap();
	
	public TileLayerDispatcher() {
		log.info("TileLayerFactor constructed");
	}
	
	
	public TileLayer getTileLayer(String layerIdent) {
		return (TileLayer) layers.get(layerIdent);
	}

	public void setConfig(Configuration config) {
		Map configLayers = null;
		try {
			configLayers = config.getTileLayers();
		} catch (GeoWebCacheException gwcce) {
			log.error("Failed to add layers from " + config.getIdentifier());
			 gwcce.printStackTrace();
		}
		
		log.info("Adding layers from " + config.getIdentifier());
		if(configLayers != null && configLayers.size() > 0 ){
			Iterator iter = configLayers.keySet().iterator();
			while(iter.hasNext()) {
				String layerIdent = (String) iter.next();
				layers.put(layerIdent, configLayers.get(layerIdent));
			}
		} else {
			log.error("Configuration "+config.getIdentifier()+" contained no layers.");
		}
	}
}