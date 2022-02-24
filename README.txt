1.service 存放请求接口方法例如：POST和GET
2.application.yml 主配置文件负责开启/关闭子配置文件
    application-devAccount.yml 存放账号相关的数据
    application-devHome.yml 存放与Home相关的接口
    application-devUser.yml 存放与User相关的接口
3.请求参数：所有的发送请求参数统一在 UrlParams中统一维护
4.如何新增访问接口：
  4.1: 在对应的 application-xxx.yml中写入接口链接 如：getUserId: [https:](不写前缀域名放入主配置文件维护)  www.baidu.com
  4.2: 在对应的类中新增属性比如在application-user.yml中的那么 在user类中添加属性：getUserId
  4.3: 在test包下的测试类中 新建测试类，测试类中只负责传输数据不负责业务处理！
  4.4: 在Controller中新建方法处理Tests的传输数据和业务数据处理
  4.5: 测试联通之后在xml文件中加上test中维护的方法