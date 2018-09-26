package applicationPackage.Repositories;

import applicationPackage.entitys.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureRepository extends JpaRepository <Procedure, Long> {
}
