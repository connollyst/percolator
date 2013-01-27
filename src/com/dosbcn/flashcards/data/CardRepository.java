package com.dosbcn.flashcards.data;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * 
 * @author Sean Connolly
 */
public class CardRepository extends OrmLiteSqliteOpenHelper {

	private static final String LOG_TAG = CardRepository.class.getSimpleName();

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "com.dosbcn.flashcards.sqlite";

	public CardRepository(Context context) {
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

	public void create(Card flashCard) {
		try {
			getFlashCardDAO().create(flashCard);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Card> fetchAll() {
		try {
			return getFlashCardDAO().queryForAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Card fetchById(int id) {
		try {
			return getFlashCardDAO().queryForId(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Dao<Card, Integer> getFlashCardDAO() throws SQLException {
		return DaoManager.createDao(getConnectionSource(), Card.class);
	}

}
