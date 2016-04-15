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

package hu.bme.mit.trainbenchmark.benchmark.tinkergraph.test;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import hu.bme.mit.trainbenchmark.benchmark.test.InjectTest;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.config.TinkerGraphEngine;

public class TinkerGraphInjectTest extends InjectTest {

	@Parameters
	public static Collection<Object[]> data() {
	    return TinkerGraphBenchmarkInitializer.getTestParameters();
	}
	
	public TinkerGraphInjectTest(final TinkerGraphEngine engine) {
		bi = new TinkerGraphBenchmarkInitializer(engine);
	}

}
