package villagegaulois;

import javax.swing.plaf.metal.MetalScrollBarUI;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	public Village(String nom, int nbVillageoisMaximum,int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	private class Marche {
		private Etal[] etals;
		private Marche(int nbEtal) {
			etals=new Etal[nbEtal];
			for (int i = 0; i < nbEtal; i++) {
				etals[i]= new Etal();
			}
		}
		
		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) return i;
			}return -1;}
			
		private Etal[] trouverEtals(String produit) {
			int nbEtalsproduits = 0;
			for (int i = 0; i < etals.length; i++) {if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) nbEtalsproduits++;}
			Etal[] etalContenant = new Etal[nbEtalsproduits];
			int index=0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[index].isEtalOccupe() && etals[i].contientProduit(produit)) {etalContenant[index] = etals[i];
				index++;}
			}return etalContenant;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) { if (etals[i].getVendeur()==gaulois) return etals[i];}
			return null;}
		
		private String afficherMarche(){
			int nbEtalsVide=0;
			StringBuilder chaine = new StringBuilder();
			
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) chaine.append(etals[i].afficherEtal());
				else nbEtalsVide++;
			}
			if (nbEtalsVide>0) {chaine.append("Il reste "+ nbEtalsVide+" étals non utilisés dans le marché\n");}
			return chaine.toString();}
		
	//FIn classe marche
		}
	
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException{
		if (chef == null ) throw new VillageSansChefException("Le village doit posséder un chef\n");
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
		public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
			StringBuilder chaine = new StringBuilder();
			chaine.append(vendeur.getNom()+" cherche un endroit pour vendre "+ nbProduit+ " "+ produit+".\n");
			int etalLibre = marche.trouverEtalLibre();
			if (etalLibre!=-1) {
				marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
				chaine.append("Le vendeur "+ vendeur.getNom()+ " vend des "+ produit+ "s à l'étal n°"+ (etalLibre+1)+".\n");
			}
			else chaine.append("Désolé il n'y a plus d'étal libre au marché\n");
			return chaine.toString();
		}
		
		public String rechercherVendeursProduit(String produit) {
			StringBuilder chaine= new StringBuilder();
			Etal[] etalProduits = marche.trouverEtals(produit);
			if (etalProduits.length == 0) chaine.append("Il n'y a pas de vendeur qui propose des "+ produit+" au marché.\n ");
			else if (etalProduits.length==1) chaine.append("Seul le vendeur "+etalProduits[0].getVendeur().getNom()+" propose des "+produit+" au marché.");
			else {
				chaine.append("Les vendeurs qui proposent des " +produit+" sont :\n");
				for (int i = 0; i < etalProduits.length; i++) {
					chaine.append("- "+etalProduits[i].getVendeur().getNom()+" \n");
				}
			}
		  	return chaine.toString();
		}
		
		public Etal rechercherEtal(Gaulois vendeur) {
			return marche.trouverVendeur(vendeur);
		}
		
		
		public String partirVendeur(Gaulois vendeur) {
			Etal etalVendeur=rechercherEtal(vendeur);
			if (etalVendeur!=null) return etalVendeur.libererEtal();
			return vendeur.getNom() + " n'a pas de place dans le marché.\n";
			
		}
		
		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			chaine.append("Le marché du village \"").append(nom).append(" possède plusieurs étals :\n");
			chaine.append(marche.afficherMarche());
			return chaine.toString();
		}
		
		
		
		
		
		
		
}