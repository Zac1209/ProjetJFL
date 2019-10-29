package cgg.informatique.jfl.webSocket.dao;

import cgg.informatique.jfl.webSocket.entite.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, String> {
}
