## MongoDB结构概念
1、`文档`MongoDB集合中的记录称为文档。文档含字段和值。

2、`集合`是MongoDB对文档的分组。类似于传统数据库的表，集合存在于数据库中，且不强制要求集合的结构。

3、`_id`是MongoDB中必填的字段。_id是MongoDB文档的唯一标识。若新建的文档没有_id则会自动创建24位唯一id。

4、`游标`指向查询结果集的指针。客户可以遍历邮游标以检索查询结果。

5、MongoDB文档是基于BSON结构存储的。


## 基本操作语句

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
        name: string
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

## 基本CRUD操作

官方API：`https://www.mongodb.com/docs/v4.0/reference/method/js-collection/`

### 1、插入语句
插入注意事项：
> 1、MongoDB所有写操作都是单个文档级别的原子操作。<br/>
> 2、若集合不存在，插入文档时会创建集合。<br/>
> 3、MongoDB集合的每个文档都需要唯一的`_id`字段作为主键，若插入的文档无此字段则MongoDB驱动会自动为_id生成ObjectId。此规则也适用于`upsert: true`更新插入的文档。

基本插入API:
* db.collection.insert();
* db.collection.insertOne();
* db.collection.insertMany();

其它插入API，以下设置upsert:true，实现存在更新，不存在插入:
* db.collection.update();
* db.collection.updateOne();
* db.collection.updateMany();
* db.collection.findAndModify();
* db.collection.findOneAndUpdate();
* db.collection.findOneAndReplace();
* db.collection.save()
* db.collection.bulkWrite()

**插入单个文档**

API：insert()、insertOne()
```javascript
// insert()方法
db.collectionName.insert(
    {
        code: "商品code",
        name: "商品名称",
        category: [
            {
                categoryCode: "woman",
                categoryName: "女士"
            }
        ]
    }
)

// insertOne()方法
db.collectionName.insertOne(
    {
        code: "商品code",
        name: "商品名称"
    }    
)
```

**插入多个文档**

API：insert()、insertMany()
```javascript
// insert()方法
db.collectionName.insert(
    [
        {
            code: "商品code",
            name: "商品名称"
        },
        {
            code: "商品code1",
            name: "商品名称"
        }
    ]
)

// insertMany()方法
db.collectionName.insertMany(
    [
        {
            code: "商品code",
            name: "商品名称"
        },
        {
            code: "商品code1",
            name: "商品名称"
        }
    ]
)
```

**批量写入操作**

官方文档：`https://www.mongodb.com/docs/manual/reference/method/db.collection.bulkWrite/#bulkwrite-example-bulk-write-operation`

描述：db.collectionName.bulkWrite()提供了批量写入、更新、删除操作。

注意：

(1)、有序与无序。<br/>
bulkWrite()默认执行有序的写入，想要指定无序写入请在选项文档中设置ordered：false。<br/> 
针对有序写入，MongoDB会串行执行，若某个文档写入过程发生异常，则会直接返回不在进行后续操作。<br/>
针对无序写入，MongoDB会并行执行，若某个文档写入过程发生异常，则会跳过此数据继续向下执行。

(2)、bulkWrite()支持以下操作。<br/>
* insertOne();
* updateOne();
* updateMany();
* replaceOne();
* deleteOne();
* deleteMany();


批处理示例：
```javascript
db.collectionName.bulkWrite(
    [
        {
            insertOne: {
                document: {
                    code: "test001",
                    goodsName: "新商品",
                    brand: "buying",
                    onListStatus: 1
                }
            }
        },
        {
            updateOne: {
                filter: {
                    code: "test001"
                },
                update: {
                    $set: {
                        goodsName: "新商品名称",
                        brand: "clarks",
                        onListStatus: 0
                    }
                }
            }
        },
        {
            deleteOne: {
                filter: {
                    code: "test002"
                }
            }
        },
        {
            replaceOne: {
                filter: {
                    code: "test002"
                },
                replacement: {
                    code: "test002",
                    goodsName: "xxx",
                    brand: "xxx",
                    onListStatus: 1
                }
            }
        }
    ]
)
```


### 2、查询语句

查询操作符：`https://www.mongodb.com/docs/manual/reference/operator/query/`

