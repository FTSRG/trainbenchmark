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

package hu.bme.mit.trainbenchmark.benchmark.analyzer;

import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.driver.Driver;
import hu.bme.mit.trainbenchmark.benchmark.publisher.TrainBenchmarkCaseDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import eu.mondo.sam.core.metrics.BenchmarkMetric;
import eu.mondo.sam.core.metrics.CompositeMetric;
import eu.mondo.sam.core.metrics.ScalarMetric;

public abstract class ModelDescription<D extends Driver<?>> extends Analyzer<D> {

	/**
	 * The first key is the type of the objects. Every one of the them contains another Map that includes
	 * the degrees of the nodes and their distributions.
	 */
	protected Map<String, Map<Integer, Integer>> degreeDistributions;

	protected Map<String, Integer> numberOfElements;

	protected Map<String, List<String>> stationsOfSchedules;

	protected int repetitiveSchedules;

	protected String ALL = "All";

	public ModelDescription(D driver) {
		super(driver);
	}

	@Override
	public void initializeMetrics() {
		degreeDistributions = new HashMap<>();
		numberOfElements = new HashMap<>();
		stationsOfSchedules = new HashMap<>();
		repetitiveSchedules = 0;
		if (metrics == null) {
			metrics = new ArrayList<BenchmarkMetric>();
		}

	}

	@Override
	public void resetMetrics() {
		degreeDistributions.clear();

	}

	@Override
	public void calculateAll(final BenchmarkConfig benchmarkConfig,
			final TrainBenchmarkCaseDescriptor descriptor) {
		calculateMetrics();
		for (String type : degreeDistributions.keySet()) {
			CompositeMetric compositeMetric = new CompositeMetric(type);
			for (Entry<Integer, Integer> entry : degreeDistributions.get(type).entrySet()) {
				ScalarMetric scalarMetric = new ScalarMetric(entry.getKey().toString());
				scalarMetric.setValue(entry.getValue());
				compositeMetric.addMetric(scalarMetric);
			}
			metrics.add(compositeMetric);
		}
		for (String key : numberOfElements.keySet()) {
			ScalarMetric metric = new ScalarMetric(key + "-elements");
			metric.setValue(numberOfElements.get(key));
			metrics.add(metric);
		}
		ScalarMetric repetitive = new ScalarMetric("Repetitive");
		repetitive.setValue(repetitiveSchedules);
		metrics.add(repetitive);
	}

	protected void addDegree(final String type, final int degree) {
		if (!degreeDistributions.containsKey(type)) {
			degreeDistributions.put(type, new HashMap<Integer, Integer>());
		}
		Map<Integer, Integer> dists = degreeDistributions.get(type);
		int count = 0;

		if (dists.containsKey(degree)) {
			count = dists.get(degree);
			count++;
			dists.put(degree, count);
		} else {
			dists.put(degree, 1);
		}

		if (!type.equals(ALL)) {
			addDegree(ALL, degree);
		}

	}

	protected void addStationOfSchedule(final String schedule, final String station) {
		if (!stationsOfSchedules.containsKey(schedule)) {
			stationsOfSchedules.put(schedule, new ArrayList<String>());
		}
		stationsOfSchedules.get(schedule).add(station);
	}

	protected void checkRepetitiveSchedules(final String schedule) {
		if (stationsOfSchedules.get(schedule).size() == 0) {
			return;
		}
		if (match(schedule, stationsOfSchedules.get(schedule))) {
			repetitiveSchedules++;
			stationsOfSchedules.remove(schedule);
		}
	}

	protected boolean match(final String key, final List<String> values) {
		for (Entry<String, List<String>> entry : stationsOfSchedules.entrySet()) {
			if (!entry.getKey().equals(key)) {
				if (entry.getValue().equals(values)) {
					return true;
				}
			}
		}
		return false;
	}
}
