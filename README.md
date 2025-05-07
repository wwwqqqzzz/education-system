# 教育管理系统

## 项目简介
这是一个基于 Spring Boot + Vue.js 的教育管理系统，支持管理员、教师和学生三种角色的日常教务管理。系统提供了完整的教务流程，包括用户管理、课程管理、成绩管理等功能，旨在提高教育机构的管理效率。

![系统截图](https://cdn.jsdelivr.net/gh/wwwqqqzzz/Image/img/1746626110941-5392fb210907bcc28cc0c5f1b0508577.png)

## 技术栈

### 后端
- Spring Boot 2.7.x - 主框架
- MyBatis - ORM框架
- MySQL 8.0 - 数据库
- Maven - 依赖管理
- Spring Security - 安全认证
- JWT - 用户鉴权
- Lombok - 简化代码

### 前端
- Vue.js 3 - 前端框架
- Element Plus - UI组件库
- Axios - HTTP客户端
- Vue Router - 路由管理
- Vuex/Pinia - 状态管理
- ECharts - 数据可视化

## 功能特性

### 1. 用户管理
- 多角色登录（管理员/教师/学生）
- 用户注册与审核
- 密码重置与找回
- 个人信息维护

### 2. 管理员功能
- 学生管理（增删改查）
- 教师管理（增删改查）
- 课程管理与审核
- 系统配置与维护
- 数据统计与分析

### 3. 教师功能
- 课程创建与管理
- 成绩录入与修改
- 学生名单查看与导出
- 教学资料上传
- 个人信息管理

### 4. 学生功能
- 课程浏览与选择
- 成绩查询与分析
- 个人信息管理
- 课表查看与导出
- 学习资料下载

## 项目结构

```
education-system/
├── backend/                 # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/education/
│   │   │   │   ├── config/           # 配置类
│   │   │   │   ├── controller/       # 控制器
│   │   │   │   ├── dto/              # 数据传输对象
│   │   │   │   ├── entity/           # 实体类
│   │   │   │   ├── mapper/           # MyBatis映射器
│   │   │   │   ├── service/          # 服务层
│   │   │   │   ├── util/             # 工具类
│   │   │   │   └── EducationSystemApplication.java  # 启动类
│   │   │   └── resources/
│   │   │       ├── application.yml   # 应用配置
│   │   │       ├── mapper/           # MyBatis XML映射文件
│   │   │       └── static/           # 静态资源
│   │   └── test/                     # 测试代码
│   └── pom.xml                       # Maven配置
│
├── frontend/                # 前端项目
│   ├── public/              # 静态资源
│   ├── src/
│   │   ├── api/             # API请求
│   │   ├── assets/          # 资源文件
│   │   ├── components/      # 组件
│   │   ├── router/          # 路由配置
│   │   ├── store/           # 状态管理
│   │   ├── views/           # 页面
│   │   ├── App.vue          # 根组件
│   │   └── main.js          # 入口文件
│   ├── package.json         # 依赖配置
│   └── vite.config.js       # Vite配置
│
├── docs/                    # 文档
├── .gitignore               # Git忽略文件
└── README.md                # 项目说明
```

## 安装与运行

### 环境要求
- JDK 11+
- Node.js 16+
- MySQL 8.0+
- Maven 3.6+

### 后端启动
1. 克隆项目
   ```bash
   git clone https://github.com/wwwqqqzzz/education-system.git
   cd education-system
   ```

2. 配置数据库
   - 创建数据库 `education_system`
   - 修改 `backend/src/main/resources/application.yml` 中的数据库连接信息

3. 编译运行
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```
   后端服务将在 http://localhost:8080 运行

### 前端启动
1. 安装依赖
   ```bash
   cd frontend
   npm install
   ```

2. 开发模式运行
   ```bash
   npm run dev
   ```
   前端服务将在 http://localhost:5173 运行

3. 生产环境构建
   ```bash
   npm run build
   ```

## API文档
系统API文档使用Swagger自动生成，启动后端服务后访问：
```
http://localhost:8080/swagger-ui/index.html
```

## 贡献指南
1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建Pull Request

## 许可证
本项目采用 MIT 许可证 - 详情请参阅 [LICENSE](LICENSE) 文件
