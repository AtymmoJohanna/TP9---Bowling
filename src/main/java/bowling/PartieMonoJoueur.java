package bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe a pour but d'enregistrer le nombre de quilles abattues lors des
 * lancers successifs d'<b>un seul et même</b> joueur, et de calculer le score
 * final de ce joueur
 */
public class PartieMonoJoueur {

	/**
	 * Constructeur
	 */
	private List<Tour> tours;  // Liste des tours effectués
	private int numtourCourant;  // Le numéro du tour courant (de 1 à 10)
	private boolean partieTerminee;  // Indicateur pour savoir si la partie est terminée


	public PartieMonoJoueur() {
		this.tours = new ArrayList<>();
		this.numtourCourant = 1;  // Partie commence au tour 1
		this.partieTerminee = false;  
	}

	/**
	 * Cette méthode doit être appelée à chaque lancer de boule
	 *
	 * @param nombreDeQuillesAbattues le nombre de quilles abattues lors de ce lancer
	 * @throws IllegalStateException si la partie est terminée
	 * @return vrai si le joueur doit lancer à nouveau pour continuer son tour, faux sinon	
	 */
	public boolean enregistreLancer(int nombreDeQuillesAbattues) {

		if (partieTerminee) {
			throw new IllegalStateException("La partie est déjà terminée.");
		}

		// Si c'est un nouveau tour, crée un nouveau tour
		if (tours.size() < numtourCourant) {
			tours.add(new Tour());  // Crée un tour pour chaque nouveau tour
		}

		// Enregistre le lancer pour le tour courant
		Tour tourContinue = tours.get(numtourCourant - 1);
		boolean retour = tourContinue.faireLancer(nombreDeQuillesAbattues);

		// Si le tour est terminé, passe au tour suivant
		if (!tourContinue.estTermine()) {
			numtourCourant++;
		}

		// Si la partie est terminée (10 tours joués), on marque la fin de la partie
		if (numtourCourant > 10) {
			partieTerminee = true;
		}

		return retour;
	}

	/**
	 * Cette méthode donne le score du joueur.
	 * Si la partie n'est pas terminée, on considère que les lancers restants
	 * abattent 0 quille.
	 * @return Le score du joueur
	 */
	public int score() {

		int scoreTotal = 0;

		for (int i = 0; i < tours.size(); i++) {
			scoreTotal += tours.get(i).getScore();

			// Si c'est un spare ou un strike, ajouter le bonus du tour suivant
			if (i < tours.size() - 1) {
				// Ajouter le score du prochain lancer si c'est un spare
				if (tours.get(i).getScore() == 10) {
					if (tours.get(i + 1).getQuillesParLancer().length > 0) {
						scoreTotal += tours.get(i + 1).getQuillesParLancer()[0];  // Bonus du 1er lancer du tour suivant
					}
				}
			}

			// Ajouter les deux prochains lancers dans le cas d'un strike
			if (i < tours.size() - 1) {
				if (tours.get(i).getScore() == 10) {
					scoreTotal += tours.get(i + 1).getScore();
				}
			}
		}

		return scoreTotal;
	}

	/**
	 * @return vrai si la partie est terminée pour ce joueur, faux sinon
	 */
	public boolean estTerminee() {
		return partieTerminee;
	}

	/**
	 * @return Le numéro du tour courant [1..10], ou 0 si le jeu est fini
	 */
	public int numeroTourCourant() {
		if (numtourCourant > 10) {
			return 0;  // Retourner 0 si la partie est terminée (tour > 10)
		} else {
			return numtourCourant;  // Retourner le tour courant
		}
	}
	/**
	 * @return Le numéro du prochain lancer pour tour courant [1..3], ou 0 si le jeu
	 *         est fini
	 */
	public int numeroProchainLancer() {
		if (partieTerminee) {
			return 0;
		}
		Tour tourActuel = tours.get(numtourCourant - 1);
		if (tourActuel.getQuillesParLancer()[0] == 10) {
			// Si c'est un strike, il n'y a qu'un seul lancer
			return 1;
		} else if (tourActuel.getQuillesParLancer()[1] == 0) {
			// Si un premier lancer est effectué, le deuxième lancer est attendu
			return 2;
		} else {
			return 0;
		}
	}

}
