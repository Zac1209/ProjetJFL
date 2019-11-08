package cgg.informatique.jfl.webSocket.dao;


import cgg.informatique.jfl.webSocket.entite.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompteDao extends JpaRepository<Compte, String> {
    @Query( value =  "SELECT p.username FROM Compte p WHERE p.avatar=(SELECT e FROM Avatar e WHERE e.avatar =:avatar)"  )
    String  getCompteIdByAvatar(@Param("avatar") String avatar);
}