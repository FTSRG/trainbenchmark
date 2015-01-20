package hu.bme.mit.trainbenchmark.benchmark.emfincquery;

import hu.bme.mit.trainbenchmark.benchmark.emfincquery.HeadMatcher;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.RouteSensorMatcher;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.util.HeadQuerySpecification;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.util.RouteSensorQuerySpecification;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.api.impl.BaseGeneratedPatternGroup;
import org.eclipse.incquery.runtime.exception.IncQueryException;

/**
 * A pattern group formed of all patterns defined in routeSensor.eiq.
 * 
 * <p>Use the static instance as any {@link org.eclipse.incquery.runtime.api.IPatternGroup}, to conveniently prepare
 * an EMF-IncQuery engine for matching all patterns originally defined in file routeSensor.eiq,
 * in order to achieve better performance than one-by-one on-demand matcher initialization.
 * 
 * <p> From package hu.bme.mit.trainbenchmark.benchmark.emfincquery, the group contains the definition of the following patterns: <ul>
 * <li>routeSensor</li>
 * <li>head</li>
 * </ul>
 * 
 * @see IPatternGroup
 * 
 */
@SuppressWarnings("all")
public final class RouteSensor extends BaseGeneratedPatternGroup {
  /**
   * Access the pattern group.
   * 
   * @return the singleton instance of the group
   * @throws IncQueryException if there was an error loading the generated code of pattern specifications
   * 
   */
  public static RouteSensor instance() throws IncQueryException {
    if (INSTANCE == null) {
    	INSTANCE = new RouteSensor();
    }
    return INSTANCE;
    
  }
  
  private static RouteSensor INSTANCE;
  
  private RouteSensor() throws IncQueryException {
    querySpecifications.add(RouteSensorQuerySpecification.instance());
    querySpecifications.add(HeadQuerySpecification.instance());
    
  }
  
  public RouteSensorQuerySpecification getRouteSensor() throws IncQueryException {
    return RouteSensorQuerySpecification.instance();
  }
  
  public RouteSensorMatcher getRouteSensor(final IncQueryEngine engine) throws IncQueryException {
    return RouteSensorMatcher.on(engine);
  }
  
  public HeadQuerySpecification getHead() throws IncQueryException {
    return HeadQuerySpecification.instance();
  }
  
  public HeadMatcher getHead(final IncQueryEngine engine) throws IncQueryException {
    return HeadMatcher.on(engine);
  }
}
