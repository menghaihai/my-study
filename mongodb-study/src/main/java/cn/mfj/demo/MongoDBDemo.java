package cn.mfj.demo;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.types.Decimal128;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.CastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * mongoDB基本操作DEMO示例
 *
 * @author favian.meng on 2022-12-20
 */
public class MongoDBDemo {

    private static final MongoTemplate mongoTemplate;

    private static final Logger log = LoggerFactory.getLogger(MongoDBDemo.class);

    private static final String BUYING_SKU_COLLECTION = "tenantbuying_sku_collection";

    private static final String BUYING_ITEM_COLLECTION = "tenantbuying_item_collection";

    static {
        String mongoDBConnectionString = "mongodb://root:0vKz7lOrxTBI@10.10.221.40:27017/?readPreference=secondary&authentication-database=goods_callback";
        MongoClient mongoClient = MongoClients.create(mongoDBConnectionString);
        MongoDbFactory mongoDbFactory = new SimpleMongoClientDbFactory(mongoClient, "goods_callback");
        mongoTemplate = new MongoTemplate(mongoDbFactory);
    }

    /*
      基本条件查询语句
      db.getCollection("tenantbuying_item_collection")
      .find(
           {
               "displayStatus": 0,
               "onListStatus": 0,
               "salesPrice": {
                    "$gte": NumberDecimal(1),
                    "$lte": NumberDecimal(10000)
               },
               "skuList": {
                    "$in": ["015715801204"]
               },
               "category.categoryCode": "woman",
               "$or": [
                    {
                        "onListStatus": 0
                    },
                    {
                        "onListStatus": 1
                    },
               ],
               "name": {
                    "$regex": "模拟",
                    "$options": ""
               },
               "code": {
                    "$exists": true
               }
           },
           {
                "code": 1,
                "name": 1,
                "category.categoryCode": 1
           }
      )
      .sort(
            {
                salesPrice: -1,
                standardPrice: -1
            }
      )
      .skip(0)
      .limit(10)
    */
    @Test
    public void basicQueryDemo1() {
        Query query = new Query();
        // 1、基本条件查询
        Criteria criteria = Criteria.where("shopCode").is("develop")
                .and("displayStatus").is(0)
                .and("onListStatus").is(0);

        // 2、区间范围查询，注意：MongoDB会把java BigDecimal对象序列化为字符串，需要转换为Decimal128对象
        criteria.and("salesPrice")
                .gte(new Decimal128(1))
                .lte(new Decimal128(10000));

        // 3、in关键字查询
        criteria.and("skuList").in("015715801204");

        // 4、判断指定字段是否存在
        criteria.and("code").exists(Boolean.TRUE);

        // 5、or语句使用
        criteria.orOperator(Criteria.where("onListStatus").is(1), Criteria.where("onListStatus").is(0));

        // 6、复杂对象嵌套查询
        criteria.and("category.categoryCode").is("woman");

        // 7、模糊查询
        criteria.and("name").regex("模拟");

        // 8、分页查询
        query.skip(0).limit(10);

        // 9、排序
        List<Sort.Order> sortOrderList = new ArrayList<>();
        sortOrderList.add(new Sort.Order(Sort.Direction.ASC, "salesPrice", Sort.NullHandling.NULLS_LAST));
        sortOrderList.add(new Sort.Order(Sort.Direction.DESC, "standardPrice", Sort.NullHandling.NULLS_LAST));
        query.with(Sort.by(sortOrderList));

        // 10、指定结果字段
        query.fields().include("code").include("name").include("category.categoryCode");

        query.addCriteria(criteria);
        List<Map<String, Object>> resultList = CastUtils.cast(mongoTemplate.find(query, Map.class, BUYING_ITEM_COLLECTION));
        log.info("查询结果为：{}", JSON.toJSONString(resultList));
    }


    /*
        三种嵌套查询方式
        1、标准结构查询，要求嵌套对象字段顺序、个数、值都一致才行，复杂对象查询一般不使用该方式
        db.tenantbuying_item_collection.find(
        {
            "category": {
                "categoryCode": "woman",
                "categoryGroupCode": "categoryTree"
            }
        )

        2、常用结构查询，普遍使用该方式
        db.tenantbuying_item_collection.find(
            {
                "category.categoryCode": "woman",
                "category.categoryGroupCode": "categoryTree"
            }
        )

        3、常用结构查询 + 指定角标查询
        db.tenantbuying_item_collection.find(
            {
                "category.0.categoryCode": "woman",
                "category.0.categoryGroupCode": "categoryTree"
            }
        )

        4、关键词查询，嵌套对象使用$elemMatch 匹配元素
        db.tenantbuying_item_collection.find(
            {
                "category": {
                    "$elemMatch": {
                        "categoryCode": "woman",
                        "categoryGroupCode": "categoryTree"
                    }
                }
            }
        )
     */
    @Test
    public void nestedQueryDemo() {
        // 方式2
        Query query = new Query();
        Criteria criteria = Criteria
                .where("category.categoryCode").is("woman")
                .and("category.categoryGroupCode").is("categoryTree");
        query.addCriteria(criteria);

        List<Map<String, Object>> resultList = CastUtils.cast(mongoTemplate.find(query, Map.class, BUYING_ITEM_COLLECTION));
        log.info("查询结果为：{}", JSON.toJSONString(resultList));


        // 方式4
        Query query1 = new Query();
        Criteria criteria1 = Criteria.where("category").elemMatch(
                Criteria.where("categoryCode").is("woman")
                        .and("categoryGroupCode").is("categoryTree")
        );
        query1.addCriteria(criteria1);

        List<Map<String, Object>> resultList1 = CastUtils.cast(mongoTemplate.find(query1, Map.class, BUYING_ITEM_COLLECTION));
        log.info("查询结果为：{}", JSON.toJSONString(resultList1));
    }

    /*
        聚合条件查询

     */
    @Test
    public void aggregationDemo() {
        // 校验索引是否存在
        if (!mongoTemplate.collectionExists(BUYING_ITEM_COLLECTION)) {
            return;
        }

        // 获取到所有索引信息
        IndexOperations indexOperations = mongoTemplate.indexOps(BUYING_ITEM_COLLECTION);
        List<IndexInfo> indexInfoList = indexOperations.getIndexInfo();
        log.info("索引信息为：{}", JSON.toJSONString(indexInfoList));
    }
}
