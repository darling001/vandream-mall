package com.vandream.mall.commons.utils.handler;

import java.sql.*;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author wangchengli
 * @version 1.0
 * @date 2018-01-25
 */
@MappedTypes(Long.class)
@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class LongTypeHandler extends BaseTypeHandler<Long> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Long aLong, JdbcType jdbcType) throws SQLException {
        preparedStatement.setTimestamp(i, new Timestamp(aLong));

    }

    @Override
    public Long getNullableResult(ResultSet resultSet,  String columnName) throws SQLException {
        Timestamp date = resultSet.getTimestamp(columnName);
        if (null != date) {
            return date.getTime();
        }
        return null;
    }

    @Override
    public Long getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Timestamp date = resultSet.getTimestamp(i);
        if (null != date) {
            return date.getTime();
        }
        return null;
    }

    @Override
    public Long getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Timestamp date = callableStatement.getTimestamp(i);
        if (null != date) {
            return date.getTime();
        }
        return null;
    }
}
