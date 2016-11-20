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
package hu.bme.mit.trainbenchmark.benchmark.emf.transformation.repair;

import java.util.Collection;

import hu.bme.mit.trainbenchmark.benchmark.emf.driver.EmfDriver;
import hu.bme.mit.trainbenchmark.benchmark.emf.matches.EmfConnectedSegmentsMatch;
import hu.bme.mit.trainbenchmark.benchmark.emf.transformation.EmfTransformation;
import hu.bme.mit.trainbenchmark.railway.Segment;

public class EmfTransformationRepairConnectedSegments<TDriver extends EmfDriver, TConnectedSegmentsMatch extends EmfConnectedSegmentsMatch>
		extends EmfTransformation<TConnectedSegmentsMatch, TDriver> {

	public EmfTransformationRepairConnectedSegments(final TDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TConnectedSegmentsMatch> matches) {
		for (final EmfConnectedSegmentsMatch match : matches) {
			final Segment segment1 = match.getSegment1();
			final Segment segment2 = match.getSegment2();
			final Segment segment3 = match.getSegment3();

			segment1.getConnectsTo().remove(segment2);
			segment1.getConnectsTo().add(segment3);
			segment2.getConnectsTo().clear();
			segment2.getMonitoredBy().clear();
		}
	}
}
