public interface Database {
    public abstract void populate();
    // populate the database from the file
    public abstract void addy(Object a);
    // add object into database
    public abstract void subtract(Object a);
    // remove object from database
    public abstract void saveFile();
    // save database into file to be rewritten

}
