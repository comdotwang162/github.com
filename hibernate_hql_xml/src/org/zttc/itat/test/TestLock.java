package org.zttc.itat.test;



import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.junit.Test;
import org.zttc.itat.model2.Student;
import org.zttc.itat.util.HibernateUtil;

public class TestLock {
	@Test
	public void testUpdate01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			//只要使用这种方式来load就会为其增加锁
//			Student stu = (Student)session.load(Student.class,1,LockOptions.UPGRADE);
			Student stu = (Student)session.load(Student.class, 1);
			stu.setName("李四1");
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testUpdate02() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
//			Student stu = (Student)session.load(Student.class,1,LockOptions.UPGRADE);
			Student stu = (Student)session.load(Student.class, 1);
			stu.setSex("女");
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
}
