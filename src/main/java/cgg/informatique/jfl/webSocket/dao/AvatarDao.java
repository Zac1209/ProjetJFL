package cgg.informatique.jfl.webSocket.dao;

import cgg.informatique.jfl.webSocket.entite.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarDao extends JpaRepository<Avatar, String> {
}
