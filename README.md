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
