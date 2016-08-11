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
package hu.bme.mit.trainbenchmark.benchmark.tinkergraph.transformations.inject;

import java.util.Collection;

import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.driver.TinkerGraphDriver;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.matches.TinkerGraphRouteSensorInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.transformations.TinkerGraphTransformation;

public class TinkerGraphTransformationInjectRouteSensor<TTinkerGraphDriver extends TinkerGraphDriver>
		extends TinkerGraphTransformation<TinkerGraphRouteSensorInjectMatch, TTinkerGraphDriver> {

	public TinkerGraphTransformationInjectRouteSensor(final TTinkerGraphDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TinkerGraphRouteSensorInjectMatch> matches) {
		for (final TinkerGraphRouteSensorInjectMatch match : matches) {
//			final Iterable<Edge> gatherss = () -> route.edges(Direction.OUT, ModelConstants.GATHERS);
//			for (final Edge gathers : gatherss) {
//				gathers.remove();
//			}
		}
	}

}
