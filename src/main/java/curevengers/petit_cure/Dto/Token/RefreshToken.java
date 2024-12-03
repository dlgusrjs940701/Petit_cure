package curevengers.petit_cure.Dto.Token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @Column(name="RT_KEY")
    private String key;     // member id 들어갈 변수

    @Column(name = "RT_VALUE")
    private String value;   // token값 들어갈 변수

    public RefreshToken updeateValue(String token) {
        this.value = token;
        return this;
    }
}
