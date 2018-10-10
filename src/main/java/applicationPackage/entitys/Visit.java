package applicationPackage.entitys;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Procedure procedure;

    private Date start;

    @OneToMany(mappedBy = "visit")
    private List<Specialist> localSpecalist = new ArrayList<>();

    private Integer fanalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public List<Specialist> getLocalSpecalist() {
        return localSpecalist;
    }

    public void setLocalSpecalist(List<Specialist> localSpecalist) {
        this.localSpecalist = localSpecalist;
    }

    public Integer getFanalPrice() {
        return fanalPrice;
    }

    public void setFanalPrice(Integer fanalPrice) {
        this.fanalPrice = fanalPrice;
    }
}
