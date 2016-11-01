package hu.bme.mit.trainbenchmark.benchmark.test.queryspecific;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;

import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfigBase;
import hu.bme.mit.trainbenchmark.benchmark.runcomponents.BenchmarkResult;
import hu.bme.mit.trainbenchmark.constants.RailwayOperation;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

public abstract class RouteSensorTest extends QueryTest {

	@Test
	public void testRouteSensor() throws Exception {
		final String xms = "1G";
		final String xmx = "1G";
		final long timeout = 120;
		final int runs = 2;
		final int queryTransformationCount = 1;
		final String modelFilename = "railway-repair-1";
		final List<RailwayOperation> operations = ImmutableList.of(//
				RailwayOperation.ROUTESENSOR_REPAIR //
		);
		final String workload = "RouteSensorTest";
		final BenchmarkConfigBase bcb = new BenchmarkConfigBase(xms, xmx, timeout, runs, queryTransformationCount, modelFilename, operations,
				workload);

		final BenchmarkResult result = runTest(bcb);
		System.out.println(result);
		System.out.println(result.csvMatches());
		System.out.println(result.csvTimes());

		final ListMultimap<RailwayQuery, Integer> allMatches = result.getLastRunResult().getMatches();
		collector.checkThat(allMatches.get(RailwayQuery.ROUTESENSOR).get(0), Matchers.equalTo(18));
		System.out.println("check 1");
		collector.checkThat(allMatches.get(RailwayQuery.ROUTESENSOR).get(1), Matchers.equalTo(8));
		System.out.println("check 2");
	}

}
