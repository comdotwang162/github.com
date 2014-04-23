package org.zttc.itat.test;

import org.hibernate.Session;
import org.junit.Test;
import org.zttc.itat.model.Admin;
import org.zttc.itat.model.Classroom;
import org.zttc.itat.model.Course;
import org.zttc.itat.model.Role;
import org.zttc.itat.model.Student;
import org.zttc.itat.model.Teacher;
import org.zttc.itat.model.TeacherCourse;
import org.zttc.itat.util.HibernateUtil;

public class TestManyToMany2 {
	@Test
	public void testAdd01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Teacher t1 = new Teacher();
			t1.setName("老张");
			session.save(t1);
			Teacher t2 = new Teacher();
			t2.setName("老刘");
			session.save(t2);
			Course c1 = new Course();
			c1.setName("数据结构");
			session.save(c1);
			Course c2 = new Course();
			c2.setName("计算机组成原理");
			session.save(c2);
			TeacherCourse tc1 = new TeacherCourse();
			tc1.setAch(87);
			tc1.setTeacher(t1);
			tc1.setCourse(c1);
			session.save(tc1);
			
			tc1 = new TeacherCourse();
			tc1.setAch(66);
			tc1.setTeacher(t1);
			tc1.setCourse(c2);
			session.save(tc1);
			
			tc1 = new TeacherCourse();
			tc1.setAch(190);
			tc1.setTeacher(t2);
			tc1.setCourse(c1);
			session.save(tc1);
			
			tc1 = new TeacherCourse();
			tc1.setAch(20);
			tc1.setTeacher(t2);
			tc1.setCourse(c2);
			session.save(tc1);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testLoad01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Teacher t = (Teacher)session.load(Teacher.class, 1);
			//load的时候由于延迟加载，会根据不同的情况取相应的关联对象，所以会发出大量的sql
			/**
			 * 总体来说：最佳实践就是，一般不使用双向关联，特别不建议使用一的这一方的关联
			 * 因为从一的这一端取关联对象很有可能会涉及到分页操作，所以基本不会使用
			 * 在设计的时候不是特殊情况不要使用双向关联。
			 */
			System.out.println(t.getName());
			for(TeacherCourse tc:t.getTcs()) {
				System.out.println(tc.getCourse().getName()+":"+tc.getAch());
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
}
