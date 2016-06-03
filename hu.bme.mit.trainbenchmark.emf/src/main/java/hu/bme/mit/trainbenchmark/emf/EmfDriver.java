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
package hu.bme.mit.trainbenchmark.emf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import hu.bme.mit.trainbenchmark.benchmark.driver.Driver;
import hu.bme.mit.trainbenchmark.railway.RailwayContainer;
import hu.bme.mit.trainbenchmark.railway.RailwayElement;
import hu.bme.mit.trainbenchmark.railway.RailwayPackage;

public class EmfDriver extends Driver<RailwayElement> {

	protected RailwayContainer container;
	protected Resource resource;
	protected final ResourceSet resourceSet = new ResourceSetImpl();
	private final Comparator<RailwayElement> elementComparator = new RailwayElementComparator();

	public EmfDriver() {
		super();
		EmfUtil.registerExtension();
	}

	@Override
	public void read(final String modelPathWithoutExtension) throws Exception {
		RailwayPackage.eINSTANCE.eClass();
		final String modelPath = modelPathWithoutExtension + getPostfix();

		final URI resourceURI = URI.createFileURI(modelPath);
		resource = resourceSet.getResource(resourceURI, true);

		if (resource.getContents().size() > 0 && resource.getContents().get(0) instanceof RailwayContainer) {
			container = (RailwayContainer) resource.getContents().get(0);
		}
	}

	@Override
	public String getPostfix() {
		return "." + EmfConstants.RAILWAY_EXTENSION;
	}

	@Override
	public Comparator<RailwayElement> getElementComparator() {
		return elementComparator;
	}

	// read

	@Override
	public Collection<RailwayElement> collectVertices(final String type) throws Exception {
		final Collection<RailwayElement> vertices = new ArrayList<>();

		RailwayPackage.eINSTANCE.eClass();
		final EClass clazz = (EClass) RailwayPackage.eINSTANCE.getEClassifier(type);

		final TreeIterator<EObject> contents = container.eAllContents();
		while (contents.hasNext()) {
			final EObject eObject = contents.next();

			// if t's type is a descendant of clazz
			if (clazz.isSuperTypeOf(eObject.eClass())) {
				vertices.add((RailwayElement) eObject);
			}
		}

		return vertices;
	}

	// utility

	public RailwayContainer getContainer() {
		return container;
	}

	public Resource getResource() {
		return resource;
	}

	public void persist() throws IOException {
		resource.save(null);
	}

}
