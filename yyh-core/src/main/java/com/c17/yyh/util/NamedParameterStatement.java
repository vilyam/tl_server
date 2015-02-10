package com.c17.yyh.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * This class wraps around a {@link PreparedStatement} and allows the programmer
 * to set parameters by name instead
 * 
 * of by index. This eliminates any confusion as to which parameter index
 * represents what. This also means that
 * 
 * rearranging the SQL statement or adding a parameter doesn't involve
 * renumbering your indices.
 * 
 * Code such as this:
 * 
 *
 * 
 * Connection con = getConnection();
 * 
 * String query = "select * from my_table where name = ? or address = ?";
 * 
 * PreparedStatement p = con.prepareStatement(query);
 * 
 * p.setString(1, "bob");
 * 
 * p.setString(2, "123 terrace ct");
 * 
 * ResultSet rs = p.executeQuery();
 * 
 *
 * 
 * can be replaced with:
 * 
 *
 * 
 * Connection con = getConnection();
 * 
 * String query =
 * "select * from my_table where name = :name or address = :address";
 * 
 * NamedParameterStatement p = new NamedParameterStatement(con, query);
 * 
 * p.setString("name", "bob");
 * 
 * p.setString("address", "123 terrace ct");
 * 
 * ResultSet rs = p.executeQuery();
 * 
 *
 * 
 * @author adam_crume
 */

public class NamedParameterStatement implements Statement{

    /** The statement this object is wrapping. */
    private final PreparedStatement          statement;

    /** Maps parameter names to arrays of ints which are the parameter indices. */
    private final Map<String, List<Integer>> indexMap;

    /**
     * 
     * Creates a NamedParameterStatement. Wraps a call to
     * 
     * c.{@link Connection#prepareStatement(java.lang.String) prepareStatement}.
     * 
     * @param connection
     *            the database connection
     * 
     * @param query
     *            the parameterized query
     * 
     * @throws SQLException
     *             if the statement could not be created
     */
    public NamedParameterStatement(Connection connection, String query)
            throws SQLException {
        indexMap = new HashMap<String, List<Integer>>();
        String parsedQuery = parse(query);
        statement = connection.prepareStatement(parsedQuery);
    }

    /**
     * 
     * Parses a query with named parameters. The parameter-index mappings are
     * put into the map, and the
     * 
     * parsed query is returned. DO NOT CALL FROM CLIENT CODE. This method is
     * non-private so JUnit code can
     * 
     * test it.
     * 
     * @param query
     *            query to parse *
     * 
     * @return the parsed query
     */
    private final String parse(String query) {
        int length = query.length();
        StringBuffer parsedQuery = new StringBuffer(length);
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        int index = 1;
        for (int i = 0; i < length; i++) {
            char c = query.charAt(i);
            if (inSingleQuote) {
                if (c == '\'') {
                    inSingleQuote = false;
                }
            } else if (inDoubleQuote) {
                if (c == '"') {
                    inDoubleQuote = false;
                }
            } else {
                if (c == '\'') {
                    inSingleQuote = true;
                } else if (c == '"') {
                    inDoubleQuote = true;
                } else if (c == ':' && i + 1 < length &&
                Character.isJavaIdentifierStart(query.charAt(i + 1))) {
                    int j = i + 2;
                    while (j < length
                            && Character.isJavaIdentifierPart(query.charAt(j))) {
                        j++;
                    }
                    String name = query.substring(i + 1, j);
                    c = '?'; // replace the parameter with a question mark
                    i += name.length(); // skip past the end if the parameter
                    List<Integer> indexList = indexMap.get(name);
                    if (indexList == null) {
                        indexList = new LinkedList<Integer>();
                    }
                    indexList.add(new Integer(index));
                    indexMap.put(name, indexList);
                    index++;
                }
            }
            parsedQuery.append(c);
        }
        return parsedQuery.toString();
    }

    /**
     * 
     * Returns the indexes for a parameter.
     * 
     * @param name
     *            parameter name
     * 
     * @return parameter indexes
     * 
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     */
    private List<Integer> getIndexes(String name) {
        List<Integer> indexes = indexMap.get(name);
        if (indexes == null) {
            throw new IllegalArgumentException("Parameter not found: " + name);
        }
        return indexes;
    }

