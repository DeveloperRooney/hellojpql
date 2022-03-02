package jpql;

import javax.persistence.*;

@Entity
@NamedQuery(
        name = "Member.findByUserName",
        query = "select m from Member m where m.userName =: userName"
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        if(this.team != null) {
            team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }
}
