package org.dosbcn.percolator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * A repository providing read and write access for {@link Card} objects.
 *
 * @author Sean Connolly
 */
public class CardRepositoryImpl
        extends OrmLiteSqliteOpenHelper
        implements
        CardRepository {

    private static final String LOG_TAG = CardRepositoryImpl.class
            .getSimpleName();

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "org.dosbcn.flashcards.sqlite";

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

    public void create(Card card) {
        try {
            getFlashCardDAO().create(card);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Card card) {
        try {
            getFlashCardDAO().update(card);
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

    private Dao<Card, Integer> getFlashCardDAO()
            throws SQLException {
        return DaoManager.createDao(getConnectionSource(), Card.class);
    }

}
