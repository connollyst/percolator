package com.dosbcn.percolator.data.ormlite;

import java.sql.SQLException;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;

/**
 * An ORMLite {@link com.j256.ormlite.field.DataType} supporting persistence of
 * JodaTime's {@link LocalDate}.
 *
 * @author Sean Connolly
 */
public class JodaLocalDateType extends BaseDataType {

	private static final JodaLocalDateType singleton = new JodaLocalDateType();

	public static JodaLocalDateType getSingleton() {
		return singleton;
	}

	private JodaLocalDateType() {
		super(SqlType.LONG, new Class<?>[] {});
	}

	@Override
	public Object parseDefaultString(FieldType fieldType, String defaultStr)
			throws SQLException {
		return Long.parseLong(defaultStr);
	}

	@Override
	public Object resultToSqlArg(FieldType fieldType, DatabaseResults results,
			int columnPos) throws SQLException {
		return results.getLong(columnPos);
	}

	@Override
	public Object javaToSqlArg(FieldType fieldType, Object javaObject)
			throws SQLException {
		LocalDate date = (LocalDate) javaObject;
		if (date == null) {
			return null;
		} else {
			DateTime dateTime = date.toDateTimeAtStartOfDay();
			return dateTime.getMillis();
		}
	}

	/**
	 * Converts the SQL argument object to a {@link LocalDate} Java object.
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
		if (sqlArg == null) {
			return null;
		} else {
			long millis = (Long) sqlArg;
			return new LocalDate(millis);
		}
	}

}
