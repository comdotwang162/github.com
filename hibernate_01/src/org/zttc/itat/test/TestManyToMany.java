package org.zttc.itat.test;

import org.hibernate.Session;
import org.junit.Test;
import org.zttc.itat.model.Admin;
import org.zttc.itat.model.Classroom;
import org.zttc.itat.model.Role;
import org.zttc.itat.model.Student;
import org.zttc.itat.util.HibernateUtil;

public class TestManyToMany {
	/*
	 * 使用Many2Many不论在哪一方来维护关系都比较的麻烦，而且很多时候关联表中需要加入其他的属性
	 * 所以在开发中，经常使用两个一对多来替代多对多
	 */
	@Test
	public void testAdd01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Admin a1 = new Admin();
			a1.setName("张三");
			session.save(a1);
			Admin a2 = new Admin();
			a2.setName("李四");
			session.save(a2);
			Role r1= new Role();
			r1.setName("超级管理员");
			r1.add(a1);
			session.save(r1);
			Role r2 = new Role();
			r2.setName("财务管理人员");
			r2.add(a1);
			r2.add(a2);
			session.save(r2);
			
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
			Admin a = (Admin)session.load(Admin.class, 1);
			System.out.println(a.getName());
			for(Role r:a.getRoles()) {
				System.out.println(r.getName());
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
