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
package hu.bme.mit.trainbenchmark.benchmark.iqdcore.transformations.repair;

import static hu.bme.mit.trainbenchmark.constants.ModelConstants.MONITORED_BY;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SENSOR;

import java.util.Collection;

import hu.bme.mit.incqueryds.WildcardInput.Transaction;
import hu.bme.mit.trainbenchmark.benchmark.iqdcore.driver.IQDCoreDriver;
import hu.bme.mit.trainbenchmark.benchmark.iqdcore.match.IQDCoreSwitchSensorMatch;

public class IQDCoreTransformationRepairSwitchSensor extends IQDCoreTransformationRepair<IQDCoreSwitchSensorMatch> {

	public IQDCoreTransformationRepairSwitchSensor(final IQDCoreDriver driver) {
		super(driver);
	}

	@Override
	public void performRHS(final Collection<IQDCoreSwitchSensorMatch> matches) throws Exception {
		final Transaction transaction = driver.newTransaction();
		for (final IQDCoreSwitchSensorMatch match : matches) {
			final long sw = match.getSw();
			final long sensorID = driver.newKey();
			transaction.add(sensorID, "type", SENSOR);
			transaction.add(sw, MONITORED_BY, sensorID);
		}
		transaction.close();
	}
}