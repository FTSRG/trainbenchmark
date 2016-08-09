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
package hu.bme.mit.trainbenchmark.benchmark.emfapi.benchmarkcases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hu.bme.mit.trainbenchmark.benchmark.emf.driver.EmfDriver;
import hu.bme.mit.trainbenchmark.benchmark.emf.matches.EmfConnectedSegmentsInjectMatch;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

public class EmfApiQueryConnectedSegmentsInject<TDriver extends EmfDriver> extends EmfApiQuery<EmfConnectedSegmentsInjectMatch, TDriver> {

	public EmfApiQueryConnectedSegmentsInject(final TDriver driver) {
		super(RailwayQuery.CONNECTEDSEGMENTS_INJECT, driver);
	}

	@Override
	public Collection<EmfConnectedSegmentsInjectMatch> evaluate() {
		final List<EmfConnectedSegmentsInjectMatch> matches = new ArrayList<>();

		
		return matches;
	}

}
