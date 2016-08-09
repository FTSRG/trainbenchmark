package hu.bme.mit.trainbenchmark.generator.scalable;

import static hu.bme.mit.trainbenchmark.constants.ModelConstants.CONNECTS_TO;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.CURRENTPOSITION;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.ELEMENTS;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.ENTRY;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.EXIT;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.FOLLOWS;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.GATHERS;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.LENGTH;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.MONITORED_BY;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.POSITION;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.REGION;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.ROUTE;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SEGMENT;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SEMAPHORE;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SEMAPHORES;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SENSOR;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SENSORS;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SIGNAL;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SWITCH;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SWITCHPOSITION;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.TARGET;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

import hu.bme.mit.trainbenchmark.constants.Position;
import hu.bme.mit.trainbenchmark.constants.Signal;
import hu.bme.mit.trainbenchmark.constants.TrainBenchmarkConstants;
import hu.bme.mit.trainbenchmark.generator.ModelGenerator;
import hu.bme.mit.trainbenchmark.generator.ModelSerializer;
import hu.bme.mit.trainbenchmark.generator.config.GeneratorConfigWrapper;

public class ScalableModelGenerator extends ModelGenerator {

	private static final Map<String, Object> EMPTY_MAP = Collections.<String, Object> emptyMap();

	public static final int MAX_SEGMENT_LENGTH = 1000;

	protected int maxSegments = 5;
	protected int maxRoutes;
	protected int maxSwitchPositions = 20;
	protected int maxSensors = 10;

	protected int posLengthErrorPercent = 0;
	protected int switchSensorErrorPercent = 0;
	protected int routeSensorErrorPercent = 0;
	protected int semaphoreNeighborErrorPercent = 0;
	protected int switchSetErrorPercent = 0;
	protected int connectedSegmentsErrorPercent = 0;

	protected Random random = new Random(TrainBenchmarkConstants.RANDOM_SEED);

	public ScalableModelGenerator(final ModelSerializer<?> serializer, final GeneratorConfigWrapper generatorConfigWrapper) {
		super(serializer, generatorConfigWrapper);
		maxRoutes = 5 * generatorConfigWrapper.getGeneratorConfig().getSize();
		switch (generatorConfigWrapper.getGeneratorConfig().getScenario()) {
		case BATCH:
			// set all error percents to 0
			break;
		case INJECT:
			connectedSegmentsErrorPercent = 5;
			posLengthErrorPercent = 2;
			routeSensorErrorPercent = 4;
			semaphoreNeighborErrorPercent = 7;
			switchSensorErrorPercent = 2;
			switchSetErrorPercent = 8;
			break;
		case REPAIR:
			connectedSegmentsErrorPercent = 5;
			posLengthErrorPercent = 10;
			routeSensorErrorPercent = 10;
			semaphoreNeighborErrorPercent = 25;
			switchSensorErrorPercent = 18;
			switchSetErrorPercent = 15;
			break;
		default:
			throw new UnsupportedOperationException("Scenario not supported.");
		}
	}

	protected int nextRandom() {
		return random.nextInt(100);
	}

