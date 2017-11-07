package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.SnbDb;
import com.ldbc.impls.workloads.ldbc.snb.cypher.bi.CypherBiQueryStore;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

public abstract class CypherDb extends SnbDb<CypherBiQueryStore> {
	
	protected CypherDriverConnectionStore dbs;

	@Override
	protected void onClose() throws IOException {
		try {
			dbs.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected DbConnectionState getConnectionState() throws DbException {
		return dbs;
	}
	
	protected static long timestampToTimestamp(ResultSet r, int column) throws SQLException {
		return r.getTimestamp(column, Calendar.getInstance(TimeZone.getTimeZone("GMT"))).getTime();
	}

}
