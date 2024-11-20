import org.junit.jupiter.api.*;
import be.iramps.ue1103.Pret.*;
@Disabled
@DisplayName("Tests d'intégration: ensemble des composants")
public class ITApp {
    @Test
    @DisplayName("Validation du meilleur scénario")
    public void happyPath (){
        Projet projet = new Projet();
        projet.setPrixHabitation(100_000);
        projet.setRevenuCadastral(700);
        projet.setFraisNotaireAchat(4_150);
        projet.setFraisTransformation(60_000);
        double apportPersonnel = projet.calculApportMinimal();
        double montantEmprunt = projet.calculResteAEmprunter();
        Pret pret = new Pret();
        pret.setFraisDossierBancaire(500);
        pret.setFraisNotaireCredit(5_475);
        pret.setNombreAnnees(15);
        pret.setTauxAnnuel(0.04);
        double apportBancaire = pret.calculRestantDu(montantEmprunt);
        Assertions.assertEquals((apportBancaire + apportPersonnel), 30084.99,0.01);   
    }
    @Test
    @DisplayName("Validation d'un sénario type")
    public void ProjetAchat(){
        Projet projet = new Projet();
        projet.setPrixHabitation(100_000);
        projet.setFraisNotaireAchat(28000);
        projet.setRevenuCadastral(1345);
        projet.setFraisTransformation(0);
        Assertions.assertEquals(135500, projet.calculTotalProjetAchat());
    }
}
