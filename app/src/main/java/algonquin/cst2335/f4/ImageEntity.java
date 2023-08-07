package algonquin.cst2335.f4;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "images")
public class ImageEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "height")
    private int height;

    @ColumnInfo(name = "width")
    private int width;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "path")
    private String path;

    // Standard getters and setters for all the fields...

    public void setImageUrl(String url) {
        this.url = url;
    }

    // Constructors
    public ImageEntity() {
    }

    public ImageEntity(int height, int width, String url, String path) {
        this.height = height;
        this.width = width;
        this.url = url;
        this.path = path;
    }

    // Standard getters and setters for all the fields...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
