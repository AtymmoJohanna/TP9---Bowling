package bowling;

public class Tour {
	private final int numTour;
	private static int nbCompte=0;
	private int[] quillesParLancer;
	private int numLancer = 0;

	private boolean estTermine;

	public Tour() {
		nbCompte++;
		this.numTour=nbCompte;
		quillesParLancer= new int[2];
		estTermine = false;
	}

	public boolean faireLancer(int nombreDeQuillesAbattues) {
		if (estTermine) {
			//throw new IllegalStateException("Le tour est déjà terminé.");
			return false;
		}
		
		if (nombreDeQuillesAbattues < 0 || nombreDeQuillesAbattues > 10) {
			//throw new IllegalArgumentException("Le nombre de quilles abattues doit être compris entre 0 et 10.");
			return false;
		}

		// Si c'est le premier lancer
		if (numLancer == 0){
			quillesParLancer[0] = nombreDeQuillesAbattues;  
			numLancer++;
			if(nombreDeQuillesAbattues == 10){
				estTermine = true;
			}
		}
		
		// Si c'est le second lancer 
		if (numLancer == 1) {
			int reste = 10-quillesParLancer[0];
			if (nombreDeQuillesAbattues > reste) {
				throw new IllegalArgumentException("Le nombre de quilles abattues doit être inférieur ou égal à "+ reste);
			}
			quillesParLancer[1] = nombreDeQuillesAbattues;
			estTermine = true;
		}

		return true;  // Le tour continue après le premier lancer
	}


	public boolean estTermine() {
		return estTermine;
	}

	public int[] getQuillesParLancer() {
		return quillesParLancer;
	}

	public int getNumTour() {
		return numTour;
	}

	public int getScore() {
		// Si le tour est terminé et le joueur a fait un strike ou un spare
		if ( quillesParLancer[0] == 10) {
			// Si c'est un strike
			return 10;  // Un strike vaut 10, le score des tours suivants ajoutera les bonus
		}
		if (quillesParLancer[0] < 10 && quillesParLancer[0] + quillesParLancer[1] == 10) {
			// Si c'est un spare
			return 10;  // Un spare vaut 10, le score du lancer suivant ajoutera le bonus
		}
		return quillesParLancer[0] + quillesParLancer[1];  // Sinon, additionner les quilles abattues dans les deux lancers
	}
	
}
