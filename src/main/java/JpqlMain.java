import jpql.Member;
import jpql.Team;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpqlMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUserName("hancoding");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.userName from Team t join t.members m";

            Integer memberSize = em.createQuery(query, Integer.class).getSingleResult();

            System.out.println("===========" + memberSize);

            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
