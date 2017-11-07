package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public abstract class CypherListOperationHandler<OperationType extends Operation<List<OperationResult>>, OperationResult, QueryStore>
	implements OperationHandler<OperationType, CypherDriverConnectionStore<QueryStore>> {

	@Override
	public void executeOperation(OperationType operation, CypherDriverConnectionStore<QueryStore> state,
			ResultReporter resultReporter) throws DbException {
//		Session session = state.getSession();
//		List<OperationResult> results = new ArrayList<OperationResult>();
//		int resultCount = 0;
//		results.clear();
//
//		final String queryString = getQueryString(state, operation);
//		state.logQuery(operation.getClass().getSimpleName(), queryString);
//		final StatementResult result = session.run(queryString);
//		while (result.hasNext()) {
//			final Record record = result.next();
//
//			resultCount++;
//
//			OperationResult tuple = convertSingleResult(result);
//			if (state.isPrintResults())
//				System.out.println(tuple.toString());
//			results.add(tuple);
//		}
//		session.close();
//		resultReporter.report(resultCount, results, operation);
	}

	public abstract String getQueryString(CypherDriverConnectionStore<QueryStore> state, OperationType operation);
	public abstract OperationResult convertSingleResult(Object[] tuple) throws SQLException, ParseException;
}
