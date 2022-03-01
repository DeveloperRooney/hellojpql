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
            Team teamA = new Team();
            teamA.setName("TeamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("TeamB");
            em.persist(teamB);

            Member memberA = new Member();
            memberA.setUserName("MemberA");
            memberA.setTeam(teamA);
            em.persist(memberA);

            Member memberB = new Member();
            memberB.setUserName("MemberB");
            memberB.setTeam(teamA);
            em.persist(memberB);

            Member memberC = new Member();
            memberC.setUserName("MemberC");
            memberC.setTeam(teamB);
            em.persist(memberC);

            em.flush();
            em.clear();

            String query = "select distinct t from Team t join fetch t.members";

            List<Team> members = em.createQuery(query, Team.class).getResultList();

            for (Team team : members) {
                System.out.println("team = " + team.getName() + " | Member Size : " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("--> Member = " + member.getUserName());
                }
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
