package cgg.informatique.jfl.webSocket.dao;

import cgg.informatique.jfl.webSocket.entite.Combat;
import cgg.informatique.jfl.webSocket.entite.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CombatDao extends JpaRepository<Combat, String> {
    @Query( value =  "SELECT ISNULL(SUM(POINTS_BLANC) + (SELECT SUM(POINTS_ROUGE) FROM COMBATS WHERE ROUGE_ID=?1 AND CEINTURE_ROUGE_ID=?2),0) FROM COMBATS WHERE BLANC_ID=?1 AND CEINTURE_BLANC_ID=?2",nativeQuery = true )
    int  getPointageActuel(String username,String ceinture);

    @Query( value =  "SELECT ISNULL(SUM(CREDITS_ARBITRE) - (SELECT COUNT(*) * 10  FROM EXAMENS WHERE EVALUE_ID=?1 AND A_REUSSI='TRUE') - (SELECT COUNT(*) * 5  FROM EXAMENS WHERE EVALUE_ID=?1 AND A_REUSSI='FALSE'),0) FROM COMBATS WHERE ARBITRE_ID=?1",nativeQuery = true )
    int  getCredit(String username);

    @Query( value =  "SELECT CASE WHEN (SELECT COUNT(A_REUSSI) FROM EXAMENS WHERE EVALUE_ID=?1 AND CEINTURE_ID=?2) > 1 THEN SELECT TOP 1 NOT A_REUSSI FROM EXAMENS WHERE EVALUE_ID=?1 AND CEINTURE_ID=?2 ORDER BY DATE DESC ELSE false END",nativeQuery = true )
    boolean  estHonteux(String username,String ceinture);

    @Query( value =  "SELECT ISNULL(COUNT(*),0) FROM COMBATS WHERE ARBITRE_ID=?1",nativeQuery = true )
    int  getNombreCombat(String username);

}
