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

package hu.bme.mit.trainbenchmark.generator.graph.tinkerpop;

import hu.bme.mit.trainbenchmark.generator.ModelGenerator;
import hu.bme.mit.trainbenchmark.generator.ScalableGeneratorFactory;
import hu.bme.mit.trainbenchmark.generator.graph.tinkerpop.config.TinkerGraphGeneratorConfig;

public class TinkerGraphGeneratorMain {

	public static void main(final String[] args) throws Exception {
		final TinkerGraphGeneratorConfig gc = TinkerGraphGeneratorConfig.fromFile(args[0], TinkerGraphGeneratorConfig.class);
		final TinkerGraphSerializer rdfSerializer = new TinkerGraphSerializer(gc);
		final ModelGenerator generator = ScalableGeneratorFactory.createGenerator(rdfSerializer, gc);
		generator.generateModel();
	}

}