	@Override
	protected void constructModel() throws FileNotFoundException, IOException {
		Object prevSemaphore = null;
		Object firstSemaphore = null;
		List<Object> firstTracks = null;
		List<Object> prevTracks = null;
		for (long i = 0; i < maxRoutes; i++) {
			boolean firstSegment = true;

			serializer.beginTransaction();

			if (prevSemaphore == null) {
				final Map<String, Object> semaphoreAttributes = ImmutableMap.of(SIGNAL, Signal.GO);

				prevSemaphore = serializer.createVertex(SEMAPHORE, semaphoreAttributes);
				firstSemaphore = prevSemaphore;
			}

			Object semaphore;
			if (i != maxRoutes - 1) {
				final Map<String, Object> semaphoreAttributes = ImmutableMap.of(SIGNAL, Signal.GO);
				semaphore = serializer.createVertex(SEMAPHORE, semaphoreAttributes);
			} else {
				semaphore = firstSemaphore;
			}

			// the semaphoreNeighborErrorPercent
			final boolean semaphoreNeighborError1 = nextRandom() < semaphoreNeighborErrorPercent;
			final Object entry = semaphoreNeighborError1 ? null : prevSemaphore;
			final Object exit = semaphore;

			// the entry might be null, therefore we avoid using an ImmutableMap here
			final Map<String, Object> routeOutgoingEdges = new HashMap<>();
			routeOutgoingEdges.put(ENTRY, entry);
			routeOutgoingEdges.put(EXIT, exit);

			final Object route = serializer.createVertex(ROUTE, EMPTY_MAP, routeOutgoingEdges);
			final Object region = serializer.createVertex(REGION);

			final int swPs = random.nextInt(maxSwitchPositions - 1) + 1;
			final List<Object> currentTrack = new ArrayList<>();
			final Set<Object> switches = new HashSet<>();
			for (int j = 0; j < swPs; j++) {
				final Object sw = serializer.createVertex(SWITCH);
				currentTrack.add(sw);
				switches.add(sw);

				// (region)-[:elements]->(sw)
				serializer.createEdge(ELEMENTS, region, sw);

				final int sensors = random.nextInt(maxSensors - 1) + 1;

				for (int k = 0; k < sensors; k++) {
					final Object sensor = serializer.createVertex(SENSOR);
					serializer.createEdge(SENSORS, region, sensor);

					// add "monitored by" edge from switch to sensor
					final boolean switchSensorError = nextRandom() < switchSensorErrorPercent;
					if (!switchSensorError) {
						serializer.createEdge(MONITORED_BY, sw, sensor);

						// add "gathers" edge from route to sensor
						final boolean routeSensorError = nextRandom() < routeSensorErrorPercent;
						if (!routeSensorError) {
							serializer.createEdge(GATHERS, route, sensor);
						}
					}

					// generate segments
					for (int m = 0; m < maxSegments; m++) {
						final Object segment = createSegment(currentTrack, sensor, region);

						if (firstSegment) {
							serializer.createEdge(SEMAPHORES, segment, semaphore);
							firstSegment = false;
						}
					}

					// create another extra segment
					if (nextRandom() < connectedSegmentsErrorPercent) {
						createSegment(currentTrack, sensor, region);
					}
				}

				final int numberOfPositions = Position.values().length;
				final int positionOrdinal = random.nextInt(numberOfPositions);
				final Position position = Position.values()[positionOrdinal];
				serializer.setAttribute(SWITCH, sw, CURRENTPOSITION, position);

				// the errorInjectedState may contain a bad value
				final boolean switchSetError = nextRandom() < switchSetErrorPercent;
				final int invalidPositionOrdinal = switchSetError ? (numberOfPositions - 1) - positionOrdinal : positionOrdinal;
				final Position invalidPosition = Position.values()[invalidPositionOrdinal];

				final Map<String, Object> swPAttributes = ImmutableMap.of(POSITION, invalidPosition);
				final Map<String, Object> swPOutgoingEdges = ImmutableMap.of(TARGET, sw);
				final Object swP = serializer.createVertex(SWITCHPOSITION, swPAttributes, swPOutgoingEdges);

				// (route)-[:follows]->(swP)
				serializer.createEdge(FOLLOWS, route, swP);
			}

			final Set<Integer> usedTracks = new HashSet<>();
			// create connectsTo (n:m) edges
			for (int j = 1; j < currentTrack.size(); ++j) {
				final Object current = currentTrack.get(j);
				if (usedTracks.contains(current))
					continue;

				serializer.createEdge(CONNECTS_TO, currentTrack.get(j - 1), current);

				if (switches.contains(current)) {
					final int segmentID = j + random.nextInt(currentTrack.size() - j);
					if (!usedTracks.contains(segmentID)) {
						// TODO check why this causes double edges
						// serializer.createEdge(CONNECTS_TO, current, currentTrack.get(segmentID));
						usedTracks.add(segmentID);
					}
				}
			}

			if (prevTracks != null && prevTracks.size() > 0 && currentTrack.size() > 0) {
				serializer.createEdge(CONNECTS_TO, prevTracks.get(prevTracks.size() - 1), currentTrack.get(0));
			}

			// loop the last track element of the last route to the first track element of the first route
			if (i == maxRoutes - 1) {
				if (currentTrack != null && currentTrack.size() > 0 && firstTracks.size() > 0) {
					serializer.createEdge(CONNECTS_TO, currentTrack.get(currentTrack.size() - 1), firstTracks.get(0));
				}
			}

			if (prevTracks == null) {
				firstTracks = currentTrack;
			}

			prevTracks = currentTrack;
			prevSemaphore = semaphore;

			serializer.endTransaction();
		}
	}

	private Object createSegment(final List<Object> currTracks, final Object sensor, final Object region) throws IOException {
		final boolean posLengthError = nextRandom() < posLengthErrorPercent;
		final int segmentLength = ((posLengthError ? -1 : 1) * random.nextInt(MAX_SEGMENT_LENGTH)) + 1;

		final Map<String, Object> segmentAttributes = ImmutableMap.of(LENGTH, segmentLength);
		final Object segment = serializer.createVertex(SEGMENT, segmentAttributes);

		// (region)-[:elements]->(segment)
		serializer.createEdge(ELEMENTS, region, segment);

		// (segment)-[:monitoredBy]->(sensor) monitoredBy n:m edge
		serializer.createEdge(MONITORED_BY, segment, sensor);
		currTracks.add(segment);
		return segment;
	}

}