public class SongEntry {
    private String uniqueID;
    private String songName;
    private String artistName;
    private int songLength;
    private SongEntry nextNode;

    // Default constructor.
    public SongEntry() {
        uniqueID = "none";
        songName = "none";
        artistName = "none";
        songLength = 0;
        nextNode = null;
    }

    // constructor init fields
    public SongEntry(String uniqueID, String songName, String artistName, int songLength) {
        this.uniqueID = uniqueID;
        this.songName = songName;
        this.artistName = artistName;
        this.songLength = songLength;
    }

     /**
     * method to insert a new node between this node and current next node
     *
     * @param currNode - node to be added
     */
    public void insertAfter(SongEntry currNode) {
        if (currNode == null) {
            return;
        }
        currNode.setNext(nextNode);
        nextNode = currNode;
    }

    public void setNext(SongEntry nextNode) {

        this.nextNode = nextNode;
    }

    // Accessor methods that are used to return the value of the private fields.
    public String getID() {

        return this.uniqueID;
    }

    public String getSongName() {

        return this.songName;
    }

    public String getArtistName() {

        return this.artistName;
    }

    public int getSongLength() {

        return this.songLength;
    }

    public SongEntry getNext() {

        return this.nextNode;
    }
    /**
     * method to print song details
     */
    public void printPlaylistSongs() {
        System.out.println("Unique ID: " + getID());
        System.out.println("Song Name: " + getSongName());
        System.out.println("Artist Name: " + getArtistName());
        System.out.println("Song Length (in seconds): " + getSongLength());
        System.out.println("");
   }
}
