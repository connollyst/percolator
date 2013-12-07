package com.dosbcn.percolator.data.ormlite;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.DateTimeType;
import org.joda.time.DateTime;

/**
 * An ORMLite {@link com.j256.ormlite.field.DataType} supporting persistence of
 * JodaTime's {@link DateTime}.
 * <p>
 * Note: ORMLite already supports JodaTime's DateTime object using reflection,
 * this approach will thus be faster.
 * </p>
 *
 * @author Sean Connolly
 */
public class JodaDateTimeType extends DateTimeType {

	private static final JodaDateTimeType singleton = new JodaDateTimeType();

	public static JodaDateTimeType getSingleton() {
		return singleton;
	}

	private JodaDateTimeType() {
		super(SqlType.LONG, new Class<?>[] { DateTime.class });
	}

	@Override
	public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
		DateTime dateTime = (DateTime) javaObject;
		if (dateTime == null) {
			return null;
		} else {
			return dateTime.getMillis();
		}
	}

	@Override
	public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		long millis = (Long) sqlArg;
		return new DateTime(millis);
	}

}
