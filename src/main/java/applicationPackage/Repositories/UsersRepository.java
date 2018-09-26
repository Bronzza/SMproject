package applicationPackage.Repositories;

import applicationPackage.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository <User, Long> {

}
