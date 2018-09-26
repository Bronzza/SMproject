package applicationPackage.Repositories;

import applicationPackage.entitys.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialistRepository extends JpaRepository <Specialist, Long> {
}
