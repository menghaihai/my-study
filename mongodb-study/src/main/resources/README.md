## 注意事项

### MongoDB结构概念
1、`文档`MongoDB集合中的记录称为文档。文档含字段和值。

2、`集合`是MongoDB对文档的分组。类似于传统数据库的表，集合存在于数据库中，且不强制要求集合的结构。

3、`_id`是MongoDB中必填的字段。_id是MongoDB文档的唯一标识。若新建的文档没有_id则会自动创建24位唯一id。

4、`游标`指向查询结果集的指针。客户可以遍历邮游标以检索查询结果。


### 基本操作语句

1、创建数据库

描述：use命令使用数据库，若不存在则创建数据库。
```javascript
    use goods_callback;
```

2、创建集合/表。

描述：使用以下命令插入一条数据，若集合不存在会先创建集合在插入数据。
```javascript
    // 使用insert方法自动创建集合
    db.collection_name.insert({});
    db.collection_name.insert(
        {
            code: "xxx",
            username: "zhangsan"
        }
    );
    
    // 指定创建集合
    db.createCollection("tenantbuying_item_collection", {
        name: string,
        age: 
    });
```

3、集合插入数组

描述：MongoDB可以使用JavaScript部分语法做批量处理，如下：
```javascript
    // 批处理插入数据
    var userList = [
        {
            code: "xxx1",
            username: "zhangsan"
        },
        {
            code: "xxx2",
            username: "lisi"
        }
    ];
    db.user.insert(userList);
    
    // 循环打印数据
    db.user.find().forEach(printjson)
```

### 基本CRUD操作

#### 插入语句
注意：MongoDB所有写操作都是单个文档级别的原子操作。

基本插入API:
* db.collection.insert({});
* db.collection.insertOne({});
* db.collection.insertMany([{}]);

其它插入API，以下设置upsert:true，实现存在更新，不存在插入:
* db.collection.updateOne({});
* db.collection.updateMany({});
* db.collection.insertMany([{}]);


1、基本条件匹配查询
```javascript
    // 查询xxx字段 = xxx
    db.tenantbuying_item_collection.find(
        {
           code: "item_xxx"
        }
    );

    // 查询金额大于100的商品
    db.tenantbuying_item_collection.find(
        {
            salesPrice: {
                "$gt": 100
            }
        }
    );

    // 查询前10条数据
    db.tenantbuying_item_collection.find().limit(10);

    // 查询第二页前10条数据
    db.tenantbuying_item_collection.find().skip(1).limit(2);

    // 查询结果排序，-1：降序排序，对应DESC；1：升序排序，对应ASC。
    db.tenantbuying_item_collection.find().sort({stockNum: -1});
    
    // 聚合函数
```


2、基本更新操作
```javascript
    // 单个值按条件进行更新
    db.tenantbuying_item_collection.update(
        {
            code: "item_xxx"
        },
        {
           $set: {
               name: "newGoodsName",
               onListStatus: 1
           } 
        }
    )
```

3、基本移除操作
```javascript
    db.tenantbuying_item_collection.remove(
        {
            code: "item_xxx"
        }
    )
```


4、索引操作

```javascript
    // 创建索引
    // 创建基于id、code的普通索引；1代表索引的排序方式。
    db.tenantbuying_item_collection.createIndex(
        {
            id: 1,
            code: 1
        }
    );

    // 查询全部的索引
    db.tenantbuying_item_collection.getIndexes();
    
    // 移除索引
    db.tenantbuying_item_collection.dropindex(
        {
            code: 1
        }
    );
```

