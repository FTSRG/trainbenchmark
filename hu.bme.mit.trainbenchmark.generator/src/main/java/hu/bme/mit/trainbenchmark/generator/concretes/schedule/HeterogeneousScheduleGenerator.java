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

package hu.bme.mit.trainbenchmark.generator.concretes.schedule;

import hu.bme.mit.trainbenchmark.generator.FormatGenerator;
import hu.bme.mit.trainbenchmark.generator.config.GeneratorConfig;

import java.io.IOException;

public abstract class HeterogeneousScheduleGenerator extends ScheduleGenerator {

	public HeterogeneousScheduleGenerator(FormatGenerator formatGenerator, GeneratorConfig generatorConfig) {
		super(formatGenerator, generatorConfig);
	}

	@Override
	protected void generateModel() throws IOException {
		printMessage();
		initializationStep();
		while (currentNodes < maxNumberOfSchedules) {
			generateStations();
			generateSchedules();
		}
		transformStationConnections();
		attachTrains();
		stations.clear();
		schedules.clear();
		System.out.println("Done!");
	}

}