package curevengers.petit_cure.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByLoginId(String username);
}
