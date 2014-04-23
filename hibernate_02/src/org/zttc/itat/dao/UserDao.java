package org.zttc.itat.dao;

import org.hibernate.Session;
import org.zttc.itat.model.User;
import org.zttc.itat.util.HibernateUtil;

public class UserDao {
	public void update(User u) {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User tu = (User)session.load(User.class, 1);
			tu.setBorn(u.getBorn());
			tu.setNickname(u.getNickname());
			tu.setPassword(u.getPassword());
			tu.setUsername(u.getUsername());
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	public User load(int id) {
		Session session = null;
		User u = null;
		try {
			session = HibernateUtil.openSession();
			u = (User)session.load(User.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
		return u;
	}
}
