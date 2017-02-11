/*******************************************************************************
 * Copyright (c) 2010-2015, Benedek Izso, Gabor Szarnyas, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Benedek Izso - initial API and implementation
 *   Gabor Szarnyas - initial API and implementation
 *******************************************************************************/
package hu.bme.mit.trainbenchmark.benchmark.dgraph.matches;

import org.apache.tinkerpop.gremlin.structure.Vertex;

import hu.bme.mit.trainbenchmark.benchmark.matches.SwitchMonitoredInjectMatch;

public class DgraphSwitchMonitoredInjectMatch extends DgraphMatch implements SwitchMonitoredInjectMatch {

	protected final Vertex sw;
	
	public DgraphSwitchMonitoredInjectMatch(final Vertex sw) {
		super();
		this.sw = sw;
	}

	@Override
	public Vertex getSw() {
		return sw;
	}

}
