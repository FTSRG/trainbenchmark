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
package hu.bme.mit.trainbenchmark.benchmark.emfapi.queries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import hu.bme.mit.trainbenchmark.benchmark.emf.driver.EmfDriver;
import hu.bme.mit.trainbenchmark.benchmark.emf.matches.EmfConnectedSegmentsMatch;
import hu.bme.mit.trainbenchmark.benchmark.emf.transformation.query.EmfApiQuery;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;
import hu.bme.mit.trainbenchmark.railway.RailwayPackage;
import hu.bme.mit.trainbenchmark.railway.Region;
import hu.bme.mit.trainbenchmark.railway.Segment;
import hu.bme.mit.trainbenchmark.railway.Sensor;
import hu.bme.mit.trainbenchmark.railway.TrackElement;

public class EmfApiQueryConnectedSegments<TDriver extends EmfDriver> extends EmfApiQuery<EmfConnectedSegmentsMatch, TDriver> {

	public EmfApiQueryConnectedSegments(final TDriver driver) {
		super(RailwayQuery.CONNECTEDSEGMENTS, driver);
	}

	@Override
	public Collection<EmfConnectedSegmentsMatch> evaluate() {
		final List<EmfConnectedSegmentsMatch> matches = new ArrayList<>();

		final EList<Region> regions = driver.getContainer().getRegions();
		for (final Region region : regions) {
			for (final Sensor sensor : region.getSensors()) {
				// (sensor)-[:monitors]->(segment1:Segment)
				for (final TrackElement element1 : sensor.getMonitors()) {
					if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element1)) {
						continue;
					}
					final Segment segment1 = (Segment) element1;

					// (segment1)-[:connectsTo]->(segment2:Segment)
					for (final TrackElement element2 : segment1.getConnectsTo()) {
						if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element2)) {
							continue;
						}
						final Segment segment2 = (Segment) element2;

						// (segment2:Segment)-[:monitoredBy]->(sensor)
						if (!segment2.getMonitoredBy().contains(sensor)) {
							continue;
						}

						// (segment2)-[:connectsTo]->(segment3:Segment)
						for (final TrackElement element3 : segment2.getConnectsTo()) {
							if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element3)) {
								continue;
							}
							final Segment segment3 = (Segment) element3;

							// (segment3:Segment)-[:monitoredBy]->(sensor)
							if (!segment3.getMonitoredBy().contains(sensor)) {
								continue;
							}

							// (segment3)-[:connectsTo]->(segment4:Segment)
							for (final TrackElement element4 : segment3.getConnectsTo()) {
								if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element4)) {
									continue;
								}
								final Segment segment4 = (Segment) element4;

								// (segment4:Segment)-[:monitoredBy]->(sensor)
								if (!segment4.getMonitoredBy().contains(sensor)) {
									continue;
								}

								// (segment4)-[:connectsTo]->(segment5:Segment)
								for (final TrackElement element5 : segment4.getConnectsTo()) {
									if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element5)) {
										continue;
									}
									final Segment segment5 = (Segment) element5;

									// (segment5:Segment)-[:monitoredBy]->(sensor)
									if (!segment5.getMonitoredBy().contains(sensor)) {
										continue;
									}

									// (segment5)-[:connectsTo]->(segment6:Segment)
									for (final TrackElement element6 : segment5.getConnectsTo()) {
										if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element6)) {
											continue;
										}
										final Segment segment6 = (Segment) element6;

										// (segment6:Segment)-[:monitoredBy]->(sensor)
										if (!segment6.getMonitoredBy().contains(sensor)) {
											continue;
										}

										final EmfConnectedSegmentsMatch csm = new EmfConnectedSegmentsMatch(sensor, segment1, segment2,
												segment3, segment4, segment5, segment6);
										matches.add(csm);
									}
								}
							}
						}
					}
				}
			}
		}
		return matches;
	}

}
