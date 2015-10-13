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
package hu.bme.mit.trainbenchmark.benchmark.emfincquery.checker;

import java.util.Collection;

import org.eclipse.incquery.runtime.api.IncQueryMatcher;
import org.eclipse.incquery.runtime.api.impl.BasePatternMatch;
import org.eclipse.incquery.runtime.exception.IncQueryException;

import hu.bme.mit.trainbenchmark.benchmark.checker.Checker;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.config.EMFIncQueryBenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.driver.EMFIncQueryBaseDriver;
import hu.bme.mit.trainbenchmark.constants.Query;

public abstract class EMFIncQueryChecker<TMatch extends BasePatternMatch> extends Checker<TMatch> {

	protected Collection<TMatch> matches;
	protected final EMFIncQueryBaseDriver<TMatch, EMFIncQueryBenchmarkConfig> driver;
	protected final EMFIncQueryBenchmarkConfig benchmarkConfig;

	protected EMFIncQueryChecker(final EMFIncQueryBenchmarkConfig benchmarkConfig,
			final EMFIncQueryBaseDriver<TMatch, EMFIncQueryBenchmarkConfig> driver) {
		this.benchmarkConfig = benchmarkConfig;
		this.driver = driver;
	}

	public static EMFIncQueryChecker<?> newInstance(final EMFIncQueryBenchmarkConfig benchmarkConfig, final EMFIncQueryBaseDriver driver,
			final Query query) {
		switch (query) {
		case CONNECTEDSEGMENTS:
			return new EMFIncQueryConnectedSegmentsChecker(benchmarkConfig, driver);
		case POSLENGTH:
			return new EMFIncQueryPosLengthChecker(benchmarkConfig, driver);
		case ROUTESENSOR:
			return new EMFIncQueryRouteSensorChecker(benchmarkConfig, driver);
		case SEMAPHORENEIGHBOR:
			return new EMFIncQuerySemaphoreNeighborChecker(benchmarkConfig, driver);
		case SWITCHSENSOR:
			return new EMFIncQuerySwitchSensorChecker(benchmarkConfig, driver);
		case SWITCHSET:
			return new EMFIncQuerySwitchSetChecker(benchmarkConfig, driver);
		default:
			throw new UnsupportedOperationException("Query " + query + " not supported");
		}
	}

	@Override
	public Collection<TMatch> check() throws IncQueryException {
		if (benchmarkConfig.isLocalSearch()) {
			matches = getMatcher().getAllMatches();
		}
		return matches;
	}

	public void setMatches(final Collection<TMatch> matches) {
		this.matches = matches;
	}

	public abstract IncQueryMatcher<TMatch> getMatcher() throws IncQueryException;

}
