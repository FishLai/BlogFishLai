package me.fishlab2.blogfishlai;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class MyBlogApplicationTests {

	private EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(
			"me.fishlab2.blogfishlai.mycollection_catalog");

	private EntityManager em = emFactory.createEntityManager();

	private Logger logger = LogManager.getLogger(this.getClass());
	private Object MyCollection;

	@Test
	void contextLoads() {
		Path path = Paths.get("/springMVCDev/temp/files");
		logger.info(path.resolve("test").normalize());
	}

	/*
	 *　確認 String.replaceAll()
	 */
	@Test
	public void stringReplaceTest() {
		String s = "test the blank and symbol replace with '_'";
		String s2 = s.replaceAll("[\\W_]", "_");
		logger.info(s2);
		logger.info(new String((s2 + "/" + "filename")));
	}

	@Test
	public void useCriteria() {

		Session ss = em.unwrap(Session.class);

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MyCollection.class)
				.setProjection(Projections.rowCount())
		//		.add(Restrictions.sqlRestriction("{alias}.coll_name = 'test2'"));
				.add(Restrictions.sqlRestriction("REGEXP_REPLACE({alias}.coll_name, '[[:space:][:punct:]]', '') = (?)", "test2", StringType.INSTANCE));
		List items = detachedCriteria.getExecutableCriteria(ss).list();
		/*
		MyCollection myColl = new MyCollection();
		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<MyCollection> query = criteriaBuilder.createQuery(MyCollection.class);
		Root<MyCollection> from = query.from(MyCollection.class);
		query.select(from);
		TypedQuery<MyCollection> q = this.em.createQuery(query);
		List<MyCollection> items = q.getResultList();

		 */


		Iterator iterator = items.iterator();
		while(iterator.hasNext()) {
			logger.info(iterator.next().toString());
		}

		em.close();

	}
}
