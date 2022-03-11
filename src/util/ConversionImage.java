/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;




/**
 *
 * @author diana
 */
public class ConversionImage {
    
     public static byte[] convertirFileImagetoArrByte(File pFile) throws IOException {  
          BufferedImage bufferedImage=null; 
        try {
            bufferedImage = ImageIO.read(pFile);
        } catch (IOException ex) {
            Logger.getLogger(ConversionImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ConversionImage.BufferedImage_to_Byte_Array_JPG(bufferedImage);
    }
     
     public static byte[] BufferedImage_to_Byte_Array_JPG(BufferedImage bufferedImage) throws IOException{
        byte[] imageBytes;
         // imageBytes = ((DataBufferByte) bufferedImage.getData().getDataBuffer()).getData();
         
         // otra vía
        /* WritableRaster raster = bufferedImage.getRaster();
         DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
         imageBytes = data.getData();*/
         
        
        
         //otra vía más
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 ImageIO.write(bufferedImage, "jpg", baos);
	 baos.flush();
	 imageBytes = baos.toByteArray();
	 baos.close();
         return imageBytes;
     }
     
     public static BufferedImage Byte_Array_to_BufferedImage(byte[] imageBytes) throws IOException{
         BufferedImage imageBuffered = ImageIO.read(new ByteArrayInputStream(imageBytes));
       
      //vía parecida
         /*InputStream in = new ByteArrayInputStream(imageInByte);
			BufferedImage bImageFromConvert = ImageIO.read(in);*/
         return imageBuffered;
     }
     
     public static Image getImagefromByteArray(byte[] imageBytes) throws IOException{
         Image imageJavaFX= null;
         if(imageBytes!=null){
             BufferedImage bufferedImage=  ConversionImage.Byte_Array_to_BufferedImage(imageBytes);
             if(bufferedImage!=null){
                 imageJavaFX = SwingFXUtils.toFXImage(bufferedImage, null);
             }             
         }
         return imageJavaFX;          
     }
}
