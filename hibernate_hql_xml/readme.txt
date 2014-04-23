一、导入相应的包
	1、hibernate安装文件夹中的lib->required中的包
	2、导入log4j
	3、导入数据库驱动
二、创建hibernate的配置文件
	在src的目录下创建相应的hibernate.cfg.xml在这个文件中加入相应的数据库基本信息的配置
	在hibernate.cfg.xml的配置文件中首先需要配置相应的数据库基本连接
三、创建实体类
四、在实体类的包中创建相应的hbm文件，用来指定实体类和数据库映射关系
五、将配置文件添加到hibernate的cfg的配置文件中
六、创建SessionFactory,SessionFactory是线程安全，所以整个SessionFactory应该基于单例的模式来创建
		Configuration cfg = new Configuration().configure();
		//cfg.buildSessionFactory();//在hibernate3中都是使用该种方法创建，但是在4中被禁用了
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
							.applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);
七、创建session
	Session session = factory.openSession();
八、通过session来进行各种操作
	以下代码完成了对象的添加操作
		try {
			session = factory.openSession();
			//开启事务
			session.beginTransaction();
			User u = new User();
			u.setNickname("张三");
			u.setPassword("123");
			u.setUsername("zhangsan");
			u.setBorn(new Date());
			session.save(u);
			//提交事务
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			if(session!=null) session.close();
		}