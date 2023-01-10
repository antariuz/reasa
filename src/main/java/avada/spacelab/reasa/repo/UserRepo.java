package avada.spacelab.reasa.repo;

import avada.spacelab.reasa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);

    Optional<User> findByEmail(String email);

    User findUserByEmail(String email);

    @Query("select b.email from refresh_token rt join rt.buyer b where rt.token = :token")
    String findUserEmailByToken(@Param("token") String token);

    @Modifying
    @Transactional
    @Query("Update User u set " +
            "u.provider = :provider " +
            "where u.email like :email")
    void updateUser(
            @Param("email") String email,
            @Param("provider") User.Provider provider);

    @Modifying
    @Transactional
    @Query("Update UserProfile up set " +
            "up.nickname = :name, " +
            "up.fullName = :fullName " +
            "where up = (select u.userProfile from User u where u.email like :email)")
    void updateUserProfile(
            @Param("email") String email,
            @Param("name") String name,
            @Param("fullName") String fullName);
}
