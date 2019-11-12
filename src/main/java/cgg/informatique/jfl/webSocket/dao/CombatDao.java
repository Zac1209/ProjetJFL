package cgg.informatique.jfl.webSocket.dao;

import cgg.informatique.jfl.webSocket.entite.Combat;
import cgg.informatique.jfl.webSocket.entite.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CombatDao extends JpaRepository<Combat, String> {
}
