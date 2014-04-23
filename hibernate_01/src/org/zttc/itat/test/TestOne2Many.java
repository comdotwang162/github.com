package org.zttc.itat.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Test;
import org.zttc.itat.model.Classroom;
import org.zttc.itat.model.Comment;
import org.zttc.itat.model.Message;
import org.zttc.itat.model.Student;
import org.zttc.itat.util.HibernateUtil;

public class TestOne2Many {
	@Test
	public void testAdd() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Comment c1 = new Comment();
			c1.setContent("123123123");
			Comment c2 = new Comment();
			c2.setContent("1sdsfdfsdf");
			session.save(c1); session.save(c2);
			Message msg = new Message();
			msg.setTitle("sdfsdfsdfsdf");
			msg.setContent("sdfsdfsdfsdf");
			msg.addComment(c1);
			msg.addComment(c2);
			session.save(msg);
			//此时会发出5条sql，三条查询，两条更新
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
			Message msg = (Message)session.load(Message.class, 1);
			System.out.println(msg.getContent());
			for(Comment comment:msg.getComments()) {
				System.out.println(comment.getContent());
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testLoad2() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Message msg = (Message)session.load(Message.class, 1);
			System.out.println(msg.getComments().size());
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	//特别注意，oneToMany在添加和维护关系时比较麻烦，所以在开发中不建议使用OneToMany的单向
	
	@Test
	public void testAdd01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Student stu1 = new Student();
			stu1.setName("猪八戒");
			stu1.setNo("1111");
			session.save(stu1);
			Student stu2 = new Student();
			stu2.setName("朱纠结");
			stu2.setNo("2222");
			session.save(stu2);
			Classroom cla = new Classroom();
			cla.setGrade(2012);
			cla.setName("计算机应用技术");
			Set<Student> stus = new HashSet<Student>();
			stus.add(stu1); stus.add(stu2);
			cla.setStus(stus);
			session.save(cla);
			/*
			 * 此时也会发出5条sql，所以最佳实践就是不要使用1的一方来维护关系
			 * 在配置文件的set标签中可以通过inverse=true来明确不使用一的这一端维护关系
			 */
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
			Classroom cla = new Classroom();
			cla.setGrade(2012);
			cla.setName("计算机网络技术");
			session.save(cla);
			Student stu1 = new Student();
			stu1.setName("猪八戒");
			stu1.setNo("1111");
			stu1.setClassroom(cla);
			session.save(stu1);
			Student stu2 = new Student();
			stu2.setName("朱纠结");
			stu2.setNo("2222");
			stu2.setClassroom(cla);
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
	public void testLoad02() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
//			Classroom cla = (Classroom)session.load(Classroom.class, 6);
//			System.out.println(cla.getStus().size());
			Student stu = (Student)session.load(Student.class, 15);
			System.out.println(stu.getName()+","+stu.getClassroom().getGrade());
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
}
