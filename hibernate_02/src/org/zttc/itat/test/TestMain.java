package org.zttc.itat.test;

import org.hibernate.Session;
import org.zttc.itat.model.Admin;
import org.zttc.itat.model.Role;
import org.zttc.itat.util.HibernateUtil;

public class TestMain {
	public static void main(String[] args) {
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
}
