package curevengers.petit_cure.common.email;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "email")
public class Email {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id", unique = true, nullable = false)
    private Long id;

    // 이메일 주소
    @Column(name = "email", nullable = false)
    private String email;
    // 이메일 인증 여부
    @Column(name = "email_status", nullable = false)
    private boolean emailStatus;

    @Builder
    public Email(String email){
        this.email=email;
        this.emailStatus=false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

