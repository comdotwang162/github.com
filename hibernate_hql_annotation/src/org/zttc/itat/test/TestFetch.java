package org.zttc.itat.test;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.zttc.itat.model.Classroom;
import org.zttc.itat.model.Student;
import org.zttc.itat.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class TestFetch {
	@Test
	public void test01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			/**
			 * 对于Annotation的配置而言，默认就是基于join的抓取的，所以只会发出一条SQL
			 */
			Student stu = (Student)session.load(Student.class, 1);
			System.out.println(stu.getName()+","+stu.getClassroom().getName()+","+stu.getClassroom().getSpecial().getName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void test02() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			/**
			 * 由于基于Annotation的配置没有延迟加载，此时会把所有的关联对象查询上来，发大量的SQL语句
			 */
			List<Student> stus = session.createQuery("from Student").list();
			for(Student stu:stus) {
				System.out.println(stu.getName()+","+stu.getClassroom());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void test04() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			/**
			 * 在XML中配置了fetch=join仅仅只是对load的对象有用，对HQL中查询的对象无用，
			 * 所以此时会发出查询班级的SQL，解决的这个SQL的问题有两种方案，
			 * 一种是设置对象的抓取的batch-size
			 * 另一种方案在HQL中使用fecth来指定抓取
			 * 特别注意，如果使用了join fetch就无法使用count(*)
			 * 基于Annotation由于默认的many-to-one的抓取策略是EAGER的，所以当抓取classroom时会自动
			 * 发出多条SQL去查询相应的Special，此时可以通过join fecth继续完成对关联的抓取，
			 * 获取直接将关联对象的fecth设置为LAZY,但是使用LAZY所带来的问题是在查询关联对象时
			 * 需要发出相应的SQL，很多时候也会影响效率
			 */
			List<Student> stus = session.createQuery("select stu from " +
					"Student stu join fetch stu.classroom cla join fetch cla.special").list();
			for(Student stu:stus) {
				System.out.println(stu.getName()+","+stu.getClassroom());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void test05() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			Classroom cla = (Classroom)session.load(Classroom.class, 1);
			System.out.println(cla.getName());
			/*
			 * 此时会在发出一条SQL取学生对象
			 */
			for(Student stu:cla.getStus()) {
				System.out.println(stu.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void test06() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			
			List<Classroom> clas = session.createQuery("from Classroom").list();
			for(Classroom cla:clas) {
				System.out.println(cla.getName());
				/*
				 * 对于通过HQL取班级列表并且获取相应的学生列表时，fecth=join就无效了
				 * 第一种方案可以设置set的batch-size来完成批量的抓取
				 * 第二中方案可以设置fetch=subselect,使用subselect会完成根据查询出来的班级进行一次对学生对象的子查询
				 */
				for(Student stu:cla.getStus()) {
					System.out.print(stu.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}
}
