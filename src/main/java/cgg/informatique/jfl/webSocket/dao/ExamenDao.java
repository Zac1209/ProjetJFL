package cgg.informatique.jfl.webSocket.dao;

import cgg.informatique.jfl.webSocket.entite.Examen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamenDao extends JpaRepository<Examen, String> {
}
