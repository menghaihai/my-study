package cn.mfj.demo;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.CastUtils;

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
               "code": "A1054-0000",
               "displayStatus": 0,
               "onListStatus": 0
           }
      )
    */
    @Test
    public void basicQueryDemo() {
        Query query = new Query();
        Criteria criteria = Criteria.where("code").is("A1054-0000")
                .and("displayStatus").is(0)
                .and("onListStatus").is(0);
        query.addCriteria(criteria);
        List<Map<String, Object>> resultList = CastUtils.cast(mongoTemplate.find(query, Map.class, BUYING_ITEM_COLLECTION));
        log.info("查询结果为：{}", JSON.toJSONString(resultList));
    }
}
