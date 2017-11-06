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
package hu.bme.mit.trainbenchmark.benchmark.graphflow.matches;

import ca.waterloo.dsg.graphflow.query.result.subgraph.Vertex;
import hu.bme.mit.trainbenchmark.benchmark.matches.SwitchSetMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

import static hu.bme.mit.trainbenchmark.constants.QueryConstants.VAR_ROUTE;
import static hu.bme.mit.trainbenchmark.constants.QueryConstants.VAR_SEMAPHORE;
import static hu.bme.mit.trainbenchmark.constants.QueryConstants.VAR_SW;
import static hu.bme.mit.trainbenchmark.constants.QueryConstants.VAR_SWP;

public class GraphflowSwitchSetMatch extends GraphflowMatch implements SwitchSetMatch {

	public GraphflowSwitchSetMatch(final Map<String, Object> match) {
		super(match);
	}

	@Override
	public Vertex getSemaphore() {
		return (Vertex) match.get(VAR_SEMAPHORE);
	}

	@Override
	public Vertex getRoute() {
		return (Vertex) match.get(VAR_ROUTE);
	}

	@Override
	public Vertex getSwP() {
		return (Vertex) match.get(VAR_SWP);
	}

	@Override
	public Vertex getSw() {
		return (Vertex) match.get(VAR_SW);
	}

	public String getCurrentPosition() {
		return (String) match.get(QueryConstants.VAR_CURRENTPOSITION);
	}

	public String getPosition() {
		return (String) match.get(QueryConstants.VAR_POSITION);
	}

}
