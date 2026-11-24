package ph.edu.dlsu.lbycpds.socialnetwithgraph;

public class Profile {
    private final String name;
    private String status = "";
    private String quote = "";
    private String imagePath = "unknown.png";

    public Profile(String name) { this.name = name; }

    public String getName() { return name; }
    public String getStatus() { return status; }
    public void setStatus(String s) { status = s; }
    public String getQuote() { return quote; }
    public void setQuote(String q) { quote = q; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String p) { imagePath = p; }
}