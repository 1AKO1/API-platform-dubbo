# API-platform-dubbo
API开放平台

```bash
nacos: startup.cmd -m standalone
```

# 设计
## 数据库设计
> 调用记录表
```sql
id
interface_info_id
user_id
invoke_date
```

> 金额表
```sql
id
user_id
acount
```


# 问题以及解决方案
1. 调用接口结束后，生成订单，再扣费，这个过程中有一个服务超时，导致订单写入了，但是并没有扣费的问题。
   1. 解决方案：分布式事务
2. 如果订单写入的时候加了超时时间，应该会添加订单信息，但是不会扣费，不过为啥订单信息会生成3遍，是dubbo的重试机制？