package Model.livreur;

/**
 *
 * @author sony
 */
public class Livraison {
    
    private int [] id ;    
    private String [] numero ; 
    private int [] id_livreur ; 
    private int [] id_commande ;    
    private String [] contact ;
    private String [] lieu ;

    public Livraison(int[] id, String[] numero, int[] id_livreur, int[] id_commande, String[] contact, String[] lieu) {
        this.id = id;
        this.numero = numero;
        this.id_livreur = id_livreur;
        this.id_commande = id_commande;
        this.contact = contact;
        this.lieu = lieu;
    }

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
    }

    public String[] getNumero() {
        return numero;
    }

    public void setNumero(String[] numero) {
        this.numero = numero;
    }

    public int[] getId_livreur() {
        return id_livreur;
    }

    public void setId_livreur(int[] id_livreur) {
        this.id_livreur = id_livreur;
    }

    public int[] getId_commande() {
        return id_commande;
    }

    public void setId_commande(int[] id_commande) {
        this.id_commande = id_commande;
    }

    public String[] getContact() {
        return contact;
    }

    public void setContact(String[] contact) {
        this.contact = contact;
    }

    public String[] getLieu() {
        return lieu;
    }

    public void setLieu(String[] lieu) {
        this.lieu = lieu;
    }

    
}
