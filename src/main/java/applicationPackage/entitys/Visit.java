package applicationPackage.entitys;

import applicationPackage.Procedure;
import applicationPackage.Specialist;

import javax.persistence.Entity;
import java.util.Date;

//@Entity
public class Visit {
    private Procedure procedure;
    private Date start;
    private Specialist localSpecalist;
    private int fanalPrice;


}
