public class Art {
    private int artID;
    private String artName;
    private String artTime;
    private String artUser;
    private String artLocation; // file path to artwork
    private String artDescription;
    private String galleryPlaceId;

    public String getGalleryPlaceId() {
        return galleryPlaceId;
    }

    public void setGalleryPlaceId(String placeId) {
        this.galleryPlaceId = placeId;
    }
    public Art(int id){
        artID = id;
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

  public void setArtDescription(String artDescription) {
    this.artDescription = artDescription;
  }

  public String toString() {
        return String.join("%*'", 
            String.valueOf(artID),
            artName,
            artTime,
            artUser,
            artLocation,
            artDescription,
            galleryPlaceId != null ? galleryPlaceId : "null"
        );
    }
}
