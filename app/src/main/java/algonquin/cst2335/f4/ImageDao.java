package algonquin.cst2335.f4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ImageDao {

    @Insert
    Long insertImage(ImageEntity imageEntity);

    @Query("SELECT * FROM images")
    List<ImageEntity> getAllImages();

    // This is the method you should have to match the call in DeleteImageTask
    @Delete
    void deleteImage(ImageEntity imageEntity);



}
