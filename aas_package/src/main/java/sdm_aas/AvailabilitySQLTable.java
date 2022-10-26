package sdm_aas;import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.tools.sql.driver.SQLDriver;

public class AvailabilitySQLTable {

	public static final String SCHEMA_NAME = "DataCsv";
	public static final String TABLE_NAME = "Data";
	public static final String FULL_TABLE_NAME = SCHEMA_NAME + "." + TABLE_NAME;
	public static final String COLUMNTIME = "Time";
	public static final String COLUMNRESOURCEID = "ResourceIDshort";
	public static final String COLUMNSTATE = "State";
	public static final String COLUMNACTIVITY = "Activity";
	public static final String COLUMNEXPECTED_END_TIME = "ExpectedEndTime";
	public static final String COLUMNMATERIAL = "Material";
	public static final String COLUMNTARGETLOCATION = "TargetLocation";
	public static final String COlUMNMTTF = "MTTF";
	public static final String COlUMNMTTR = "MTTR";

	private static SQLDriver driver;

	/**
	 * creates a schema with a SQL table the table consists out of 7 columns
	 * 
	 * @param args
	 * @throws SQLException
	 */

	public static void main(String[] args) throws SQLException {
		driver = new SQLDriver("//localhost/basyx-map?", "postgres", "admin", "jdbc:postgresql:",
				"org.postgresql.Driver");

		String sqlCommandStringSchema = "CREATE SCHEMA IF NOT EXISTS " + SCHEMA_NAME + ";";
		driver.sqlUpdate(sqlCommandStringSchema);

		String sqlCommandStringTable = "CREATE TABLE IF NOT EXISTS " + FULL_TABLE_NAME + " " + getSQLColumnTypes()
				+ ";";
		driver.sqlUpdate(sqlCommandStringTable);

		String updateString = generateUpdateTestDataSQLString(getColumnSQLTypes());
		driver.openConnection();
		PreparedStatement statement = driver.getConnection().prepareStatement(updateString);

		statement.setFloat(5, (float) (14.123)); // Time
		statement.setString(7, (String) ("r2")); // resource
		statement.setString(2, (String) ("Breakdownstate")); // state
		statement.setString(4, (String) ("startState")); // activity
		statement.setFloat(8, (float) (44.234)); // expected end time
		statement.setString(6, (String) ("material_1 Instance")); // material
		statement.setString(3, (String) ("R1")); // location
		statement.setFloat(1, (float) (35)); // MTTR
		statement.setFloat(9, (float) (600)); // MTTF

		statement.execute();
		driver.closeConnection();
	}

	private static String getSQLColumnTypes() {
		return getColumnSQLTypes().entrySet().stream().map(e -> e.getKey() + " " + e.getValue())
				.collect(Collectors.joining(",", "(", ")"));
	}

	static Map<String, String> getColumnSQLTypes() {
		Map<String, String> colSQLTypes = new HashMap<>();
		colSQLTypes.put(COLUMNTIME, "float");
		colSQLTypes.put(COLUMNRESOURCEID, "VARCHAR(255)");
		colSQLTypes.put(COLUMNSTATE, "VARCHAR(255)");
		colSQLTypes.put(COLUMNACTIVITY, "VARCHAR(55)");
		colSQLTypes.put(COLUMNEXPECTED_END_TIME, "float");
		colSQLTypes.put(COLUMNMATERIAL, "VARCHAR(255)");
		colSQLTypes.put(COLUMNTARGETLOCATION, "VARCHAR(255)");
		colSQLTypes.put(COlUMNMTTF, "float");
		colSQLTypes.put(COlUMNMTTR, "float");

		return colSQLTypes;
	}

	private static String generateUpdateTestDataSQLString(Map<String, String> colSQLTypes) {
		String colList = colSQLTypes.keySet().stream().collect(Collectors.joining(",", "(", ")"));
		String typeList = colSQLTypes.entrySet().stream().map(e -> "?::" + e.getValue())
				.collect(Collectors.joining(", ", "(", ")"));
		return "INSERT INTO " + FULL_TABLE_NAME + " " + colList + " VALUES " + typeList + ";";
	}
}
