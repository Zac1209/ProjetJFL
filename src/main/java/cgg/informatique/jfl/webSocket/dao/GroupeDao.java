package cgg.informatique.jfl.webSocket.dao;

import cgg.informatique.jfl.webSocket.entite.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeDao extends JpaRepository<Groupe, String> {
}
