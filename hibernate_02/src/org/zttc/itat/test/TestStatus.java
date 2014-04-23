package org.zttc.itat.test;

import java.text.SimpleDateFormat;

import org.hibernate.Session;
import org.junit.Test;
import org.zttc.itat.model.User;
import org.zttc.itat.util.HibernateUtil;

public class TestStatus {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Test
	public void testTransient() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setBorn(sdf.parse("1976-2-3"));
			u.setUsername("zxl");
			u.setNickname("赵晓六");
			u.setPassword("123");
			//以上u就是Transient（瞬时状态），表示没有被session管理并且数据库中没有
			//执行save之后，被session所管理，而且，数据库中已经存在，此时就是Persistent状态
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
	public void testPersistent01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setBorn(sdf.parse("1976-2-3"));
			u.setUsername("zxq");
			u.setNickname("赵晓七");
			u.setPassword("123");
			//以上u就是Transient（瞬时状态），表示没有被session管理并且数据库中没有
			//执行save之后，被session所管理，而且，数据库中已经存在，此时就是Persistent状态
			session.save(u);
			//此时u是持久化状态，已经被session所管理，当在提交时，会把session中的对象和目前的对象进行比较
			//如果两个对象中的值不一致就会继续发出相应的sql语句
			u.setNickname("赵晓其");
			//此时会发出2条sql，一条用户做插入，一条用来做更新
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testPersistent02() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setBorn(sdf.parse("1976-2-3"));
			u.setUsername("zxq");
			u.setNickname("赵晓八");
			u.setPassword("123");
			session.save(u);
			u.setPassword("222");
			//该条语句没有意义
			session.save(u);
			u.setNickname("赵晓吧");
			//没有意义
			session.update(u);
			u.setBorn(sdf.parse("1988-12-22"));
			//没有意义
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
	public void testPersistent03() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setBorn(sdf.parse("1976-2-3"));
			u.setUsername("zxq");
			u.setNickname("赵晓九");
			u.setPassword("123");
			session.save(u);
			/*
			 * 以下三条语句没有任何意义
			 */
			session.save(u);
			session.update(u);
			session.update(u);
			u.setUsername("zxj");
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testPersistent04() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			//此时u是Persistent
			User u = (User)session.load(User.class, 10);
			//由于u这个对象和session中的对象不一致，所以会发出sql完成更新
			u.setUsername("aaa");
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testPersistent05() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			//此时u是Persistent
			User u = (User)session.load(User.class, 10);
			session.getTransaction().commit();
			session.beginTransaction();
			u.setUsername("123");
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testPersistent06() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			//此时u是Persistent
			User u = (User)session.load(User.class, 11);
			u.setUsername("123");
			//清空session
			session.clear();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testDetach01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setId(10);
			u.setNickname("abc");
			//当执行save的时候总是会添加一条数据，此时id就会根据Hibernate所定义的规则来生成
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
	public void testDetach02() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setId(10);
			//完成update之后也会变成持久化状态
			session.update(u);
			u.setBorn(sdf.parse("1998-12-22"));
			u.setNickname("aaa");
			u.setUsername("111");
			//会发出一条sql
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
	public void testDetach03() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setId(10);
			//完成update之后也会变成持久化状态
			session.update(u);
			u.setBorn(sdf.parse("1998-12-22"));
			u.setNickname("aaa");
			u.setUsername("111");
			//会抛出异常
			u.setId(333);
			//会发出一条sql
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testDetach04() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
			u.setId(10);
			//现在u就是transient对象
			session.delete(u);
			//此时u已经是瞬时对象，不会被session和数据库所管理
			u.setNickname("abc");
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testDetach05() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			User u = new User();
//			u.setId(110);
			u.setNickname("abc");
			//如果u是离线状态就执行update操作，如果是瞬时状态就执行Save操作
			//但是注意：该方法并不常用
			session.saveOrUpdate(u);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
	
	@Test
	public void testDetach06() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			//u1已经是持久化状态
			User u1 = (User)session.load(User.class, 11);
			System.out.println(u1.getNickname());
			//u2是离线状态
			User u2 = new User();
			u2.setId(11);
			u2.setPassword("12223");
			//此时u2将会变成持久化状态，在session的缓存中就存在了两份同样的对象,在session中不能存在两份拷贝，否则会抛出异常
//			session.saveOrUpdate(u2);
			//merge方法会判断session中是否已经存在同一个对象，如果存在就将两个对象合并
			session.merge(u2);
			//最佳实践：merge一般不用
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
		
	}
}
