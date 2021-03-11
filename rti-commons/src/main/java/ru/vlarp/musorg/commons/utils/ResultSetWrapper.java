package ru.vlarp.musorg.commons.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetWrapper {
    private ResultSet resultSet;

    public ResultSetWrapper(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public String getString(String label) throws SQLException {
        return resultSet.getString(label);
    }

    public Long getLong(String label) throws SQLException {
        Long value = resultSet.getLong(label);
        return (resultSet.wasNull()) ? null : value;
    }
}
