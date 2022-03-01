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

            // fetch join 대상에는 웬만하면 별칭을 주면 안 된다(관례)
            // 문제가 발생할 수 있음, 또한 JPA에서는 객체 그래프는 모두 조회하도록 설계됨
            // 둘 이상의 컬렉션은 페치 조인을 할 수 없다
            // 컬렉션을 페치 조인하면 페이징 API를 사용할 수 없다
            String query = "select t from Team t join fetch t.members m";

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
