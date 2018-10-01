package applicationPackage.entitys;

import applicationPackage.Enums.Proffession;

import javax.persistence.*;

@Entity
public class Specialist {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Proffession proffession;

    @ManyToOne
    private Visit visit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proffession getProffession() {
        return proffession;
    }

    public void setProffession(Proffession proffession) {
        this.proffession = proffession;
    }

    public Specialist(String name) {
        this.name = name;
    }
    public Specialist() {

    }
}
