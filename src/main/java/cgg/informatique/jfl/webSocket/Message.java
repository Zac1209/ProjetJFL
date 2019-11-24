package cgg.informatique.jfl.webSocket;


public class Message {

    private String de;
    private String texte;
    private Long   creation;
    private String avatar  = new String();
    private String position = new String();
    private String resultat = new String();
    private String idUser = new String();

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public Message(String avatar, String position, String resultat) {
        this.avatar = avatar;
        this.position = position;
        this.resultat = resultat;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Message() {
    }

    public Message(String de, String texte, Long creation, String avatar) {
        this.de = de;
        this.texte = texte;
        this.creation = creation;
        this.avatar = avatar;
    }

    public Long getCreation() {
        return creation;
    }

    public void setCreation(Long creation) {
        this.creation = creation;
    }

    public Message(String de, String texte) {
        this.de = de;
        this.texte = texte;
    }

    public Message(String valeur, String id, String rien,String rien2){
        this.avatar = valeur;
        this.idUser = id;
    }

    public Message(  String texte) {
        this.de = "anonyme";
        this.texte = texte;
    }


    @Override
    public String toString() {
        return "Reponse{" +
                "de='" + de + '\'' +
                ", texte='" + texte + '\'' +
                ", creation=" + creation +
                ", avatar=" + avatar +
                '}';
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String text) {
        this.texte = text;
    }
}

