# STOJ 在线评测系统后端

### 项目介绍
本项目为个人 OJ 系统的微服务后端，使用了 Spring Cloud Alibaba 对单体项目进行改造，使用 Nacos 作为注册中心。项目主要分为以下模块：

* `user-service`：用户服务
* `question-service`：题目服务
* `judge-service`：判题服务
* `common`：项目通用模块
* `gateway`：服务网关
* `model`：项目公用的实体类
* `service-client`：公用接口模块（多个服务间共享）
