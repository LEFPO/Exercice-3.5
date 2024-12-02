import be.iramps.ue1103.Pret.Pret;
import be.iramps.ue1103.Pret.Projet;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

@DisplayName("Test de la classe Projet")
public class TestProjet {

    private static Projet mockedProjet;
    private static Projet projet;
    private static Pret pret;

    @BeforeAll
    public static void setup() {
        projet = new Projet();
        TestProjet.mockedProjet = Mockito.spy(projet);
        pret = new Pret();
    }
    @Disabled
    @Nested
     class calculTVAFraisTransformation {
         @Test
         @DisplayName("Calcul de la tva sur les frais de transformation: Validation simple")
         public void calculTVAFraisTransformationSimple() {
             // Test de valeurs simples, le test doit les vérifier toutes
             Assertions.assertAll(
                     () -> {
                         projet.setFraisTransformation(90_000.00);
                         Assertions.assertEquals(5_400.00, projet.calculTVAFraisTransformation(), 0.001);
                     },
                     () -> {
                         projet.setFraisTransformation(0.00);
                         Assertions.assertEquals(0.00, projet.calculTVAFraisTransformation(),0.001);
                     },
                     () -> {
                         projet.setFraisTransformation(100_000.00);
                         Assertions.assertEquals(6_000.00, projet.calculTVAFraisTransformation(),0.001);
                     },
                     () -> {
                         projet.setFraisTransformation(59_595.00);
                         Assertions.assertEquals(3_575.70, projet.calculTVAFraisTransformation(),0.001);
                     },
                     () -> {
                         projet.setFraisTransformation(1.00);
                         Assertions.assertEquals(0.06, projet.calculTVAFraisTransformation(),0.001);
                     }
             );
         }


         @Test
         @DisplayName("Calcul de la tva sur les frais de transformation: Validation des frais arrondi")
         public void calculTVAFraisTransformationArrondi() {
             // Test de probleme d'arrondis
             projet.setFraisTransformation(92_123.89);
             Assertions.assertEquals(5_527.44, projet.calculTVAFraisTransformation());
         }

         @ParameterizedTest
         @ValueSource(doubles = {-90_000.00, -25_000.00})
         @DisplayName("Calcul de la tva sur les frais de transformation: Validation des frais negatifs")
         public void calculTVAFraisTransformationNegatif(double fraisTransformation) {
             // Test de valeurs negatives
             projet.setFraisTransformation(fraisTransformation);
             Assertions.assertThrows(Exception.class, () -> projet.calculTVAFraisTransformation());
         }
     }

     @Nested
     @DisplayName("Calcul du droit d'enregistrement")
     class calculDroitEnregistrement {
         @Test
         public void calculDroitEnregistrementRevenuCadastralInferieur745() {
             Assertions.assertAll(() -> {
                 mockedProjet.setPrixHabitation(350_000.00);
                 Mockito.doReturn(40_000.00).when(mockedProjet).calculAbattement();
                 mockedProjet.setRevenuCadastral(740);
                 Assertions.assertEquals(18_600.00, mockedProjet.calculDroitEnregistrement());
                 }
             );
         }
     }
     @Disabled
     @Nested
     @DisplayName("Calcul du coût total d'emprunt à remboursé après calcul des interets")
     class  calculTotalProjet{
         @Test
         public void calculTotalProjetSimple(){
             Assertions.assertAll(
                 () -> {
                     pret.setFraisDossierBancaire(500);
                     pret.setFraisNotaireCredit(4500);
                     pret.setNombreAnnees(15);
                     pret.setTauxAnnuel(0.05);
                     Assertions.assertEquals(146303.74, pret.calculTotalProjet(100_000), 0.01);
                 },
                 () -> {
                     pret.setFraisDossierBancaire(600);
                     pret.setFraisNotaireCredit(5400);
                     pret.setNombreAnnees(20);
                     pret.setTauxAnnuel(0.035);
                     Assertions.assertEquals(172216.75, pret.calculTotalProjet(120_000), 0.01);    
                 },
                 () -> {
                     pret.setFraisDossierBancaire(750);
                     pret.setFraisNotaireCredit(6750);
                     pret.setNombreAnnees(25);
                     pret.setTauxAnnuel(0.025);
                     Assertions.assertEquals(208739.12, pret.calculTotalProjet(150_000), 0.01);    
                 },
                 () -> {
                     pret.setFraisDossierBancaire(1000);
                     pret.setFraisNotaireCredit(9000);
                     pret.setNombreAnnees(30);
                     pret.setTauxAnnuel(0.018);
                     Assertions.assertEquals(268463.13, pret.calculTotalProjet(200_000), 0.01);    
                 }
             );
         }        
     }
    @Nested
    @DisplayName("Calcul Du total € pour le projet")
    class  calculTotalProjetAchat{
        @Test
        public void calculTotalProjetAchatSimple(){
            Assertions.assertAll(
                () -> {
                    projet.setPrixHabitation(100_000);
                    projet.setFraisNotaireAchat(28000);
                    projet.setRevenuCadastral(1345);
                    projet.setFraisTransformation(0);
                    Mockito.doReturn(7500).when(mockedProjet).calculDroitEnregistrement();
                    Assertions.assertEquals(135500, projet.calculTotalProjetAchat(),0.001);
                },
                () -> {
                    projet.setPrixHabitation(197616);
                    projet.setFraisNotaireAchat(23423);
                    projet.setRevenuCadastral(1749);
                    projet.setFraisTransformation(13444);
                    Assertions.assertEquals(254991.64, projet.calculTotalProjetAchat(),0.001);
                },
                () -> {
                    projet.setPrixHabitation(172604);
                    projet.setFraisNotaireAchat(31563);
                    projet.setRevenuCadastral(4048);
                    projet.setFraisTransformation(9670);
                    Assertions.assertEquals(230992.70, projet.calculTotalProjetAchat(),0.001);
                },
                () -> {
                    projet.setPrixHabitation(163398);
                    projet.setFraisNotaireAchat(49182);
                    projet.setRevenuCadastral(4030);
                    projet.setFraisTransformation(1649);
                    Assertions.assertEquals(229752.69, projet.calculTotalProjetAchat(),0.001);
                },
                () -> {
                    projet.setPrixHabitation(0);
                    projet.setFraisNotaireAchat(0);
                    projet.setRevenuCadastral(0);
                    projet.setFraisTransformation(0);
                    Assertions.assertEquals(Exception.class, projet.calculTotalProjetAchat());
                }
            );
        }
        @Test
        @DisplayName("Calcul total projet achat - Scénario Positif")
        public void calculTotalProjetAchatSimpleAvecMockito() {
            Mockito.doReturn(7500.0).when(mockedProjet).calculDroitEnregistrement();

            mockedProjet.setPrixHabitation(100_000);
            mockedProjet.setFraisNotaireAchat(28_000);
            mockedProjet.setRevenuCadastral(1345);
            mockedProjet.setFraisTransformation(0);

            Assertions.assertEquals(135500.0, mockedProjet.calculTotalProjetAchat(), 0.001);

            Mockito.verify(mockedProjet).calculDroitEnregistrement();
        }

