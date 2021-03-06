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
package hu.bme.mit.trainbenchmark.benchmark.jena.transformations.inject;

import static hu.bme.mit.trainbenchmark.constants.ModelConstants.LENGTH;
import static hu.bme.mit.trainbenchmark.rdf.RdfConstants.BASE_PREFIX;

import java.io.IOException;
import java.util.Collection;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import hu.bme.mit.trainbenchmark.benchmark.jena.driver.JenaDriver;
import hu.bme.mit.trainbenchmark.benchmark.jena.matches.JenaPosLengthInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.jena.transformations.JenaTransformation;

public class JenaTransformationInjectPosLength extends JenaTransformation<JenaPosLengthInjectMatch> {

	public JenaTransformationInjectPosLength(final JenaDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<JenaPosLengthInjectMatch> matches) throws IOException {
		final Model model = driver.getModel();
		final Property lengthProperty = model.getProperty(BASE_PREFIX + LENGTH);

		for (final JenaPosLengthInjectMatch match : matches) {
			final Resource segment = match.getSegment();
			final Selector selector = new SimpleSelector(segment, lengthProperty, (RDFNode) null);
			final StmtIterator oldStatements = model.listStatements(selector);
			if (!oldStatements.hasNext()) {
				continue;

			}
			final Statement oldStatement = oldStatements.next();
			model.remove(oldStatement);
			final Statement newStatement = model.createLiteralStatement(segment, lengthProperty, 0);
			model.add(newStatement);
		}
	}

}
