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

package hu.bme.mit.trainbenchmark.benchmark.dgraph.queries;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.tinkerpop.gremlin.structure.Vertex;

import hu.bme.mit.trainbenchmark.benchmark.dgraph.driver.DgraphDriver;
import hu.bme.mit.trainbenchmark.benchmark.dgraph.matches.DgraphSwitchMonitoredInjectMatch;
import hu.bme.mit.trainbenchmark.constants.ModelConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

public class DgraphQuerySwitchMonitoredInject<TDgraphDriver extends DgraphDriver>
		extends DgraphQuery<DgraphSwitchMonitoredInjectMatch, TDgraphDriver> {

	public DgraphQuerySwitchMonitoredInject(final TDgraphDriver driver) {
		super(RailwayQuery.SWITCHMONITORED_INJECT, driver);
	}

	@Override
	public Collection<DgraphSwitchMonitoredInjectMatch> evaluate() {
		final Collection<DgraphSwitchMonitoredInjectMatch> matches = new ArrayList<>();

		Collection<Vertex> switches = driver.getVertices(ModelConstants.SWITCH);
		for (Vertex sw : switches) {
			matches.add(new DgraphSwitchMonitoredInjectMatch(sw));
		}
		
		return matches;
	}
}