        @Test
        public void calculTotalProjetAchatNegatif(){
            Assertions.assertAll(
                () -> {
                    projet.setPrixHabitation(153768);
                    projet.setFraisNotaireAchat(12415);
                    projet.setRevenuCadastral(3632);
                    projet.setFraisTransformation(-16080);
                    Assertions.assertEquals(Exception.class, projet.calculTotalProjetAchat());
                },
                () -> {
                    projet.setPrixHabitation(-89918);
                    projet.setFraisNotaireAchat(5206);
                    projet.setRevenuCadastral(-4922);
                    projet.setFraisTransformation(-19952);
                    Assertions.assertEquals(Exception.class, projet.calculTotalProjetAchat());
                },
                () -> {
                    projet.setPrixHabitation(43622);
                    projet.setFraisNotaireAchat(5217);
                    projet.setRevenuCadastral(-3861);
                    projet.setFraisTransformation(-16745);
                    Assertions.assertEquals(Exception.class, projet.calculTotalProjetAchat());
                },
                () -> {
                    projet.setPrixHabitation(40863);
                    projet.setFraisNotaireAchat(-6038);
                    projet.setRevenuCadastral(3043);
                    projet.setFraisTransformation(9882);
                    Assertions.assertEquals(Exception.class, projet.calculTotalProjetAchat());
                },
                () -> {
                    projet.setPrixHabitation(139749);
                    projet.setFraisNotaireAchat(18353);
                    projet.setRevenuCadastral(-4508);
                    projet.setFraisTransformation(16639);
                    Assertions.assertEquals(Exception.class, projet.calculTotalProjetAchat());
                }
            );
        }
        @Test
        public void calculTotalProjetAchatArrondi(){
            Assertions.assertAll(
                () -> {
                    projet.setPrixHabitation(153768.56);
                    projet.setFraisNotaireAchat(12415.45);
                    projet.setRevenuCadastral(3632);
                    projet.setFraisTransformation(16080.48);
                    Assertions.assertEquals(197450.39, projet.calculTotalProjetAchat(),0.01);
                },
                () -> {
                    projet.setPrixHabitation(89918.87);
                    projet.setFraisNotaireAchat(5206.48);
                    projet.setRevenuCadastral(4922);
                    projet.setFraisTransformation(19952.78);
                    Assertions.assertEquals(122515.16, projet.calculTotalProjetAchat(),0.01);
                }
            );
        }
        @Test
        @DisplayName("Calcul total projet achat - Cas d'Arrondi")
        public void calculTotalProjetAchatArrondiAvecMockito() {
            Mockito.doReturn(12_415.45).when(mockedProjet).calculDroitEnregistrement();

            mockedProjet.setPrixHabitation(153_768.56);
            mockedProjet.setFraisNotaireAchat(12_415.45);
            mockedProjet.setRevenuCadastral(3_632);
            mockedProjet.setFraisTransformation(16_080.48);

            Assertions.assertEquals(197450.39, mockedProjet.calculTotalProjetAchat(), 0.01);

            Mockito.verify(mockedProjet).calculDroitEnregistrement();
        }
    }
    @Nested
    @DisplayName("Calcul de l'apport minimal pour l'achat d'un bien")
    class calculApportMinimal{
        @Test
        public void calculApportMinimalSimple(){
            Assertions.assertAll(
                () -> {
                    projet.setPrixHabitation(110_000);
                    projet.setFraisNotaireAchat(28000);
                    projet.setRevenuCadastral(1345);
                    projet.setFraisTransformation(0);
                    Assertions.assertEquals(47500, projet.calculApportMinimal(),0.01);
                },
                () -> {
                    projet.setPrixHabitation(197616);
                    projet.setFraisNotaireAchat(23423);
                    projet.setRevenuCadastral(1749);
                    projet.setFraisTransformation(13444);
                    Assertions.assertEquals(64311.66, projet.calculApportMinimal(),0.01);
                },
                () -> {
                    projet.setPrixHabitation(172604);
                    projet.setFraisNotaireAchat(31563);
                    projet.setRevenuCadastral(4048);
                    projet.setFraisTransformation(9670);
                    Assertions.assertEquals(66423.92, projet.calculApportMinimal(),0.01);
                },
                () -> {
                    projet.setPrixHabitation(163398);
                    projet.setFraisNotaireAchat(49182);
                    projet.setRevenuCadastral(4030);
                    projet.setFraisTransformation(1649);
                    Assertions.assertEquals(81121.34, projet.calculApportMinimal(),0.01);
                },
                () -> {
                    projet.setPrixHabitation(0);
                    projet.setFraisNotaireAchat(0);
                    projet.setRevenuCadastral(0);
                    projet.setFraisTransformation(0);
                    Assertions.assertEquals(Exception.class, projet.calculApportMinimal());
                }
            );
        }
        @Test
        @DisplayName("Calcul apport minimal - Scénario Positif")
        public void calculApportMinimalAvecMockito() {
            Mockito.doReturn(5000.0).when(mockedProjet).calculDroitEnregistrement();

            // Set des valeurs
            mockedProjet.setPrixHabitation(100_000);
            mockedProjet.setFraisNotaireAchat(28_000);
            mockedProjet.setRevenuCadastral(1345);
            mockedProjet.setFraisTransformation(0);

            Assertions.assertEquals(47_500.0, mockedProjet.calculApportMinimal(), 0.01);

            Mockito.verify(mockedProjet).calculDroitEnregistrement();
        }

