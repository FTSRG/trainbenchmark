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

import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.driver.TinkerGraphDriver;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.matches.TinkerGraphRouteSensorInjectMatch;
import hu.bme.mit.trainbenchmark.constants.ModelConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

public class TinkerGraphQueryRouteSensorInject<TTinkerGraphDriver extends TinkerGraphDriver>
		extends TinkerGraphQuery<TinkerGraphRouteSensorInjectMatch, TTinkerGraphDriver> {

	public TinkerGraphQueryRouteSensorInject(final TTinkerGraphDriver driver) {
		super(RailwayQuery.ROUTESENSOR, driver);
	}

	@Override
	public Collection<TinkerGraphRouteSensorInjectMatch> evaluate() {
		final Collection<TinkerGraphRouteSensorInjectMatch> matches = new ArrayList<>();

		Collection<Vertex> routes = driver.getVertices(ModelConstants.ROUTE);
		for (Vertex route : routes) {
			Iterable<Edge> edges = () -> route.edges(Direction.OUT, ModelConstants.GATHERS);
			for (Edge gathers : edges) {
				Vertex sensor = gathers.inVertex();
				matches.add(new TinkerGraphRouteSensorInjectMatch(route, sensor));
			}			
		}
		
		return matches;
	}
}
