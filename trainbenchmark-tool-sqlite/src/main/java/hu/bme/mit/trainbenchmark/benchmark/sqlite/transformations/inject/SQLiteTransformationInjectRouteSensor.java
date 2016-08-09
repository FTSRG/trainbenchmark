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
package hu.bme.mit.trainbenchmark.benchmark.sqlite.transformations.inject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import hu.bme.mit.trainbenchmark.benchmark.sql.matches.SqlRouteSensorInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.sqlite.driver.SQLiteDriver;
import hu.bme.mit.trainbenchmark.benchmark.sqlite.transformation.SQLiteTransformation;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;
import hu.bme.mit.trainbenchmark.constants.Scenario;

public class SQLiteTransformationInjectRouteSensor extends SQLiteTransformation<SqlRouteSensorInjectMatch> {

	final String setBindings = "INSERT OR REPLACE INTO Variables VALUES ('segment1', ?);";
	
	public SQLiteTransformationInjectRouteSensor(final SQLiteDriver driver, final Optional<String> workspaceDir) throws IOException {
		super(driver, workspaceDir, RailwayQuery.ROUTESENSOR, Scenario.INJECT);
	}

	@Override
	public void activate(final Collection<SqlRouteSensorInjectMatch> elements) throws SQLException {
//		if (preparedUpdateStatement == null) {
//			preparedUpdateStatement = driver.getConnection().prepareStatement(setBindings);
//		}
//
//		for (final Long element : elements) {
//			preparedUpdateStatement.setLong(1, element);
//			preparedUpdateStatement.executeUpdate();
//
//			driver.getConnection().createStatement().executeUpdate(updateQuery);
//		}
	}
	
}
