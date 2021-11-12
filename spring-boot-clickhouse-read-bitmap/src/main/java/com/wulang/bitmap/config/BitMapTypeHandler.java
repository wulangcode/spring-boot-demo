package com.wulang.bitmap.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.roaringbitmap.RoaringBitmap;
import ru.yandex.clickhouse.domain.ClickHouseDataType;
import ru.yandex.clickhouse.util.ClickHouseBitmap;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Administrator
 */
@MappedTypes(RoaringBitmap.class)
public class BitMapTypeHandler extends BaseTypeHandler<RoaringBitmap> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RoaringBitmap parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, ClickHouseBitmap.wrap(parameter, ClickHouseDataType.UInt32));
    }

    @Override
    public RoaringBitmap getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return (RoaringBitmap) rs.getObject(columnName, ClickHouseBitmap.class).unwrap();
    }

    @Override
    public RoaringBitmap getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return (RoaringBitmap) rs.getObject(columnIndex, ClickHouseBitmap.class).unwrap();
    }

    @Override
    public RoaringBitmap getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return (RoaringBitmap) cs.getObject(columnIndex, ClickHouseBitmap.class).unwrap();
    }
}
