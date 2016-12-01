## Getting started

The framework provides a set of scripts for building the projects, generating the instance models and running the benchmark.

### Installation guide

The benchmark requires a 64-bit operating system. We recommend Ubuntu-based Linux systems.

### Setup

* Initialization
    * [`install-jdk.sh`](scripts/install-jdk.sh): installs [Oracle JDK 8](https://github.com/FTSRG/cheat-sheets/wiki/Linux-packages#oracle-jdk)
    * [`./gradlew initScripts`](trainbenchmark-scripts/build.gradle): intializes the Groovy scripts for the [generate](trainbenchmark-scripts/src-template/GeneratorScript.groovy) and the [benchmark](trainbenchmark-scripts/src-template/BenchmarkScript.groovy) goals.

Provided that you start with a fresh Ubuntu server installation, you can run the provided install scripts like this:

```bash
scripts/install-jdk.sh && \
./gradlew initScripts
```

#### Optional dependencies

Some tools require dependencies, e.g. installing a database manager or adding artifacts to your local Maven repository

* [MySQL](hu.bme.mit.trainbenchmark.benchmark.mysql): install with

  ```
  sudo apt-get install -y mysql-server
  ```
  and set the root password to empty. This might be tricky on latest Ubuntu systems, see the [MySQL README](../trainbenchmark-tool-mysql/README.md) for details.
* [SQLite](hu.bme.mit.trainbenchmark.benchmark.sqlite): install with

  ```
  sudo apt-get install -y sqlite3
  ```

### Usage

The benchmark configuration is defined in the `trainbenchmark-scripts/src/BenchmarkScript.groovy` file.

* Use `./gradlew shadowjar generate benchmark plot page` to run the benchmark and generate the plots.

To remove **all** previous results, add the `cleanResults` task befor the other tasks.

### Importing to Eclipse

The projects are developed and tested with **Eclipse Neon**.

To develop the Train Benchmark, you need a Gradle Eclipse plugin from the **Eclipse Marketplace**, e.g. the **Buildship: Eclipse Plug-ins for Gradle**. We also recommend installing the Eclipse Groovy tooling from <https://github.com/groovy/groovy-eclipse/wiki>.

To import the projects, choose **Import...** | **Gradle Project**, specify the root directory as the repository directory and import them with the default **Gradle distribution** (**Gradle wrapper (recommended)**). If Eclipse prompts you if you would like to overwrite existing Eclipse project descriptors, choose **Keep**.
