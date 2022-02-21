# micro-service-ShiroAuth-demo (Shiro微服务认证授权Demo)

使用Shiro+Spring-Gateway+Nacos+Redis的微服务授权Demo案例。



## 主要使用技术栈：

- Shiro
- SpringCloud-GateWay网关
- Ali-Nacos注册中心
- Redis缓存存储
- MyBatis+通用Mapper
- Spring-Boot-Admin

## 授权步骤：

- 利用网关 使用micro-service-login Login接口登录， 其中默认登录用户:admin 密码123456

  请求路径（http://127.0.0.1:8000/login-api/login?user_name=admin&Pwd=123456）

- GET登录后，请求返回Result JSON，内含授权Token

- 利用网关 使用micro-service-login getLoginInfo接口进行获取登录信息，模拟接口授权

  请求路径（http://127.0.0.1:8000/login-api/login/getLoginInfo）  请求头新增 “token”内容，用于前后分离时身份认证



## 技术重点分析

由于Shiro是一种有状态的（利用Session）身份认证，则在前后端分离时固然需要使用Token或者Jwt来进行认证。

目前Shiro官方支持的两种Session存储方式：ehcache 、concurrentHashMap。它们不适用于微服务平台缓存存储，所以我们使用Redis第三方扩展来进行管理Shiro的Session，目前市面上已经拥有其开源项目https://github.com/alexxiyang/shiro-redis，可实现快速Redis+Shiro配置。

使用Token+Redis进行认证，这样认证并非无状态，而是将其原本的Session进行存储到，通过Token进行在Redis进行查询，再将其Redis上的数据进行反序列化。

Shiro的 定位是基于单应用的授权框架，然而现在在一些中大型的项目，使用多为Spring-Cloud微服务项目，所以如何让Shiro在微服务项目中独立存在并授权是项目关键问题。

我采用的方案是：

> 登录方案： 网关开放登录模块的 登录接口--》`登录模块的 登录接口 `使用Feign远程调用 `授权模块的 登录认证接口`--》授权模块的 登录认证接口通过Shiro登录接口生成的Session通过一串Token存储在Redis缓存中。
>
> 授权认证方案：网关对需要进行授权的接口进行设置过滤器--》网关过滤器接受到 对于接口后，先将其头部Token和请求连接 调用其  `授权模块的 认证接口` --》通过Token获取到Redis缓存中的Shiro-Session，ShiroRealm的doGetAuthorizationInfo获取到用户角色和权限 --》网关过滤器的请求 返回True则 授权认证成功，否则为授权认证失败 --》成功则通过放行原本请求



## 记一次编写时途中遇到的问题总结

认证后获取不到其认证身份信息“Principal”，这个问题困扰我很久，发现其在网关过滤器中的远程调用都能够获取信息，结果在其认证通过后，身份信息居然为Null了。

最开始我怀疑是Shiro-Redis这个依赖包的问题，以为是这个依赖包的配置问题，导致Shiro-Session无法正常反序列化，从而得不到身份信息。

结果其罪魁祸首是 Feign！Feign远程请求时的原请求头部无法正常传递，从而导致，身份认证成功后原请求头部Token丢失，导致其Shiro第二次请求（第一次是授权认证，第二次才是正常的接口调用SecurityUtils）无法获取到Shiro-Session。最终我采用其Feign远程请求时重新生成新的请求，在新请求中添加头部Token。



