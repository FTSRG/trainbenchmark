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
package hu.bme.mit.trainbenchmark.benchmark.sesame.transformations;

import hu.bme.mit.trainbenchmark.benchmark.operations.ModelTransformation;
import hu.bme.mit.trainbenchmark.benchmark.sesame.driver.SesameDriver;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.inject.SesameTransformationInjectConnectedSegments;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.inject.SesameTransformationInjectPosLength;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.inject.SesameTransformationInjectRouteSensor;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.inject.SesameTransformationInjectSemaphoreNeighbor;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.inject.SesameTransformationInjectSwitchMonitored;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.inject.SesameTransformationInjectSwitchSet;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.repair.SesameTransformationRepairConnectedSegments;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.repair.SesameTransformationRepairPosLength;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.repair.SesameTransformationRepairRouteSensor;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.repair.SesameTransformationRepairSemaphoreNeighbor;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.repair.SesameTransformationRepairSwitchMonitored;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.repair.SesameTransformationRepairSwitchSet;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;
import hu.bme.mit.trainbenchmark.constants.Scenario;

public abstract class SesameTransformation<TObject> extends ModelTransformation<TObject, SesameDriver> {

	protected SesameTransformation(final SesameDriver driver) {
		super(driver);
	}

	public static ModelTransformation<?, ?> newInstance(final SesameDriver driver, final RailwayQuery query, final Scenario scenario) {
		switch (scenario) {
		case REPAIR:
			switch (query) {
			case CONNECTEDSEGMENTS:
				return new SesameTransformationRepairConnectedSegments(driver);
			case POSLENGTH:
				return new SesameTransformationRepairPosLength(driver);
			case ROUTESENSOR:
				return new SesameTransformationRepairRouteSensor(driver);
			case SEMAPHORENEIGHBOR:
				return new SesameTransformationRepairSemaphoreNeighbor(driver);
			case SWITCHMONITORED:
				return new SesameTransformationRepairSwitchMonitored(driver);
			case SWITCHSET:
				return new SesameTransformationRepairSwitchSet(driver);
			default:
				break;
			}
		case INJECT:
			switch (query) {
			case CONNECTEDSEGMENTS:
				return new SesameTransformationInjectConnectedSegments(driver);
			case POSLENGTH:
				return new SesameTransformationInjectPosLength(driver);
			case ROUTESENSOR:
				return new SesameTransformationInjectRouteSensor(driver);
			case SEMAPHORENEIGHBOR:
				return new SesameTransformationInjectSemaphoreNeighbor(driver);
			case SWITCHMONITORED:
				return new SesameTransformationInjectSwitchMonitored(driver);
			case SWITCHSET:
				return new SesameTransformationInjectSwitchSet(driver);
			default:
				break;
			}
		default:
			break;
		}
		throw new UnsupportedOperationException("Query: " + query.toString() + ", scenario: " + scenario);
	}
}
