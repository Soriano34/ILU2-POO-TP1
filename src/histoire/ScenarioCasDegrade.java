package histoire;
import personnages.Gaulois;
import villagegaulois.*;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Gaulois o = new Gaulois("o", 0);
		Etal etal = new Etal();
		
		etal.acheterProduit(1,o);
		System.out.println("Fin du test");
		}
}
