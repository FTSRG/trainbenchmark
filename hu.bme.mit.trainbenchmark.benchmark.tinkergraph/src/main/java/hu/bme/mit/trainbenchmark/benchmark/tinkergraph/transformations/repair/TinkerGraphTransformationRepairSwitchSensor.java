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
package hu.bme.mit.trainbenchmark.benchmark.tinkergraph.transformations.repair;

import static hu.bme.mit.trainbenchmark.benchmark.tinkergraph.constants.TinkerGraphConstants.labelSensor;
import static hu.bme.mit.trainbenchmark.benchmark.tinkergraph.constants.TinkerGraphConstants.relationshipTypeMonitoredBy;

import java.util.Collection;

import org.neo4j.graphdb.Node;

import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.driver.TinkerGraphDriver;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.matches.TinkerGraphSwitchSensorMatch;

public class TinkerGraphTransformationRepairSwitchSensor extends TinkerGraphTransformationRepair<TinkerGraphSwitchSensorMatch> {

	public TinkerGraphTransformationRepairSwitchSensor(final TinkerGraphDriver driver) {
		super(driver);
	}

	@Override
	public void performRHS(final Collection<TinkerGraphSwitchSensorMatch> matches) {
		for (final TinkerGraphSwitchSensorMatch ssnm : matches) {
			final Node sw = ssnm.getSw();
			final Node sensor = driver.getGraphDb().createNode(labelSensor);
			sw.createRelationshipTo(sensor, relationshipTypeMonitoredBy);
		}
	}

}
