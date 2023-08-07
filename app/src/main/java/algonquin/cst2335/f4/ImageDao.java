package algonquin.cst2335.f4;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ImageDao {
    @Insert
    long insertImage(ImageEntity imageEntity);

    @Query("SELECT * FROM images")
    List<ImageEntity> getAllImages();

    // add more methods as required...
}
