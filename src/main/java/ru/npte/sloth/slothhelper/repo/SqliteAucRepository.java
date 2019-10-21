package ru.npte.sloth.slothhelper.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.npte.sloth.slothhelper.dto.AucItem;

import java.sql.*;

@Repository
public class SqliteAucRepository implements AucRepository {

    private static final Logger logger = LoggerFactory.getLogger(SqliteAucRepository.class);

    private final static String DRIVER_NAME = "org.sqlite.JDBC";
    private final static String CONNECTION_STRING = "jdbc:sqlite:slothauc.s3db";

    private final static String SQL_GET_ITEM = "select * from storage where " +
            "id = ? and seller = ? and name = ?";

    private final static String SQL_REPLACE_ITEM = "replace into storage " +
            "(id, seller, name) values(?, ?, ?)";

    @Override
    public AucItem get(AucItem aucItem) {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            logger.error("Can not load driver", e);
        }

        AucItem res = null;
        ResultSet resultSet = null;

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement stat = conn.prepareStatement(SQL_GET_ITEM)) {
            stat.setString(1, aucItem.getId());
            stat.setString(2, aucItem.getSeller());
            stat.setString(3, aucItem.getName());

            resultSet = stat.executeQuery();
            while (resultSet.next()) {
                res = new AucItem(resultSet.getString(0),
                        resultSet.getString(1),
                        resultSet.getString(2));
            }

        } catch (SQLException e) {
            logger.error("Error while getting item", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Error while closing resultSet", e);
                }
            }
        }

        return res;
    }

    @Override
    public void put(AucItem aucItem) {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            logger.error("Can not load driver", e);
        }

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement stat = conn.prepareStatement(SQL_REPLACE_ITEM)) {
            stat.setString(1, aucItem.getId());
            stat.setString(2, aucItem.getSeller());
            stat.setString(3, aucItem.getName());

            stat.execute();
        } catch (SQLException e) {
            logger.error("Error while replacing item", e);
        }
    }
}
