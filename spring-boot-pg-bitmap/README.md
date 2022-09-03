**Java 操作 pg Bitmap**

#### 1、需求背景

最近接手项目需要用到pg的bitmap插件，存在需要读取与插入的pg中bitmap的类型

#### 2、代码实现

POM引入

```java
<dependency>
<groupId>org.roaringbitmap</groupId>
<artifactId>RoaringBitmap</artifactId>
<version>${roaringbitmap.version}</version>
</dependency>
```

配置文件

```java
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
```

#### 3、测试

```java
    @Test
public void insterBitMap() {
  T1 t1 = new T1();
  t1.setId(666);
  RoaringBitmap roaringBitmap = RoaringBitmap.bitmapOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
  t1.setBitmap(roaringBitmap);
  pgMapper.insTest(t1);

  List<T1> list = pgMapper.list();

  System.out.println(list.size());
  list.forEach(e -> {
  if (Objects.equals(e.getId(), 666)) {
  System.out.println("id:" + e.getId() + ",bitmap:" + JSON.toJSONString(e.getBitmap().toArray()));
  }
  });
  }
```

测试结果：
![结果](/src/docs/img/img.png)
