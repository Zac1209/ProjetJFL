package cgg.informatique.jfl.webSocket.dao;


import cgg.informatique.jfl.webSocket.entite.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteDao extends JpaRepository<Compte, String> {
}