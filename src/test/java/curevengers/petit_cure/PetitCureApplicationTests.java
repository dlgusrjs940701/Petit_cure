package curevengers.petit_cure;

import curevengers.petit_cure.Dao.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetitCureApplicationTests {

    @Autowired
    MemberMapper memberMapper;

    @Test
    void contextLoads() {
        System.out.println("abc");
        System.out.println(memberMapper);
    }

}
