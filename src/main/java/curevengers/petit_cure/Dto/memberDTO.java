package curevengers.petit_cure.Dto;

public class memberDTO {
    private String name;
    private String age;
    private String gender;
    private String id;
    private String pass;
    private String email;
    private String addr;
    private String grade;
    private String jumin1;
    private String jumin2;

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    private String con;
    public String getJumin1() {
        return jumin1;
    }

    public void setJumin1(String jumin1) {
        this.jumin1 = jumin1;
    }

    public String getJumin2() {
        return jumin2;
    }

    public void setJumin2(String jumin2) {
        this.jumin2 = jumin2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "memberDTO{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", id='" + id + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", addr='" + addr + '\'' +
                ", grade='" + grade + '\'' +
                ", jumin1='" + jumin1 + '\'' +
                ", jumin2='" + jumin2 + '\'' +
                ", con='" + con + '\'' +
                '}';
    }
}
