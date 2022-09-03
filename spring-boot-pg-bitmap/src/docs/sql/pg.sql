CREATE EXTENSION if not exists roaringbitmap;

CREATE TABLE t1
(
  id     integer,
  bitmap roaringbitmap
);

INSERT INTO t1
SELECT 1, rb_build(ARRAY [1,2,3,4,5,6,7,8,9,200]);

INSERT INTO t1
SELECT 2, rb_build_agg(e)
FROM generate_series(1, 100) e;

SELECT rb_cardinality('{1,2,3}');