注意事项：
> 1、模糊匹配的两种方式：`$regex` 或 `/^匹配字符/`。<br/> 
> 2、使用find()方法会检索到文档的一个游标。<br/>
> 3、findOne()方法其实是find()方法后加了limit(1)。<br/>
> 4、等值查询要求字段类型也要一致。<br/>
> 5、查询null或不存在的字段使用 等值判断null 或 $exist操作符。

基本查询API：
* db.collectionName.find();
* db.collectionName.findOne();
* 聚合管道操作，`$match`管道阶段提供了MongoDB的查询过滤;


常用操作符
> 比较：$eq, $ne, $gte, $lt, $lte, $in, $nin
> 逻辑：$and, $not, $nor, $or
> 元素：$exists, $type
> 匹配：$expr, $jsonSchema, $mod, $regex, $text, $where
> 数组：$all, $elemMatch, $size
> 预测映射：$elemMatch, $meta, $slice


**基本查询操作示例**
```javascript
// 1、等值查询
db.collectionName.find(
    {
        code: "商品code"
    }
)

// 2、使用查询操作符查询
db.collectionName
    // 查询函数：find(筛选条件, 展示结果处理)
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
            "name": -1
        }
    )
    // 排序函数，1：升序,ASC  -1：降序,DESC
    .sort(
        {
            "salesPrice": 1,
            "code": -1
        }
    )
    // 配合limit()实现分页效果
    .skip(0)
    .limit(1)
```

**嵌套文档查询**

嵌套查询三种方式：
> 1、全文档匹配，要求字段顺序、字段值都一致。<br/>
> 2、指定文档字段匹配，使用最广泛的方式。<br/>
> 3、指定文档数组下标匹配。<br/>
> 3、多条件匹配。

嵌套文档示例：
```javascript
// 1、全文档匹配
db.collectionName.find(
    {
        category: {
            categoryCode: "xx",
            name: "xxx"
        }
    }
)

// 2、指定文档字段匹配
db.collectionName.find(
    {
        "category.categoryCode": "xxx"
    }
)

// 3、指定文档数组下标匹配
db.collectionName.find(
    {
        "category.0.categoryCode": "xxx"
    }
)

// 4、多条件匹配
db.collectionName.find(
    {
        category: {
            $elemMatch: {
                categoryCode: "woman",
                categoryGroupCode: "tree"
            }
        }
    }
)
```

**数组查询**

数组查询三种查询方式：
```javascript
// 1、文档全精度查询，且要求数组值、字段顺序等全匹配
// category数组字段必须是以下两个元素，且元素也是此排列顺序
db.collectionName.find(
    {
        category: [
            {
                "categoryCode": "woman",
                "categoryGroupCode": "tree"
            },
            {
                "categoryCode": "man",
                "categoryGroupCode": "tree"
            }
        ]
    }
)

// 2、查询数组包含以下两个元素，且不在意顺序或者数组中是否包含其他元素
db.collectionName.find(
    {
        category: {
            $all: [
                {
                    "categoryCode": "woman",
                    "categoryGroupCode": "tree"
                },
                {
                    "categoryCode": "man",
                    "categoryGroupCode": "tree"
                }
            ]
        }
    }
)
```

**数组中嵌套文档查询**

**查询结果返回指定字段**

结果返回指定字段：
```javascript
// 映射会返回在映射文档中显示设置为1的字段
db.collectionName.find(
    {},
    {
        code: 1,
        name: 1,
        "category.categoryCode": 1
    }
)
```

结果排除指定字段：
```javascript
// 映射会排序在映射文档中设置为0的字段
db.collectionName.find(
    {},
    {
        code: 1,
        name: 0,
        "category.categoryCode": 0
    }
)
```

嵌套数组返回指定索引字段：
```javascript
// $slice: -1，代表返回数组的最后一个元素
db.collectionName.find(
    {},
    {
        code: 1,
        name: 1,
        category: {
            $slice: -1
        }
    }
)
```

**Mongo Shell迭代游标**

```javascript
// 1、使用迭代器处理结果
var result = db.collectionName.find()
while (result.hasNext()) {
    printjson(result.next());
}

// 2、使用for循环处理结果
var result = db.collectionName.find()
result.forEach(printjson)
```



### 3、更新操作

更新操作符文档：`https://www.mongodb.com/docs/manual/reference/operator/update/`

更新操作API：
* db.collectionName.update({filter}, {update}, {options});
* db.collectionName.updateOne({filter}, {update}, {options});
* db.collectionName.updateMany({filter}, {update}, {options});
* db.collectionName.replaceOne({filter}, {update}, {options});

