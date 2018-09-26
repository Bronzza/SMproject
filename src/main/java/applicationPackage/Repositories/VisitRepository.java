package applicationPackage.Repositories;

import applicationPackage.entitys.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
