package org.zttc.itat.test;

import org.hibernate.Session;
import org.junit.Test;
import org.zttc.itat.model.Book;
import org.zttc.itat.util.HibernateUtil;

public class TestIDGen {

	@Test
	public void testAssign() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Book b = new Book();
			b.setName("Thinking in java");
			b.setBookPage(11);
			b.setPrice(12.2);
			session.save(b);
			//第二次运行就抛出异常，因为id为0，而id需要由开发人员自己指定
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.close(session);
		}
	}
}
