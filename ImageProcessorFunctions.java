import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageProcessorFunctions {

    File selectedFile;
    BufferedImage image;
    final String DEFAULT_IMAGE_NAME = "raytrace.jpg";
    File image_file = new File(DEFAULT_IMAGE_NAME);

    public ImageProcessorFunctions() {
        initialiseImage();
    }

    private BufferedImage initialiseImage() {
        try {
            image = ImageIO.read(image_file);
            return image;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public JLabel ChangeImage(File image_file, JLabel image_icon) {
        try {
            this.image_file = image_file;
            image = ImageIO.read(image_file);
            image_icon.setIcon(new ImageIcon(image));
            return image_icon;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /*
     * This function will return a pointer to an array
     * of bytes which represent the image data in memory.
     * Using such a pointer allows fast access to the image
     * data for processing (rather than getting/setting
     * individual pixels)
     */
    public byte[] GetImageData() {
        WritableRaster WR = image.getRaster();
        DataBuffer DB = WR.getDataBuffer();
        if (DB.getDataType() != DataBuffer.TYPE_BYTE)
            throw new IllegalStateException("That's not of type byte");

        return ((DataBufferByte) DB).getData();
    }

    public JLabel ResetImage(JLabel image_icon) {
        try {
            image = ImageIO.read(image_file);
            image_icon.setIcon(new ImageIcon(image));
            return image_icon;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public JLabel Equalise(JLabel image_icon) {// ToDo: Implement
        // // Get image dimensions, and declare loop variables
        // int w = image.getWidth(), h = image.getHeight(), i, j, c;
        // // Obtain pointer to data for fast processing
        // byte[] data = GetImageData();
        // int[] histogram;

        image_icon.setIcon(new ImageIcon(image));
        return image_icon;
    }

    /*
     * This function shows how to carry out an operation on an image.
     * It obtains the dimensions of the image, and then loops through
     * the image carrying out the invert.
     */
    // public BufferedImage Invert(BufferedImage image) {
    public JLabel Invert(JLabel image_icon) {
        // Get image dimensions, and declare loop variables
        int w = image.getWidth(), h = image.getHeight(), i, j, c;
        // Obtain pointer to data for fast processing
        byte[] data = GetImageData();

        // Shows how to loop through each pixel and colour
        // Try to always use j for loops in y, and i for loops in x
        // as this makes the code more readable
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                for (c = 0; c < 3; c++) {
                    data[c + 3 * i + 3 * j * w] = (byte) (255
                            - (data[c + 3 * i + 3 * j * w] & 255));
                } // colour loop
            } // column loop
        } // row loop

        image_icon.setIcon(new ImageIcon(image));
        return image_icon;
    }

    public JLabel SlowGamma(JLabel image_icon, int gammaValue) { // ToDo: Implement
        /*
         * • V=(I/a)1/ (assume a=1 for this course)
         * • Implementation: divide pixel by 255 (get I, a number between 0 and 1)
         * • Find power(I, 1.0/gamma)
         * • Multiply by 255 to get new intensity
         * • (Do for each colour channel, for each pixel)
         */

        // Get image dimensions, and declare loop variables
        int w = image.getWidth(), h = image.getHeight(), i, j, c;
        // Obtain pointer to data for fast processing
        byte[] data = GetImageData();

        // Shows how to loop through each pixel and colour
        // Try to always use j for loops in y, and i for loops in x
        // as this makes the code more readable
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                for (c = 0; c < 3; c++) {
                    data[c + 3 * i + 3 * j * w] = (byte) (255
                            - (data[c + 3 * i + 3 * j * w] & 255));
                } // colour loop
            } // column loop
        } // row loop

        image_icon.setIcon(new ImageIcon(image));
        return image_icon;
    }

    public JLabel FastGamma(JLabel image_icon) { // ToDo: Implement
        // // Get image dimensions, and declare loop variables
        // int w = image.getWidth(), h = image.getHeight(), i, j, c;
        // byte[] data = GetImageData();
        // // Obtain pointer to data for fast processing

        image_icon.setIcon(new ImageIcon(image));
        return image_icon;
    }

    public JLabel BlueFade(JLabel image_icon, int selectedFade) {
        // Get image dimensions, and declare loop variables
        int w = image.getWidth(), h = image.getHeight(), i, j, c;
        // Obtain pointer to data for fast processing
        byte[] data = GetImageData();
        int int_image[][][];
        //double t;

        int_image = new int[h][w][3];

        // Copy byte data to new image taking care to treat bytes as unsigned
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                for (c = 0; c < 3; c++) {
                    int_image[j][i][c] = data[c + 3 * i + 3 * j * w] & 255;
                } // colour loop
            } // column loop
        } // row loop

        // Now carry out processing on this different data typed image (e.g. correlation
        // or "bluefade"
        int bFade = 0,
                gFade = 0,
                rFade = 0;
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                switch (selectedFade) {
                    case 0:
                        rFade = 255 * j / h;
                        break;
                    case 1:
                        gFade = 255 * j / h;
                        break;
                    case 2:
                        bFade = 255 * j / h;
                        break;
                    case 3:
                        rFade = 255 * j / h;
                        gFade = 255 * j / h;
                        bFade = 255 * j / h;
                        break;
                }
                int_image[j][i][0] = bFade; // BLUE
                int_image[j][i][1] = gFade; // GREEN
                int_image[j][i][2] = rFade; // RED
            } // column loop
        } // row loop

        // Now copy the processed image back to the original
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                for (c = 0; c < 3; c++) {
                    data[c + 3 * i + 3 * j * w] = (byte) int_image[j][i][c];
                } // colour loop
            } // column loop
        } // row loop

        image_icon.setIcon(new ImageIcon(image));
        return image_icon;
    }

    public JLabel Threshold(JLabel image_icon) {
        return image_icon;
    }
}