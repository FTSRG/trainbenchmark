package hu.bme.mit.trainbenchmark.benchmark.viatra.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedEMFPQuery;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedEMFQuerySpecification;
import org.eclipse.viatra.query.runtime.emf.types.EClassTransitiveInstancesKey;
import org.eclipse.viatra.query.runtime.emf.types.EStructuralFeatureInstancesKey;
import org.eclipse.viatra.query.runtime.exception.ViatraQueryException;
import org.eclipse.viatra.query.runtime.matchers.psystem.PBody;
import org.eclipse.viatra.query.runtime.matchers.psystem.PVariable;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.Equality;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.TypeConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.QueryInitializationException;
import org.eclipse.viatra.query.runtime.matchers.tuple.FlatTuple;

import com.google.common.collect.Sets;

import hu.bme.mit.trainbenchmark.benchmark.viatra.EntrySemaphoreMatch;
import hu.bme.mit.trainbenchmark.benchmark.viatra.EntrySemaphoreMatcher;

/**
 * A pattern-specific query specification that can instantiate EntrySemaphoreMatcher in a type-safe way.
 * 
 * @see EntrySemaphoreMatcher
 * @see EntrySemaphoreMatch
 * 
 */
@SuppressWarnings("all")
public final class EntrySemaphoreQuerySpecification extends BaseGeneratedEMFQuerySpecification<EntrySemaphoreMatcher> {
  private EntrySemaphoreQuerySpecification() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryException if the pattern definition could not be loaded
   * 
   */
  public static EntrySemaphoreQuerySpecification instance() throws ViatraQueryException {
    try{
    	return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
    	throw processInitializerError(err);
    }
  }
  
  @Override
  protected EntrySemaphoreMatcher instantiate(final ViatraQueryEngine engine) throws ViatraQueryException {
    return EntrySemaphoreMatcher.on(engine);
  }
  
  @Override
  public EntrySemaphoreMatch newEmptyMatch() {
    return EntrySemaphoreMatch.newEmptyMatch();
  }
  
  @Override
  public EntrySemaphoreMatch newMatch(final Object... parameters) {
    return EntrySemaphoreMatch.newMatch((hu.bme.mit.trainbenchmark.railway.Route) parameters[0], (hu.bme.mit.trainbenchmark.railway.Semaphore) parameters[1]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link EntrySemaphoreQuerySpecification} to be created 
   * 	<b>not</b> at the class load time of the outer class, 
   * 	but rather at the first call to {@link EntrySemaphoreQuerySpecification#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private final static EntrySemaphoreQuerySpecification INSTANCE = new EntrySemaphoreQuerySpecification();
    
    /**
     * Statically initializes the query specification <b>after</b> the field {@link #INSTANCE} is assigned.
     * This initialization order is required to support indirect recursion.
     * 
     * <p> The static initializer is defined using a helper field to work around limitations of the code generator.
     * 
     */
    private final static Object STATIC_INITIALIZER = ensureInitialized();
    
    public static Object ensureInitialized() {
      INSTANCE.ensureInitializedInternalSneaky();
      return null;					
    }
  }
  
  private static class GeneratedPQuery extends BaseGeneratedEMFPQuery {
    private final static EntrySemaphoreQuerySpecification.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    @Override
    public String getFullyQualifiedName() {
      return "hu.bme.mit.trainbenchmark.benchmark.viatra.entrySemaphore";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("route","semaphore");
    }
    
    @Override
    public List<PParameter> getParameters() {
      return Arrays.asList(
      			 new PParameter("route", "hu.bme.mit.trainbenchmark.railway.Route", null),
      			 new PParameter("semaphore", "hu.bme.mit.trainbenchmark.railway.Semaphore", null)
      			);
    }
    
    @Override
    public Set<PBody> doGetContainedBodies() throws QueryInitializationException {
      Set<PBody> bodies = Sets.newLinkedHashSet();
      try {
      	{
      		PBody body = new PBody(this);
      		PVariable var_route = body.getOrCreateVariableByName("route");
      		PVariable var_semaphore = body.getOrCreateVariableByName("semaphore");
      		body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
      		   new ExportedParameter(body, var_route, "route"),
      		   new ExportedParameter(body, var_semaphore, "semaphore")
      		));
      		// 	Route.entry(route, semaphore)
      		new TypeConstraint(body, new FlatTuple(var_route), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("http://www.semanticweb.org/ontologies/2015/trainbenchmark", "Route")));
      		PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
      		new TypeConstraint(body, new FlatTuple(var_route, var__virtual_0_), new EStructuralFeatureInstancesKey(getFeatureLiteral("http://www.semanticweb.org/ontologies/2015/trainbenchmark", "Route", "entry")));
      		new Equality(body, var__virtual_0_, var_semaphore);
      		bodies.add(body);
      	}
      	// to silence compiler error
      	if (false) throw new ViatraQueryException("Never", "happens");
      } catch (ViatraQueryException ex) {
      	throw processDependencyException(ex);
      }
      return bodies;
    }
  }
}