    /**
     * 
     * Sets a parameter.
     * 
     * @param name
     *            parameter name
     * 
     * @param value
     *            parameter value
     * 
     * @throws SQLException
     *             if an error occurred
     * 
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * 
     * @see PreparedStatement#setObject(int, java.lang.Object)
     */
    public void setObject(String name, Object value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (int i = 0; i < indexes.size(); i++) {
            statement.setObject(indexes.get(i), value);
        }

    }

    /**
     * 
     * Sets a parameter.
     * 
     * @param name
     *            parameter name
     * 
     * @param value
     *            parameter value
     * 
     * @throws SQLException
     *             if an error occurred
     * 
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * 
     * @see PreparedStatement#setString(int, java.lang.String)
     */
    public void setString(String name, String value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (int i = 0; i < indexes.size(); i++) {
            int xx = indexes.get(i);
            statement.setString(xx, value);
        }
    }

    /**
     * 
     * Sets a parameter.
     * 
     * @param name
     *            parameter name
     * 
     * @param value
     *            parameter value
     * 
     * @throws SQLException
     *             if an error occurred
     * 
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * 
     * @see PreparedStatement#setInt(int, int)
     */
    public void setInt(String name, int value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (int i = 0; i < indexes.size(); i++) {
            statement.setInt(indexes.get(i), value);
        }
    }

    /**
     * 
     * Sets a parameter.
     * 
     * @param name
     *            parameter name
     * 
     * @param value
     *            parameter value
     * 
     * @throws SQLException
     *             if an error occurred
     * 
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * 
     * @see PreparedStatement#setInt(int, int)
     */
    public void setLong(String name, long value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (int i = 0; i < indexes.size(); i++) {
            statement.setLong(indexes.get(i), value);
        }
    }

    /**
     * 
     * Sets a parameter.
     * 
     * @param name
     *            parameter name
     * 
     * @param value
     *            parameter value
     * 
     * @throws SQLException
     *             if an error occurred
     * 
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * 
     * @see PreparedStatement#setTimestamp(int, java.sql.Timestamp)
     */
    public void setTimestamp(String name, Timestamp value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (int i = 0; i < indexes.size(); i++) {
            statement.setTimestamp(indexes.get(i), value);
        }

    }

    /**
     * 
     * Returns the underlying statement.
     * 
     * @return the statement
     */
    public PreparedStatement getStatement() {
        return statement;
    }

    /**
     * 
     * Executes the statement.
     * 
     * @return true if the first result is a {@link ResultSet}
     * 
     * @throws SQLException
     *             if an error occurred
     * 
     * @see PreparedStatement#execute()
     */
    public boolean execute() throws SQLException {
        return statement.execute();
    }

    /**
     * 
     * Executes the statement, which must be a query.
     * 
     * @return the query results
     * 
     * @throws SQLException
     *             if an error occurred
     * 
     * @see PreparedStatement#executeQuery()
     */
    public ResultSet executeQuery() throws SQLException {
        return statement.executeQuery();
    }

    /**
     * 
     * Executes the statement, which must be an SQL INSERT, UPDATE or DELETE
     * statement;
     * 
     * or an SQL statement that returns nothing, such as a DDL statement.
     * 
     * @return number of rows affected
     * 
     * @throws SQLException
     *             if an error occurred
     * 
     * @see PreparedStatement#executeUpdate()
     */
    public int executeUpdate() throws SQLException {
        return statement.executeUpdate();
    }

    /**
     * 
     * Closes the statement.
     * 
     * @throws SQLException
     *             if an error occurred
     * 
     * @see Statement#close()
     */
    public void close() throws SQLException {
        statement.close();
    }

    /**
     * 
     * Adds the current set of parameters as a batch entry.
     * 
     * @throws SQLException
     *             if something went wrong
     */
    public void addBatch() throws SQLException {
        statement.addBatch();
    }

    /**
     * 
     * Executes all of the batched statements.
     * 
     *
     * 
     * See {@link Statement#executeBatch()} for details.
     * 
     * @return update counts for each statement
     * 
     * @throws SQLException
     *             if something went wrong
     */
    public int[] executeBatch() throws SQLException {
        return statement.executeBatch();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return statement.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return statement.isWrapperFor(iface);
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getMaxRows() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void cancel() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getFetchDirection() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getFetchSize() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void clearBatch() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Connection getConnection() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys)
            throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes)
            throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames)
            throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys)
            throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames)
            throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isPoolable() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

}