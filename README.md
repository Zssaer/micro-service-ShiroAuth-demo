# micro-service-auth-demo (微服务认证授权Demo)
使用Shiro+Spring-Gateway+Nacos+Redis的微服务授权Demo案例
## 主要使用技术栈：
- Shiro
- SpringCloud-GateWay网关
- Ali-Nacos注册中心
- Redis缓存存储
- MyBatis+通用Mapper

## 授权步骤：
- 利用网关 使用micro-service-login Login接口登录
- 利用网关全局拦截器 使用micro-service-auth isPermitted接口进行对URL授权验证