注意事项：
> 1、`$set`操作符，若字段不存在则会常见该字段，若文档不存在则不会更新。<br/>
> 2、`$currentDate`操作符，指定字段更新为当前日期，字段不存在则创建该字段。

更新操作示例：
```javascript
// 1、按指定条件更新单个文档
db.collectionName.updateOne(
    {
        itemCode: "test",
        stockNum: {
            $lt: 100
        }
    },
    {
        $set: {
            goodsName: "新的商品名称",
            brand: "clarks"
        },
        $currentDate: {
            updateTime: true
        }
    }
)

// 2、文档存在则插入，不存在则更新操作
db.collectionName.updateOne(
    {
        itemCode: "test"
    },
    {
        $set: {
            goodsName: "新商品",
            brand: "clarks"
        }
    },
    {
        upsert: true
    }
)

// 3、更新多个文档用法同上
db.collectionName.updateMany(
    {
        ...
    },
    {
        ...
    }
)

// 4、替换文档，查询满足条件的文档，将其替换为一个新的文档
db.collectionName.replaceOne(
    {
        itemCode: "test"
    },
    {
        itemCode: "test",
        goodsName: "新文档",
        brand: "clarks",
        onListStatus: 1,
        category: [
            {
                categoryCode: "man",
                categoryGroupCode: "root"
            }
        ]
    }
)

// 5、修改文档，修改数组字段中某个对象的值
// 单独使用$字符，代指条件匹配元素的位置或下标
db.collectionName.updateMany(
    {
        "salesAttr.attrCode": "size"
    },
    {
        "$set": {
            "salesAttr.$.attrSort": NumberInt(1),
            "salesAttr.$.attrName": "尺码"
        }
    }
)
```


### 4、删除操作

删除操作API：
* db.collectionName.delete()：支持删除单个、多个，不支持删除全量。
* db.collectionName.deleteOne()：仅支持删除单个。
* db.collectionName.deleteMany()：支持删除多个、全量。
* db.collectionName.remove()：可选项删除单个、多个。
* db.collectionName.findOneAndDelete()：可选项排序后删除第一个。

操作示例：
```javascript
// 1、删除集合全部文档
db.collectionName.deleteMany({})

// 2、删除集合满足指定条件的文档
db.collectionName.delete(
    {
        itemCode: "test",
        onListStatus: 0,
        salesPrice: {
            $lt: NumberDecimal(1000)
        }
    }
)

// 3、删除一个文档
db.collectionName.deleteOne(
    {
        onListStatus: 0
    }
)

// 4、remove()方法用法与deleteMany()相似，支持指定移除一个
db.collectionName.remove(
    {
        itemCode: "test"
    },
    {
        justOne: true
    }
)

// 5、删除查询结果排序后的第一个文档
db.collectionName.findOneAndDelete(
    {},
    {
        sort: {
            salesPrice: -1
        }
    }
)
```

## MongoDB聚合

MongoDB提供三种聚合方法:
* 聚合管道
* map-reduce function
* 单用途聚合操作

### 1、聚合管道

官方API地址：`https://docs.mongoing.com/can-kao/mongo-shell-methods/collection-methods/db-collection-aggregate`

函数：db.collectionName.aggregate(管道, 选项);

管道操作符：

| 操作符 | 说明 |
| ------ | ---- |
| $match | 对文档进行筛选 |
| $project | 提取文档中想要获取的字段 |
| $addFields | 对聚合阶段添加临时字段 |


聚合管道示例：
```javascript
db.collectionName.aggregate(
    [
        // 第一阶段：$match条件过滤
        {
            $match: {
                salesPrice: {
                    $lt: NumberDecimal(1000)
                },
                onListStatus: 1
            }
        },
        // 第二阶段：按指定字段分组，并计算指定字段
        {
            $group: {
                _id: "$code",           // _id标准格式，代表分组id;$code指分组的字段;
                totalAmount: {          // totalAmount，聚合结果别名
                    $sum: "$salesPrice"
                }
            }
        },
        // $project作用：1、提取想要的字段(1-展示，0-不展示)  2、为字段命名别名，注意起别名后字段就没有索引了
        {
            $project: {
                _id: 0,
                newAmount: "$totalAmount"
            }
        },
        {
            $sort: {
                totalAmount: 1
            }
        }
    ]
)
```

