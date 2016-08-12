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
package hu.bme.mit.trainbenchmark.benchmark.neo4j.transformations.inject;

import java.util.Collection;

import org.neo4j.graphdb.Relationship;

import hu.bme.mit.trainbenchmark.benchmark.neo4j.driver.Neo4jDriver;
import hu.bme.mit.trainbenchmark.benchmark.neo4j.matches.Neo4jRouteSensorInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.neo4j.transformations.Neo4jTransformation;
import hu.bme.mit.trainbenchmark.neo4j.Neo4jConstants;

public class Neo4jTransformationInjectRouteSensor extends Neo4jTransformation<Neo4jRouteSensorInjectMatch> {

	public Neo4jTransformationInjectRouteSensor(final Neo4jDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<Neo4jRouteSensorInjectMatch> matches) {
		for (final Neo4jRouteSensorInjectMatch match : matches) {
			final Iterable<Relationship> gatherss = match.getRoute().getRelationships(Neo4jConstants.relationshipTypeGathers);
			for (final Relationship gathers : gatherss) {
				if (gathers.getEndNode().equals(match.getSensor())) {
					gathers.delete();
				}
			}
		}
	}

}
