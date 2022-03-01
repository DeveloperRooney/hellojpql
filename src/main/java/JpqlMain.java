import jpql.Member;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = new Member();
            member.setUserName("hancoding");
            member.setAge(28);
            em.persist(member);

            String query = "select " +
                    "case when m.age <= 10 then '학생 요금' " +
                    "when m.age >= 60 then '경로요금' " +
                    "else '일반요금' end " +
                    "from Member m";
            List<String> resultList = em.createQuery(query, String.class).getResultList();

            for (String s : resultList) {
                System.out.println("=========" + s + "============");
            }
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
