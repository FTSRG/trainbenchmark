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
package hu.bme.mit.trainbenchmark.benchmark.tinkergraph.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.MatchComparator;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.driver.VertexComparator;

import org.neo4j.graphdb.Node;

public class TinkerGraphMatchComparator extends MatchComparator<TinkerGraphMatch, Node> {

	protected VertexComparator nc = new VertexComparator();

	@Override
	public int compare(final TinkerGraphMatch o1, final TinkerGraphMatch o2) {
		final Node[] m1 = o1.toArray();
		final Node[] m2 = o2.toArray();
		return compareArrays(m1, m2, nc);
	}

}
