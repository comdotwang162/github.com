package org.zttc.itat.test;

import java.util.Date;

import org.hibernate.Session;
import org.junit.Test;
import org.zttc.itat.dao.UserDao;
import org.zttc.itat.model.User;
import org.zttc.itat.util.HibernateUtil;

public class TestLazy {
	@Test
	public void testLazy01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			User u = (User)session.load(User.class, 1);
			//此时一条sql都没有发，这就是hibernate的延迟加载
			/**
			 * 延迟加载指的就是，当完成load操作之后，并不会马山发出sql语句，只有在使用到该对象时才会发出sql
			 * 当完成load之后，u其实是一个代理对象，这个代理对象中仅仅只有一个id的值
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testLazy02() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			User u = (User)session.load(User.class, 1);
			//此时一条sql都没有发，这就是hibernate的延迟加载
			/**
			 * 延迟加载指的就是，当完成load操作之后，并不会马山发出sql语句，只有在使用到该对象时才会发出sql
			 * 当完成load之后，u其实是一个代理对象，这个代理对象中仅仅只有一个id的值
			 */
			//此时不会发sql
			System.out.println(u.getId());
			//nickname没有值，必须去数据库中取。所以会发出sql
			System.out.println(u.getNickname());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testLazy03() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			//get是只要一执行就会发出sql，get没有延迟加载
			User u = (User)session.get(User.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testLazy04() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			//get是只要一执行就会发出sql，get没有延迟加载
			User u = (User)session.get(User.class, 101);
			//此时由于取了这个数据，发现数据库中并没有该数据，所以u是null，所以会抛出空指针异常
			System.out.println(u.getId());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testLazy05() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			User u = (User)session.load(User.class, 101);
			//由于id已经存在，所以不会抛出异常
			System.out.println(u.getId());
			//此时会去数据库中取数据，发现没有这个对象，但是u并不是空，所以会抛出ObjectNotFoundException
			System.out.println(u.getNickname());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testLazyQuestion() {
		UserDao ud = new UserDao();
		/*
		 * 由于使用了load，load是有延迟加载的，返回的时候的u是一个代理对象，仅仅只有一个id
		 * 但是在返回的时候Session已经被关闭了，此时当需要使用u的其他属性时就需要去数据库中
		 * 但是Session关闭了，所以就抛出org.hibernate.LazyInitializationException no session
		 */
		User u = ud.load(2);
		System.out.println(u.getUsername());
	}
}
