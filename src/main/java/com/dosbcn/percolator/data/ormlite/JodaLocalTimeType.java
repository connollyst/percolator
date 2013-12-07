package com.dosbcn.percolator.data.ormlite;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.joda.time.LocalTime;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;

/**
 * An ORMLite {@link com.j256.ormlite.field.DataType} supporting persistence of
 * JodaTime's {@link LocalTime}.
 *
 * @author Sean Connolly
 */
public class JodaLocalTimeType extends BaseDataType {

	private static final JodaLocalTimeType singleton = new JodaLocalTimeType();

	public static JodaLocalTimeType getSingleton() {
		return singleton;
	}

	private JodaLocalTimeType() {
		super(SqlType.INTEGER, new Class<?>[] {});
	}

	@Override
	public Object parseDefaultString(FieldType fieldType, String defaultStr)
			throws SQLException {
		return Integer.parseInt(defaultStr);
	}

	@Override
	public Object resultToSqlArg(FieldType fieldType, DatabaseResults results,
			int columnPos) throws SQLException {
		Integer millis = results.getInt(columnPos);
		System.out.println("Result to SQL: " + results + " -> " + millis);
		return millis;
	}

	@Override
	public Object javaToSqlArg(FieldType fieldType, Object javaObject)
			throws SQLException {
		LocalTime time = (LocalTime) javaObject;
		if (time == null) {
			System.out.println("Java to SQL: " + javaObject);
			return null;
		} else {
			int millis = time.getMillisOfDay();
			System.out.println("Java to SQL: " + javaObject + " -> " + millis);
			return millis;
		}
	}

	/**
	 * Converts the SQL argument object to a {@link org.joda.time.LocalDate}
	 * Java object.
	 *
	 * @param fieldType
	 *            the field's type
	 * @param sqlArg
	 *            the sql argument
	 * @param columnPos
	 *            the column position in the table
	 * @return the JodaTime LocalDate object
	 */
	@Override
	public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		System.out.println("SQL to Java: " + sqlArg);
		if (sqlArg == null) {
			return null;
		} else {
			int millis = (Integer) sqlArg;
			return new LocalTime(millis);
		}
	}

}
