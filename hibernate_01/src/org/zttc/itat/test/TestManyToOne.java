package org.zttc.itat.test;

import org.hibernate.Session;
import org.junit.Test;
import org.zttc.itat.model.Classroom;
import org.zttc.itat.model.Student;
import org.zttc.itat.util.HibernateUtil;

public class TestManyToOne {
	@Test
	public void testAdd01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			//先添加1
			Classroom c = new Classroom();
			c.setGrade(2012);
			c.setName("计算机网络技术");
			session.save(c);
			Student stu1 = new Student();
			stu1.setName("猪八戒");
			stu1.setNo("001");
			stu1.setClassroom(c);
			session.save(stu1);
			Student stu2 = new Student();
			stu2.setName("孙悟空");
			stu2.setNo("002");
			stu2.setClassroom(c);
			session.save(stu2);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testAdd02() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			//先添加多
			Student stu1 = new Student();
			stu1.setName("沙僧");
			stu1.setNo("003");
			session.save(stu1);
			Student stu2 = new Student();
			stu2.setName("唐僧");
			stu2.setNo("004");
			session.save(stu2);
			Classroom c = new Classroom();
			c.setGrade(2012);
			c.setName("计算机应用技术");
			session.save(c);
			//此时还会发两条update
			stu1.setClassroom(c);
			stu2.setClassroom(c);
			//最佳实践：一定要先添加一的一方，之后在添加多的一方
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testAdd03() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Classroom c = new Classroom();
			c.setGrade(2012);
			c.setName("计算机信息管理");
			//此时classroom没有存储，所以在添加student的时候没有外键，会抛出异常
			Student stu1 = new Student();
			stu1.setName("如来");
			stu1.setNo("005");
			session.save(stu1);
			Student stu2 = new Student();
			stu2.setName("观音");
			stu2.setNo("006");
			session.save(stu2);
			stu1.setClassroom(c);
			stu2.setClassroom(c);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testDelete01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Student stu = (Student)session.load(Student.class, 7);
			session.delete(stu);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testLoad() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Student stu = (Student)session.load(Student.class, 1);
			//此时仅仅只是发一条sql
			System.out.println(stu.getName());
			//此时student的关联对象Classroom也是延迟加载的，会再发一条sql来取对象
			System.out.println(stu.getClassroom().getName());
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
}
