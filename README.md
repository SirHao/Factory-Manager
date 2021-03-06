# Factory Manager

基于Spring Boot实现的水厂管理实时监测系统；

大三狗一枚，去年（2019）年初写的人生第一个java项目，代码量还是比平时的课程项目大，设计难度正常范围。国创项目，运气好得了优秀等级；不打算更新

+ 后端：SpringBoot + MyBatis + MySQL
+ 前端：MDUI  +  Thymleaf
+ 开发工具：JB IDEA

#### 1.项目说明

**Part 1:** ```Version3```   项目主体

+ 用户分权限登录，带简易验证码

+ 基于TCP/IP接收数据，监控设备实时显示与数据持久化
+ 基于阈值配置，邮箱服务的异常检测，邮件通知
+ 基于数据库字段设计，实现在一张表中同时实现 工厂-厂房-二级厂房-......n级厂房-设备的管理，从属关系可为任意层级。
+ 基于数据库字段设计，通过对设备和属性的抽象泛化，实现对从未出现的型号的工厂设备的完全兼容；
+ 将监控设备，厂房可视化，用户可自定义对象的展示，故障状况等多个参数，实现对工厂的监控画面完全自定义

**Part2:**```Simulation ```   工厂数据产生器

+ 根据xml配置，模拟产生配置范围内的随机数据（属性数量名称以及属性数据可配置）

#### 2.使用

+ 根据``` version3/version_3.sql```生成数据表,暂不支持注册,请直接在user表中新增

  eg.: username:  liuhao	password: 0912	authority1	userid:2

+ ```application.properties```:配置MySQL密码，端口号（默认8080），以及邮箱服务的密码

+ 启动simulation产生模拟数据，接着启动version3，登录即可

#### 3.注意事项

前端库目前被墙了，所以可以更改静态页面的引用前端库为本地static目录下的对应文件

去年（2018.12-2019.4）项目,怕放久了丢失，放出来，当时第一次写Java，现在忘得差不多了，本地运行了一遍没问题就放出来了，千万别喷，菜狗一枚

初学者如有疑问，欢迎咨询（大佬别来）：2775153204@qq.com

#### 4.项目日志

+ 2018．6-2018.9 

  ​        前后端学习：了解基本web应用运作原理；搞清前后端在项目中发挥的作用；掌握基本的简单的web应用的前端，后端单独应用；
  ​       实施情况：小组成员交流学习明白了web Application基本原理;前端能使用最基本的html以及自定义css，会简单的JS；后端能够完成简单逻辑；满足基本需求，但仍有不足：
  ​	    前端过于简单，没有框架支撑过于简陋，基本不能满足前端逻辑需求；后端不能与前端交互，处理后的数据只能以文本方式显示或命令行输出；

+ 2018.10-2018.11： 

  ​        入手项目：从最简版本搭建项目雏形并在此过程中深化理解项目并进行学习；从最简单功能入手逐个击破；
  ​        实施情况：项目过于庞大，计划从前端入手，中途队友个人原因，项目进度受阻，一个人入手百度地图API；掌握了根据经纬度在地图上绘制以及通过API自定义界面，提供基本服务。但与项目相关度较小，数据是静态的

+ 2018.12

  ​        中期答辩准备，以及项目深入解读：总结项目进度，找出不足；选型前后端框架，进行正式开发；
  ​         实施情况：完成中期答辩，整理了团队当前存在的缺点并更换项目人员，找出了团队分工不合理的地方。简单尝试了spring框架后，团队决定采用Spring Boot框架作为java后端，前端采用Thymeleaf模板引擎实现前后端交互；搭建并运行了简单的实战；与指导老师沟通，细化项目细节，明确项目初步目标——数据库设计；

+ 2019.1 入手项目，完成一些必要模块；
  实施情况：

  + 实现了用户的登录，通过session检查登录状态并添加拦截器拦截未登录请求；
    存在问题：拦截器尚未搞清原理，导致静态资源的请求被拦截（图片，配置，css样式文件等）；
    解决方案：进一步理解拦截器原理，添加“访问白名单”；

  + 选型前端样式框架：MDUI前端有了基本排版以及主题风格，样式不再单一；
    存在问题：框架较为不成熟，只能满足大部分前端样式需求而不能满足前端逻辑需求以及响应式排版需求；

    解决方案：引入bootstrap栅格布局；逻辑采用js实现；

+ 2019.2-2019.3

  设计核心数据库：与设备，车间以及工厂，有关的数据库；完成数据，设备等基础模块；
  	实施情况：

  + 将三个层面的对象统一为device类存在一张表中，通过parent_id指向父节点等设计将关系建立；并通过ID的统一使其与记录数据的表格关联；

       存在问题：只能存储具有相同属性以及相同属性数量的设备；设备不具有扩展性；

  + 建立数据记录表；实现模拟数据并通过TCP传输，模拟了数据采集的过程；
    	存在问题：所有设备的数据存在一张表中，查询时间复杂度较高，数据过多时查询速度下降；

  + 解决了本阶段第一部分的问题：通过设置参数类，设备表通过装配参数组合成新设备；
    	存在问题：数据表过大搜索速度较慢；

  + 通过修改SQL语句优化了数据库查询速度；

  + 通过amchart完成设备数据可视化

+ 2019．4实现界面视图自定义;完善项目功能；
  	实施情况：
  + 为每个设备绑定图片资源；设置其前端属性并持久化于数据库中，为工作人员提供更；
  + 为每个设备绑定了正常情况以及异常情况资源，使其在异常状态更直观地识别；当绑定后，设备将在监控页面以用户定义时的规则出现（正常时异常时图片，长宽，位置等）；
  + 添加邮件预警，验证码功能；能在数据存入数据库前及时发现异常并以邮件方式通知管理员；完善验证码功能一定情况下阻止爬虫；
