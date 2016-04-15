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
package hu.bme.mit.trainbenchmark.benchmark.tinkergraph.inject;

import static hu.bme.mit.trainbenchmark.benchmark.tinkergraph.constants.TinkerGraphConstants.labelSegment;
import static hu.bme.mit.trainbenchmark.benchmark.tinkergraph.constants.TinkerGraphConstants.relationshipTypeConnectsTo;
import static hu.bme.mit.trainbenchmark.benchmark.tinkergraph.constants.TinkerGraphConstants.relationshipTypeMonitoredBy;

import java.util.Collection;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.driver.TinkerGraphDriver;

public class TinkerGraphTransformationInjectConnectedSegments extends TinkerGraphTransformationInject {

	public TinkerGraphTransformationInjectConnectedSegments(final TinkerGraphDriver driver) {
		super(driver);
	}

	@Override
	public void performRHS(final Collection<Node> segments) {
		for (final Node segment1 : segments) {
			final Iterable<Relationship> monitoredByEdges = segment1.getRelationships(Direction.OUTGOING, relationshipTypeMonitoredBy);

			final Node segment2 = driver.getGraphDb().createNode(labelSegment);

			// (segment2)-[:monitoredBy]->(sensor)
			for (final Relationship monitoredByEdge : monitoredByEdges) {
				final Node sensor = monitoredByEdge.getEndNode();
				segment2.createRelationshipTo(sensor, relationshipTypeMonitoredBy);
			}

			// delete (segment1)-[:connectsTo]->(segment3)
			final Iterable<Relationship> connectsToEdges = segment1.getRelationships(Direction.OUTGOING, relationshipTypeConnectsTo);
			if (!connectsToEdges.iterator().hasNext()) {
				continue;
			}
			final Relationship connectsTo = connectsToEdges.iterator().next();
			final Node segment3 = connectsTo.getEndNode();
			connectsTo.delete();
			// (segment1)-[:connectsTo]->(segment2)
			segment1.createRelationshipTo(segment2, relationshipTypeConnectsTo);
			// (segment2)-[:connectsTo]->(segment3)
			segment2.createRelationshipTo(segment3, relationshipTypeConnectsTo);
		}
	}

}
