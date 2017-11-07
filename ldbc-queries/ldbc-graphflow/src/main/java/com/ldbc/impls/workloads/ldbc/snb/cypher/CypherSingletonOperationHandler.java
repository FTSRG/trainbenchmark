package com.ldbc.impls.workloads.ldbc.snb.cypher;

import ca.waterloo.dsg.graphflow.query.result.QueryResult;
import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CypherSingletonOperationHandler<OperationType extends Operation<OperationResult>, OperationResult, QueryStore>
implements OperationHandler<OperationType, CypherDriverConnectionStore<QueryStore>> {

@Override
public void executeOperation(OperationType operation, CypherDriverConnectionStore<QueryStore> state,
		ResultReporter resultReporter) throws DbException {
	Session session = state.getSession();
	OperationResult tuple = null;
	int resultCount = 0;

	final String queryString = getQueryString(state, operation);
	state.logQuery(operation.getClass().getSimpleName(), queryString);
	final StatementResult result = session.run(queryString);
	if (result.hasNext()) {
		final Record record = result.next();
		resultCount++;

//		tuple = convertSingleResult(result);
//		if (state.isPrintResults())
//			System.out.println(tuple.toString());
		if (state.isPrintResults()) {
			System.out.println(record);
		}
	}
	session.close();

	resultReporter.report(resultCount, tuple, operation);
}

public abstract String getQueryString(CypherDriverConnectionStore<QueryStore> state, OperationType operation);
public abstract OperationResult convertSingleResult(QueryResult result) throws SQLException;
}
