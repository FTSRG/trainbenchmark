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

package hu.bme.mit.trainbenchmark.generator.cypher;

import hu.bme.mit.trainbenchmark.generator.ModelSerializer;
import hu.bme.mit.trainbenchmark.generator.cypher.config.CypherGeneratorConfig;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import static hu.bme.mit.trainbenchmark.constants.ModelConstants.CONNECTS_TO;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.ELEMENTS;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.FOLLOWS;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.ID;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.MONITORED_BY;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.REQUIRES;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SEMAPHORE;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SEMAPHORES;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SENSOR;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SENSORS;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SUPERTYPES;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SWITCHPOSITION;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.TRACKELEMENT;

public class CypherSerializer extends ModelSerializer<CypherGeneratorConfig> {

	public CypherSerializer(final CypherGeneratorConfig gc) {
		super(gc);
	}

	@Override
	public String syntax() {
		return "Cypher";
	}

	@Override
	public void initModel() throws IOException {

	}

	@Override
	public void persistModel() throws IOException, InterruptedException {

	}

	private void write(String format) {

	}

	@Override
	public Object createVertex(final int id, final String type, final Map<String, ? extends Object> attributes, final Map<String, Object> outgoingEdges,
			final Map<String, Object> incomingEdges) throws IOException {
		final StringBuilder columns = new StringBuilder();
		final StringBuilder values = new StringBuilder();

		columns.append("\"" + ID + "\"");
		values.append(id);

		structuralFeaturesToSQL(attributes, columns, values);
		structuralFeaturesToSQL(outgoingEdges, columns, values);
		structuralFeaturesToSQL(incomingEdges, columns, values);

		if (SUPERTYPES.containsKey(type)) {
			final String ancestorType = SUPERTYPES.get(type);
			write(String.format("INSERT INTO \"%s\" (\"%s\") VALUES (%s);", ancestorType, ID, id));
			write(String.format("INSERT INTO \"%s\" (%s) VALUES (%s);", type, columns.toString(), values.toString()));
		} else {
			final String insertQuery = String.format("INSERT INTO \"%s\" (%s) VALUES (%s);", type, columns.toString(), values.toString());
			write(insertQuery.toString());
		}

		return id;
	}

	@Override
	public void createEdge(final String label, final Object from, final Object to) throws IOException {
		if (from == null || to == null) {
			return;
		}

		String insertQuery;
		switch (label) {
		// n:m edges
		case MONITORED_BY:
		case CONNECTS_TO:
		case REQUIRES:
			insertQuery = String.format("INSERT INTO \"%s\" VALUES (%s, %s);", label, from, to);
			break;
		// n:1 edges
		case FOLLOWS:
			insertQuery = String.format("UPDATE \"%s\" SET \"%s\" = %s WHERE \"%s\" = %s;", SWITCHPOSITION, "route", from, ID, to);
			break;
		case SENSORS:
			insertQuery = String.format("UPDATE \"%s\" SET \"%s\" = %s WHERE \"%s\" = %s;", SENSOR, "region", from, ID, to);
			break;
		case ELEMENTS:
			insertQuery = String.format("UPDATE \"%s\" SET \"%s\" = %s WHERE \"%s\" = %s;", TRACKELEMENT, "region", from, ID, to);
			break;
		case SEMAPHORES:
			insertQuery = String.format("UPDATE \"%s\" SET \"%s\" = %s WHERE \"%s\" = %s;", SEMAPHORE, "segment", from, ID, to);
			break;
		default:
			throw new UnsupportedOperationException("Label '" + label + "' not supported.");
		}

		write(insertQuery);
	}

	@Override
	public void setAttribute(final String type, final Object node, final String key, final Object value) throws IOException {
		final String stringValue = valueToString(value);
		final String updateQuery = String.format("UPDATE \"%s\" SET \"%s\" = %s WHERE \"%s\" = %s;", type, key, stringValue, ID, node);
		write(updateQuery);
	}

	protected void structuralFeaturesToSQL(final Map<String, ? extends Object> attributes, final StringBuilder columns, final StringBuilder values) {
		for (final Entry<String, ? extends Object> entry : attributes.entrySet()) {
			final String key = entry.getKey();
			final Object value = entry.getValue();

			columns.append(", \"" + key + "\"");
			values.append(", ");

			final String stringValue = (value == null ? "NULL" : valueToString(value));
			values.append(stringValue);
		}
	}

	private String valueToString(final Object value) {
		String stringValue;
		if (value instanceof Boolean) {
			stringValue = (Boolean) value ? "1" : "0";
		} else if (value instanceof String) {
			// escape string
			stringValue = "\"" + value + "\"";
		} else if (value instanceof Enum) {
			// change enum to ordinal value
			final Enum<?> enumeration = (Enum<?>) value;
			stringValue = Integer.toString(enumeration.ordinal());
		} else {
			stringValue = value.toString();
		}
		return stringValue;
	}

}