Full text queries 所有的查询语句:

1）match query：用于执行全文查询的标准查询，包括模糊匹配和短语或接近查询。重要参数：控制Token之间的布尔关系：operator：or/and
2）match_phrase query：与match查询类似，但用于匹配确切的短语或单词接近匹配。重要参数：Token之间的位置距离：slop 参数
3）match_phrase_prefix query：与match_phrase查询类似，但是会对最后一个Token在倒排序索引列表中进行通配符搜索。重要参数：模糊匹配数控制：max_expansions 默认值50，最小值为1
4）multi_match query：match查询 的多字段版本。该查询在实际中使用较多，可以降低DSL语句的复杂性。同时该语句有多个查询类型。
5）common terms query：对于中文检索意义不大。
6）query_string query 和 simple_query_string query，其实就是以上 query语句的合集，使用非常灵活，DSL编写简单。但是，这两个查询语句，有一个很明显的弊端：类似于sql注入。如果用户在检索词输入了对应的“关键字”【比如OR、*】等，用户将获取到本不应该被查询到的数据。慎用！

Term-level queries 的11种查询，：

1、 所有的 Term-level queries 的检索关键词都不会分词；
2、term query 等价于sql【where Token = “检索词”】；
3、terms query 等价于sql【where Token in ( 检索词List )】；
4、range query 掌握Date Math 和对 range类型字段检索的 relation参数；
5、掌握 wildcard query、prefix query、fuzzy query 这3种模糊查询；
6、terms_set query 用于检索Array类型的字段，但文档中必须定义一个数字字段——表示最低匹配的term数量；
7、exists query 用于检索为null的字段，检索不为null的字段使用 must_not + exists。

