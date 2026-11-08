package ma.fstg.security;

import ma.fstg.security.entities.Role;
import ma.fstg.security.entities.User;
import ma.fstg.security.repositories.RoleRepository;
import ma.fstg.security.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringSecurityJpaApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        // Vérifier que les rôles sont créés
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role userRole = roleRepository.findByName("ROLE_USER");
        assertNotNull(adminRole, "Le rôle ROLE_ADMIN doit exister");
        assertNotNull(userRole, "Le rôle ROLE_USER doit exister");

        // Vérifier que l'utilisateur admin existe et que le mot de passe est bien haché
        Optional<User> adminOpt = userRepository.findByUsername("admin");
        assertTrue(adminOpt.isPresent(), "L'utilisateur admin doit exister");
        User admin = adminOpt.get();
        assertTrue(passwordEncoder.matches("1234", admin.getPassword()), "Le mot de passe admin doit correspondre à 1234");
        assertTrue(admin.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")));

        // Vérifier que l'utilisateur user existe
        Optional<User> userOpt = userRepository.findByUsername("user");
        assertTrue(userOpt.isPresent(), "L'utilisateur user doit exister");
        User user = userOpt.get();
        assertTrue(passwordEncoder.matches("1111", user.getPassword()), "Le mot de passe user doit correspondre à 1111");
        assertTrue(user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_USER")));
    }
}
