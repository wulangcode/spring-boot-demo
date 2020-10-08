Full text queries 所有的查询语句:

1）match query：用于执行全文查询的标准查询，包括模糊匹配和短语或接近查询。重要参数：控制Token之间的布尔关系：operator：or/and
2）match_phrase query：与match查询类似，但用于匹配确切的短语或单词接近匹配。重要参数：Token之间的位置距离：slop 参数
3）match_phrase_prefix query：与match_phrase查询类似，但是会对最后一个Token在倒排序索引列表中进行通配符搜索。重要参数：模糊匹配数控制：max_expansions 默认值50，最小值为1
4）multi_match query：match查询 的多字段版本。该查询在实际中使用较多，可以降低DSL语句的复杂性。同时该语句有多个查询类型，后面TeHero会专门进行讲解。
5）common terms query：对于中文检索意义不大。
6）query_string query 和 simple_query_string query，其实就是以上 query语句的合集，使用非常灵活，DSL编写简单。但是，TeHero认为这两个查询语句，有一个很明显的弊端：类似于sql注入。如果用户在检索词输入了对应的“关键字”【比如OR、*】等，用户将获取到本不应该被查询到的数据。慎用！
