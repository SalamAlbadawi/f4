package algonquin.cst2335.f4;


import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;

public class ImageUtils {

    /**
     * Converts a Bitmap object into a byte array.
     * @param bitmap The bitmap to be converted.
     * @return A byte array representing the bitmap.
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    /**
     * If you ever need to convert back from byte array to bitmap, you can include that method here as well.
     */

    // Other image-related utility methods can be added in this class in the future...
}
