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

package hu.bme.mit.trainbenchmark.benchmark.tinkergraph.queries;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.tinkerpop.gremlin.structure.Vertex;

import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.driver.TinkerGraphDriver;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.matches.TinkerGraphPosLengthInjectMatch;
import hu.bme.mit.trainbenchmark.constants.ModelConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

public class TinkerGraphQueryPosLengthInject<TTinkerGraphDriver extends TinkerGraphDriver>
		extends TinkerGraphQuery<TinkerGraphPosLengthInjectMatch, TTinkerGraphDriver> {

	public TinkerGraphQueryPosLengthInject(final TTinkerGraphDriver driver) {
		super(RailwayQuery.POSLENGTH_INJECT, driver);
	}

	@Override
	public Collection<TinkerGraphPosLengthInjectMatch> evaluate() {
		final Collection<TinkerGraphPosLengthInjectMatch> matches = new ArrayList<>();

		Collection<Vertex> segments = driver.getVertices(ModelConstants.SEGMENT);
		for (Vertex segment : segments) {
			matches.add(new TinkerGraphPosLengthInjectMatch(segment));
		}
		
		return matches;
	}
}
