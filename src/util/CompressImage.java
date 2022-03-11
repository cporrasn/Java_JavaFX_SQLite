/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author diana
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


public class CompressImage {
    /**
	 * @param args
	 * @throws IOException 
	 */
 /*public static void main(String[] args) throws IOException {
		
	  System.out.println("F:/Work/GYM/Proyecto GYM/GYM_Librio 20131213/Invierno.jpeg");
	  BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	  String s = bufferRead.readLine();
	    
	    //Obteniendo entrada
		FileInputStream fis = new FileInputStream(new File(s));
		BufferedImage image = null;
		image = ImageIO.read(fis);
		fis.close();
		
		//Creando fichero salida
		System.out.println("inserta camino imagen dest");
		BufferedReader bufferRead2 = new BufferedReader(new InputStreamReader(System.in));
	    String s2 = bufferRead2.readLine();
		File f = new File(s2);
		ImageOutputStream ios = ImageIO.createImageOutputStream(f);
		
		//Obteniendo writer tipo jpg
		Iterator<ImageWriter> writersJpeg = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = null;
		while (writersJpeg.hasNext()) {
			writer = (ImageWriter) writersJpeg.next();
			if (writer.getClass().getName().equals("com.sun.media.imageioimpl.plugins.jpeg.CLibJPEGImageWriterSpi"))
			{
				break;
			}
		}
		
		//Seteandole al writer sobre que va a escribir(outputstream)
		writer.setOutput(ios);

		IIOImage ioimage = new IIOImage(image, null, null);

		//Jugando con la calidad de la imagen para mas o menos tamanno
		//Ver mas sets para mas opciones
		ImageWriteParam paramJpeg = writer.getDefaultWriteParam();
		paramJpeg.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		paramJpeg.setCompressionType("JPEG");
		paramJpeg.setCompressionQuality(0.3f);
		
		//escribir imagen final
		writer.write(null, ioimage, paramJpeg);
		
		//liberando
		writer.dispose();
		ios.flush();
		ios.close();
		image.flush();

	}*/
 
  public static void compressImageFoto(File fileFoto,boolean isFotoCorporal) throws IOException {

     /* BufferedReader bufferRead = new BufferedReader(new InputStreamReader(foto));
      String s = bufferRead.readLine();*/
	    
	    //Obteniendo entrada
		//FileInputStream fis = new FileInputStream(new File(s));
                File f;
                FileInputStream fis = new FileInputStream(fileFoto);	
                
                BufferedImage image = null;
                image = ImageIO.read(fis);
                fis.close();
		
		//Creando fichero salida
		/*System.out.println("inserta camino imagen dest");
		BufferedReader bufferRead2 = new BufferedReader(new InputStreamReader(System.in));
	        String s2 = bufferRead2.readLine();*/
                if(!isFotoCorporal)
		   f = new File("reportes/foto.jpeg");
                else  f = new File("reportes/fotoCorporal.jpeg");
		ImageOutputStream ios = ImageIO.createImageOutputStream(f);
		
		//Obteniendo writer tipo jpg
		Iterator<ImageWriter> writersJpeg = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = null;
		while (writersJpeg.hasNext()) {
			writer = (ImageWriter) writersJpeg.next();
			if (writer.getClass().getName().equals("com.sun.media.imageioimpl.plugins.jpeg.CLibJPEGImageWriterSpi"))
			{
				break;
			}
		}
		
		//Seteandole al writer sobre que va a escribir(outputstream)
		writer.setOutput(ios);

		IIOImage ioimage = new IIOImage(image, null, null);
           
		//Jugando con la calidad de la imagen para mas o menos tamanno
		//Ver mas sets para mas opciones
               
		ImageWriteParam paramJpeg = writer.getDefaultWriteParam();
		paramJpeg.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		paramJpeg.setCompressionType("JPEG");
                System.out.println("compressionQuality" + 0.01f);
		paramJpeg.setCompressionQuality(0.3f);
            
		
		//escribir imagen final
		writer.write(null, ioimage, paramJpeg);
		
		//liberando
		writer.dispose();
		ios.flush();
		ios.close();
		image.flush();

	}
 
}
