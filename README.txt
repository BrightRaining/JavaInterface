JavaInterface  接口自动化项目
为自行封装使用，如有不妥之处请留言，以下列举推广后比较好的地方  
1.service 存放请求接口方法例如：POST和GET
2.application.yml 主配置文件负责开启/关闭子配置文件
    application-devAccount.yml 存放账号相关的数据
    application-devHome.yml 存放与Home相关的接口
    application-devUser.yml 存放与User相关的接口
3.请求参数：所有的发送请求参数统一在 UrlParams中统一维护
4.因避免个人服务器被攻击所以将相关信息全部删除，启动文件为Application.java
5.该项目以接口的形式对外开放对使用者友好，使用者只需要调用对应的接口就能执行相应的用例
6.可让开发人员快速进行冒烟测试和之前接口的回归测试
