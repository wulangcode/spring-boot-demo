package com.wulang.pg.config;

import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.roaringbitmap.RoaringBitmap;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wulang
 * @date 2022-09-03 19:22
 **/
@MappedTypes(RoaringBitmap.class)
public class BitMapTypeHandler extends BaseTypeHandler<RoaringBitmap> {

    @Override
    @SneakyThrows
    public void setNonNullParameter(PreparedStatement ps, int i, RoaringBitmap parameter, JdbcType jdbcType) throws SQLException {
        byte[] array = new byte[parameter.serializedSizeInBytes()];
        parameter.serialize(new java.io.DataOutputStream(new OutputStream() {
            int c = 0;

            @Override
            public void flush() {
            }

            @Override
            public void close() {
            }

            @Override
            public void write(int b) {
                array[c++] = (byte) b;
            }

            @Override
            public void write(byte[] b) {
                write(b, 0, b.length);
            }

            @Override
            public void write(byte[] b, int off, int l) {
                System.arraycopy(b, off, array, c, l);
                c += l;
            }
        }));
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(array);
        ps.setBinaryStream(i, byteArrayInputStream, array.length);
    }

    @Override
    @SneakyThrows
    public RoaringBitmap getNullableResult(ResultSet rs, String columnName) {
        RoaringBitmap roaringBitmap = new RoaringBitmap();
        InputStream binaryStream = rs.getBinaryStream(columnName);
        if (binaryStream == null) return roaringBitmap;
        DataInputStream is = new DataInputStream(binaryStream);
        roaringBitmap.deserialize(is);
        return roaringBitmap;
    }

    @Override
    @SneakyThrows
    public RoaringBitmap getNullableResult(ResultSet rs, int columnIndex) {
        InputStream binaryStream = rs.getBinaryStream(columnIndex);
        RoaringBitmap roaringBitmap = new RoaringBitmap();
        DataInputStream is = new DataInputStream(binaryStream);
        roaringBitmap.deserialize(is);
        return roaringBitmap;
    }

    @Override
    @SneakyThrows
    public RoaringBitmap getNullableResult(CallableStatement cs, int columnIndex) {
        InputStream binaryStream = cs.getObject(columnIndex, InputStream.class);
        RoaringBitmap roaringBitmap = new RoaringBitmap();
        DataInputStream is = new DataInputStream(binaryStream);
        roaringBitmap.deserialize(is);
        return roaringBitmap;
    }
}
