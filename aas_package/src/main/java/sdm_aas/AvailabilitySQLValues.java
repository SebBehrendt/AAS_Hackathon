package sdm_aas;import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.basyx.tools.sql.driver.SQLDriver;
import org.eclipse.basyx.tools.sql.query.DynamicSQLQuery;

/**
 * retrieves MTTR, MTTF and data for specific resources for resource 1 enter in
 * body raw {id: "r1"} for get request for resource 2 enter in body raw {id:
 * "r2"} for GET request
 * 
 * http://localhost:4000/handson/resource/aas/submodels/Availability/submodel/submodelElements/get_MTTF/invoke
 * http://localhost:4000/handson/resource/aas/submodels/Availability/submodel/submodelElements/get_MTTR/invoke
 * http://localhost:4000/handson/resource/aas/submodels/Availability/submodel/submodelElements/get_resource_data/invoke
 * 
 * @author marieke
 *
 */
public class AvailabilitySQLValues {
	private static SQLDriver driver;
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

	protected static SQLDriver getSQLDriver() {
		if (driver == null) {
			driver = new SQLDriver("//localhost/basyx-map?", "postgres", "admin", "jdbc:postgresql:",
					"org.postgresql.Driver");
		}
		return driver;
	}

	public static String getData(String resourceidshort) throws SQLException {
		List<Map<String, Object>> queriedVALUES = SQLValues(resourceidshort);
		String ergebnis = queriedVALUES.toString();
		return ergebnis;
	}

	public static double getMTTR(String resourceidshort) throws SQLException {
		List<Map<String, Object>> queriedVALUES = SQLMTTR(resourceidshort);
		ListIterator<Map<String, Object>> iterator = null;
		iterator = queriedVALUES.listIterator();
		Map<String, Object> map = null;
		while (iterator.hasNext()) { // skip until last row
			map = iterator.next();
		}
		double timeValue = (double) (map.get(COlUMNMTTR));
		return timeValue;
	}

	public static double getMTTF(String resourceidshort) throws SQLException {
		List<Map<String, Object>> queriedVALUES = SQLMTTF(resourceidshort);
		ListIterator<Map<String, Object>> iterator = null;
		iterator = queriedVALUES.listIterator();
		Map<String, Object> map = null;
		while (iterator.hasNext()) { // skip until last row
			map = iterator.next();
		}
		double timeValue = (double) (map.get(COlUMNMTTF));
		return timeValue;
	}

	private static List<Map<String, Object>> SQLValues(String resourceidshort) {
		String sqlQueryString = "SELECT * FROM " + FULL_TABLE_NAME + " WHERE " + COLUMNRESOURCEID + " = " + "'"
				+ resourceidshort + "'" + ";";
		String colTypeList = AvailabilitySQLTable.getColumnSQLTypes().entrySet().stream()
				.map(e -> e.getKey() + ":" + e.getValue()).collect(Collectors.joining(","));
		String sqlResultFilter = "listOfMaps(" + colTypeList + ")";
		DynamicSQLQuery query = new DynamicSQLQuery(getSQLDriver(), sqlQueryString, sqlResultFilter);
		return (List<Map<String, Object>>) query.get();
	}

	static List<Map<String, Object>> SQLMTTR(String resourceidshort) {
		String sqlQuery2String = "SELECT * FROM " + FULL_TABLE_NAME + " WHERE " + COLUMNRESOURCEID + " = " + "'"
				+ resourceidshort + "'" + ";";
		String sqlResultFilter = "listOfMaps(MTTR:Float)";
		DynamicSQLQuery query = new DynamicSQLQuery(getSQLDriver(), sqlQuery2String, sqlResultFilter);
		System.out.println((List<Map<String, Object>>) query.get());
		return (List<Map<String, Object>>) query.get();
	}

	static List<Map<String, Object>> SQLMTTF(String resourceidshort) {
		String sqlQuery2String = "SELECT * FROM " + FULL_TABLE_NAME + " WHERE " + COLUMNRESOURCEID + " = " + "'"
				+ resourceidshort + "'" + ";";
		String sqlResultFilter = "listOfMaps(MTTF:Float)";
		DynamicSQLQuery query = new DynamicSQLQuery(getSQLDriver(), sqlQuery2String, sqlResultFilter);
		System.out.println((List<Map<String, Object>>) query.get());
		return (List<Map<String, Object>>) query.get();
	}
}