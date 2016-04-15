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

package hu.bme.mit.trainbenchmark.benchmark.tinkergraph;

import java.io.IOException;
import java.util.Comparator;

import org.neo4j.graphdb.Node;

import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.AbstractBenchmarkCase;
import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.transformations.Transformation;
import hu.bme.mit.trainbenchmark.benchmark.checker.Checker;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.checkers.TinkerGraphCoreChecker;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.checkers.TinkerGraphCypherChecker;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.config.TinkerGraphBenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.config.TinkerGraphEngine;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.driver.TinkerGraphDriver;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.matches.TinkerGraphMatch;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.matches.TinkerGraphMatchComparator;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.transformations.TinkerGraphTransformation;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

public class TinkerGraphBenchmarkCase<TinkerGraphChecker>
		extends AbstractBenchmarkCase<TinkerGraphMatch, Node, TinkerGraphDriver, TinkerGraphBenchmarkConfig, Checker<TinkerGraphMatch>> {

	@Override
	public TinkerGraphDriver createDriver(final TinkerGraphBenchmarkConfig benchmarkConfig) throws Exception {
		return new TinkerGraphDriver(benchmarkConfig.getWorkspacePath());
	}
	
	@Override
	public Checker<TinkerGraphMatch> createChecker(final TinkerGraphBenchmarkConfig benchmarkConfig, final TinkerGraphDriver driver, final RailwayQuery query)
			throws Exception {		
		final TinkerGraphEngine engine = benchmarkConfig.getEngine();
		switch (engine) {	
		case COREAPI:
			return TinkerGraphCoreChecker.newInstance(driver, query);
		case CYPHER:
			return TinkerGraphCypherChecker.newInstance(driver, benchmarkConfig, query);
		default:
			throw new UnsupportedOperationException("Engine not supported: " + engine);
		}
	}

	@Override
	public Comparator<?> createMatchComparator() {
		return new TinkerGraphMatchComparator();
	}

	@Override
	public Transformation<?, ?> createTransformation(final TinkerGraphBenchmarkConfig benchmarkConfig, final TinkerGraphDriver driver, final RailwayQuery query)
			throws IOException {
		return TinkerGraphTransformation.newInstance(driver, query, benchmarkConfig.getScenario());
	}

}
