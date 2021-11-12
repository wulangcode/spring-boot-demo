**Java 操作 ClickHouse Bitmap**

#### 1、需求背景

最近接手项目需要做人群标签分析，结果集会保存为bitmap的形式，需要读取ck中bitmap数据到内存中。

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
```

#### 3、测试

```java
   @Test
public void insterCkBitMap(){
  long parseLong=Long.parseLong(RandomStringUtils.randomNumeric(11));
  System.out.println("当前上下文ID为:"+parseLong);
  RoaringBitmap roaringBitmap=RoaringBitmap.bitmapOf(1,2,3,4,5,6,7,8,9,10);
  ckMapper.insertBitMap(new TestBitMap(parseLong,roaringBitmap));

  TestBitMap testBitMap=ckMapper.findById(parseLong);
  System.out.println(testBitMap==null);
  System.out.println(Arrays.toString(testBitMap.getRoaringBitmap().toArray()));
  }
```

测试结果：
![结果](/src/docs/结果.png)
