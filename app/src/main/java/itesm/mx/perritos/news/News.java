package itesm.mx.perritos.news;

import java.io.Serializable;

/**
 * Created by jorgevazquez on 3/20/17.
 */

public class News implements Serializable {

    private String title;
    private String description;
    private String photoUrl;

    /**
     * Default constructor
     */
    public News() {
        this.title = "";
        this.description = "";
        photoUrl = null;
    }

    /**
     * Constructor
     * @param title Title of the news
     * @param desciption Description of the news
     */
    public News(String title, String desciption, String photoUrl) {
        this.title = title;
        this.description = desciption;
        this.photoUrl = photoUrl;
    }

    /**
     * Set the title of the news
     * @param title News to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the description of the news
     * @param description Description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Return the title of the news
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Return the description of the news
     * @return description
     */
    public String getDescription() {
        return this.description;
    }



    /**
     * Set the idImage of the news
     * @param photoUrl
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


   public String getPhotoUrl() {
       return this.photoUrl;
   }
}
