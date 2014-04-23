package org.zttc.itat.test;

import org.hibernate.Session;
import org.junit.Test;
import org.zttc.itat.model.IDCard;
import org.zttc.itat.model.Person;
import org.zttc.itat.util.HibernateUtil;

public class TestOne2One {
	@Test
	public void testAdd01() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Person p = new Person();
			p.setName("老张");
			session.save(p);
			IDCard id = new IDCard();
			id.setNo("999");
			id.setPerson(p);
			session.save(id);
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
			Person p = (Person)session.load(Person.class, 2);
			IDCard id = new IDCard();
			id.setNo("333");
			id.setPerson(p);
			session.save(id);
			/*
			 * 由于使用了unique,所以一个用户只能有一个IDCard，这里就会报错
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
	public void testAdd03() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			/*
			 * 此时，由于使用的是IDCard来维护关系(外键在哪一端就由哪一段来维护)
			 * 通过p.setIdCard就无效，所以关系不会更新
			 */
			IDCard id = new IDCard();
			id.setNo("123");
			session.save(id);
			Person p = new Person();
			p.setName("zzz");
			p.setIdCard(id);
			session.save(p);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Test
	public void testAdd04() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Person p = new Person();
			p.setName("zzzzz");
			session.save(p);
			IDCard id = new IDCard();
			id.setNo("12322");
			id.setPerson(p);
			session.save(id);
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
			Person p = (Person)session.load(Person.class, 1);
			//只要取出的是没有维护关系的这一方，会自动将关联对象取出，会发出1条sql
			//由于person端没有维护关系,所以不会进行延迟加载,所以1条就搞定了
			System.out.println(p.getName()+","+p.getIdCard().getNo());
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
			//特别注意：如果没有双向，此时会发出2条，一条去idCard，一条延迟加载取person
			//此时会发出三条SQL语句
			IDCard id = (IDCard)session.load(IDCard.class, 1);
			//此时没有使用idCard的Person，会延迟加载，目前只是发出1条SQL
			System.out.println(id.getNo());
			//要去取person同时也会取出这个person的idCard,这里就不会使用join来取出，所以会发出2条sql
			System.out.println(id.getPerson().getName());
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(session!=null) session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * 最佳实践就是，One2One的时候最好不要使用双向关联，如果使用双向关联，尽可能在没有维护关系的一边取数据
	 * hibernate会自动完成joine，仅仅只会发一条sql，如果使用维护关系端取数据，在通过延迟加载取关联对象时
	 * 会同时再去取person的idCard关联，所以会发3条
	 */
	
}
