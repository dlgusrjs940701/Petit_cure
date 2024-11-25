package curevengers.petit_cure.Dao;

import curevengers.petit_cure.common.email.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailMapper extends JpaRepository<Email,Long>{

    // 인증코드 발송한 이메일 주소를 조회
    public Optional<Email> findByEmail(String email);
}
