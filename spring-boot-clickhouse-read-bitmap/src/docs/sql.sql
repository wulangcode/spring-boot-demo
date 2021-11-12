create table test_bitmap
(
    id         Int64,
    result_bit AggregateFunction(groupBitmap, UInt32)
)
    engine = MergeTree() ORDER BY (id) SETTINGS index_granularity = 8192;