        @Test
        public void calculTotalProjetAchatNegatif(){
            Assertions.assertAll(
                () -> {
                    projet.setPrixHabitation(153768);
                    projet.setFraisNotaireAchat(12415);
                    projet.setRevenuCadastral(3632);
                    projet.setFraisTransformation(-16080);
                    Assertions.assertEquals(Exception.class, projet.calculApportMinimal());
                },
                () -> {
                    projet.setPrixHabitation(-89918);
                    projet.setFraisNotaireAchat(5206);
                    projet.setRevenuCadastral(-4922);
                    projet.setFraisTransformation(-19952);
                    Assertions.assertEquals(Exception.class, projet.calculApportMinimal());
                },
                () -> {
                    projet.setPrixHabitation(43622);
                    projet.setFraisNotaireAchat(5217);
                    projet.setRevenuCadastral(-3861);
                    projet.setFraisTransformation(-16745);
                    Assertions.assertEquals(Exception.class, projet.calculApportMinimal());
                },
                () -> {
                    projet.setPrixHabitation(40863);
                    projet.setFraisNotaireAchat(-6038);
                    projet.setRevenuCadastral(3043);
                    projet.setFraisTransformation(9882);
                    Assertions.assertEquals(Exception.class, projet.calculApportMinimal());
                },
                () -> {
                    projet.setPrixHabitation(139749);
                    projet.setFraisNotaireAchat(18353);
                    projet.setRevenuCadastral(-4508);
                    projet.setFraisTransformation(16639);
                    Assertions.assertEquals(Exception.class, projet.calculApportMinimal());
                }
            );
        }
        @Test
        public void calculApportMinimalArrondi(){
            Assertions.assertAll(
                () -> {
                    projet.setPrixHabitation(89918.87);
                    projet.setFraisNotaireAchat(5206.48);
                    projet.setRevenuCadastral(4922);
                    projet.setFraisTransformation(19952.78);
                    Assertions.assertEquals(22553.22, projet.calculApportMinimal(),0.01); 
                },
                () -> {
                    projet.setPrixHabitation(153768.56);
                    projet.setFraisNotaireAchat(12415.45);
                    projet.setRevenuCadastral(3632);
                    projet.setFraisTransformation(16080.48);
                    Assertions.assertEquals(43717.91, projet.calculApportMinimal(),0.01); 
                }
            );
        }
    }
}