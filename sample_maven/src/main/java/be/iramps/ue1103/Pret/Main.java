package be.iramps.ue1103.Pret;

public class Main {
    public static void main(String[] args) throws Exception {
            Projet projet = new Projet();
            projet.setPrixHabitation(89918.87);
            projet.setFraisNotaireAchat(5206.48);
            projet.setRevenuCadastral(4922);
            projet.setFraisTransformation(19952.78);

        System.out.printf("Le total du projet est : %.2f%n", projet.calculTotalProjetAchat());

                    projet.setPrixHabitation(89918.87);
                    projet.setFraisNotaireAchat(5206.48);
                    projet.setRevenuCadastral(4922);
                    projet.setFraisTransformation(19952.78);

            System.out.println(projet.calculTotalProjetAchat());
    }
}
