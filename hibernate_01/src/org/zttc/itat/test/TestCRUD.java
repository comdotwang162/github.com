package org.zttc.itat.test;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.zttc.itat.model.User;
import org.zttc.itat.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class TestCRUD {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Test
	public void testAdd() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			
			User u = new User();
			u.setBorn(sdf.parse("1977-11-22"));
			u.setNickname("张老七");
			u.setPassword("123");
			u.setUsername("zhanglaowu");
			session.save(u);
			
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
			User u = (User)session.load(User.class,2);
			System.out.println(u);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testUpdate() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = (User)session.load(User.class, 2);
			u.setNickname("张小四");
			session.update(u);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testDelete() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setId(2);
			session.delete(u);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testList() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			List<User> users = session.createQuery("from User").list();
			for(User u:users) {
				System.out.println(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testPager() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			List<User> users = session.createQuery("from User")
								.setFirstResult(0)
								.setMaxResults(2).list();
			for(User u:users) {
				System.out.println(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}
}
