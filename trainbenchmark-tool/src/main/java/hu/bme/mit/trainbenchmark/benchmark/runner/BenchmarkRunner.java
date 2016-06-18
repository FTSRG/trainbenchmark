package hu.bme.mit.trainbenchmark.benchmark.runner;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfigWrapper;

public class BenchmarkRunner {

	public static int run(final BenchmarkConfigWrapper bcw, final String toolName) throws IOException, InterruptedException {
		final File configFile = File.createTempFile("trainbenchmark-benchmark-", ".conf");
		final String configPath = configFile.getAbsolutePath();
		bcw.saveToFile(configPath);

		final String projectName = String.format("trainbenchmark-tool-%s", toolName);
		final String jarPath = String.format("../%s/build/libs/%s-1.0.0-SNAPSHOT-fat.jar %s", projectName,
				projectName, configPath);

		final String javaCommand = String.format("java -Xms%s -Xmx%s -server -jar %s %s",
				bcw.getBenchmarkConfig().getXms(), bcw.getBenchmarkConfig().getXmx(), jarPath, configPath);
		final CommandLine cmdLine = CommandLine.parse(javaCommand);
		final DefaultExecutor executor = new DefaultExecutor();
		final int exitValue = executor.execute(cmdLine);

		return exitValue;
	}

}