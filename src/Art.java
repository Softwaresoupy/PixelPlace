public class Art {
    private String artName;
    private String artTime;
    private String artUser;
    private String artLocation;
    private String artFile;
    private String artDescription;
    private String galleryPlaceId;

    public String getGalleryPlaceId() {
        return galleryPlaceId;
    }

    public void setGalleryPlaceId(String placeId) {
        this.galleryPlaceId = placeId;
    }

    public Art(){}
    public Art(String artName, String time, String userName, String artLocation, String artFile, String artDescription){
        this.artName = artName;
        artTime = time;
        artUser = userName;
        this.artLocation = artLocation;
        this.artFile = artFile;
        this.artDescription = artDescription;
    }
    
  public String getArtName() {
    return artName;
  }

  public void setName(String artName) {
    this.artName = artName;
  }
   public String getArTime() {
    return artTime;
  }

  public void setArtTime(String artTime) {
    this.artTime = artTime;
  }
  
  public String getArtUser() {
    return artUser;
  }

  public void setArtUser(String artUser) {
    this.artUser = artUser;
  }
  
  public String getArtLocation() {
    return artLocation;
  }

  public void setArtLocation(String artLocation) {
    this.artLocation = artLocation;
  }
  
  public String getArtDescription() {
    return artDescription;
  }

  public String getArtFile(){return artFile;}

    public void setArtFile(String string){
        artFile = string;
    }

  public void setArtDescription(String artDescription) {
    this.artDescription = artDescription;
  }

  public String toString() {
        return String.join("%",
            artName,
            artTime,
            artUser,
            artLocation,
            artFile,
            artDescription
        );
    }
}
