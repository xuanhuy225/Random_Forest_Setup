package me.common.helper;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;


public class ImageHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageHelper.class);
    public static final int DEFAULT_MAX_WIDTH_IMAGE = 2560;
    public static final int DEFAULT_MAX_HEIGHT_IMAGE = 2560;

    public static byte[] fetchImageFromUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (InputStream inputStream = url.openStream()) {
                int readBytes;
                byte[] readBuffer = new byte[4096];
                while ((readBytes = inputStream.read(readBuffer)) != -1) {
                    outputStream.write(readBuffer, 0, readBytes);
                }
            }
            return outputStream.toByteArray();
        } catch (MalformedURLException e) {
            LOGGER.error("error fetching image", e);
            return null;
        } catch (IOException e) {
            LOGGER.error("error reading image", e);
            return null;
        }
    }

    // convert BufferedImage to byte[]
    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public static byte[] compressImageOld(byte[] imageData, String formatName, int targetSize) throws IOException {
        String formatNameCheck = formatName.toUpperCase();
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (formatNameCheck.equals("PNG")) {
            bufferedImage = convertPNGToJPG(bufferedImage);
            formatNameCheck = "JPG";
        }

        switch (formatNameCheck) {
            case "JPG":
            case "JPEG":
                // need Java 9+ for PNG writer support
                ImageWriter writer = ImageIO.getImageWritersByFormatName(formatNameCheck).next();
                ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
                writer.setOutput(ios);

                ImageWriteParam param = writer.getDefaultWriteParam();
                if (param.canWriteCompressed()) {
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(1f);
                }

                writer.write(null, new IIOImage(bufferedImage, null, null), param);
                writer.dispose();
                return outputStream.toByteArray();
            default:
                LOGGER.warn("Image type unknown");
                return imageData;
        }
    }

    public static byte[] compressImage(byte[] imageData, String formatName, int maxSize) {
        return compressImage(imageData, formatName, maxSize, false, DEFAULT_MAX_WIDTH_IMAGE, DEFAULT_MAX_HEIGHT_IMAGE);
    }

    public static byte[] compressImage(byte[] imageData, String formatName, int maxSize, boolean resizeImage) {
        return compressImage(imageData, formatName, maxSize, resizeImage, DEFAULT_MAX_WIDTH_IMAGE, DEFAULT_MAX_HEIGHT_IMAGE);
    }

    public static byte[] compressImage(byte[] imageData, String formatName, int maxSize, boolean resize, int maxWidth, int maxHeight) {

        try {
            String formatNameCheck = formatName.toUpperCase();
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));

            if (formatNameCheck.equals("PNG")) {
                bufferedImage = convertPNGToJPG(bufferedImage);
                formatNameCheck = "JPG";
            }

            switch (formatNameCheck) {
                case "JPG":
                case "JPEG":

                    if (resize) {
                        bufferedImage = resizeImageQuality(bufferedImage, maxWidth, maxHeight);
                    }

//                    TiffImageMetadata metaData = readExifMetadata(imageData);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, formatNameCheck, outputStream);

                    // compress image until its size is less than maxSize
                    float compressionQuality = 0.8f;
                    while ((outputStream.size() > maxSize && compressionQuality > 0.1f)) {
                        outputStream.reset(); // reset output stream
                        // write image to output stream with current compression quality
                        writeImage(bufferedImage, formatNameCheck, outputStream, compressionQuality);

                        // if size is still too large, decrease compression quality and reset output stream
                        LOGGER.info("Compression quality: " + compressionQuality + ", size: " + outputStream.size());
                        if (outputStream.size() > maxSize) {
                            compressionQuality -= 0.1f;
                        }
                    }

                    // save compressed image to file
                    byte[] compressedImage = outputStream.toByteArray();
//                    if(metaData != null) {
//                        compressedImage = writeExifMetadata(compressedImage, metaData);
//                    }
                    return compressedImage;
                default:
                    LOGGER.warn("Image type unknown");
                    return imageData;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeImage(BufferedImage image, String formatName, ByteArrayOutputStream outputStream, float compressionQuality) throws IOException {
        // get image writer for the specified format
        ImageWriter writer = ImageIO.getImageWritersByFormatName(formatName).next();

        // configure writer with compression quality
        writer.setOutput(new MemoryCacheImageOutputStream(outputStream));
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(compressionQuality);
        }

        // write image
        writer.write(null, new IIOImage(image, null, null), param);
    }

    public static BufferedImage convertPNGToJPG(BufferedImage pngImage) {
        BufferedImage result = new BufferedImage(
                pngImage.getWidth(),
                pngImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        result.createGraphics().drawImage(pngImage, 0, 0, Color.WHITE, null);
        return result;
    }

    public static BufferedImage resizeImageQuality(BufferedImage image, Integer maxWidth, Integer maxHeight) {
        if (image.getWidth() > maxWidth || image.getHeight() > maxHeight) {
            return Scalr.resize(image, Scalr.Method.QUALITY, maxWidth, maxHeight);
        } else {
            return image;
        }
    }

    private static TiffImageMetadata readExifMetadata(byte[] jpegData) {
        try {
            ImageMetadata imageMetadata = Imaging.getMetadata(jpegData);
            if (imageMetadata == null) {
                return null;
            }
            TiffImageMetadata exif = null;
            if (imageMetadata instanceof JpegImageMetadata) {
                JpegImageMetadata jpegMetadata = (JpegImageMetadata) imageMetadata;
                exif = jpegMetadata.getExif();
            }
            return exif;
        } catch (Exception e) {
            return null;
        }
    }
    public static String getBase64Image(byte[] imageData) {
        try {
            return Base64.getEncoder().encodeToString(imageData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] writeExifMetadata(byte[] jpegData, TiffImageMetadata metadata)
            throws ImageReadException, ImageWriteException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        new ExifRewriter().updateExifMetadataLossless(jpegData, out, metadata.getOutputSet());
        out.close();
        return out.toByteArray();
    }
    private static String getImageType(byte[] image) {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(image));
            return img.getType() == BufferedImage.TYPE_INT_RGB ? "JPG" : "PNG";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
