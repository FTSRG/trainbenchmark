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
import hu.bme.mit.trainbenchmark.benchmark.matches.PosLengthInjectMatch;

import java.util.Map;

import static hu.bme.mit.trainbenchmark.constants.QueryConstants.VAR_SEGMENT;

public class GraphflowPosLengthInjectMatch extends GraphflowMatch implements PosLengthInjectMatch {

	public GraphflowPosLengthInjectMatch(final Map<String, Object> match) {
		super(match);
	}

	@Override
	public Vertex getSegment() {
		return (Vertex) match.get(VAR_SEGMENT);
	}

}
