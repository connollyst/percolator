package com.dosbcn.percolator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.joda.time.LocalDate;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * A repository providing read and write access for {@link Card} objects.
 *
 * @author Sean Connolly
 */
public class CardRepositoryImpl extends OrmLiteSqliteOpenHelper implements
		CardRepository {

	private static final String LOG_TAG = CardRepositoryImpl.class.getName();

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "com.dosbcn.percolator.sqlite";

	public CardRepositoryImpl(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource source) {
		Log.i(LOG_TAG, "Creating database.");
		try {
			TableUtils.createTable(connectionSource, Card.class);
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Failed to create the database.", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource source,
			int oldVersion, int newVersion) {
		// we have no versions yet
	}

	@Override
	public void create(Card card) {
		try {
			getDAO().create(card);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Card card) {
		try {
			getDAO().update(card);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Card> fetchAll() {
		try {
			List<Card> cards = getDAO().queryForAll();
			Collections.sort(cards);
			return cards;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Card fetchById(int id) {
		try {
			return getDAO().queryForId(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long count() {
		try {
			return getDAO().countOf();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long count(LocalDate day) {
		try {
			Dao<Card, Integer> dao = getDAO();
			PreparedQuery<Card> query = dao.queryBuilder().setCountOf(true)
					.where().eq(Card.COLUMN_NAME_NOTIFICATION_DATE, day)
					.prepare();
			return dao.countOf(query);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Dao<Card, Integer> getDAO() throws SQLException {
		return DaoManager.createDao(getConnectionSource(), Card.class);
	}

}
