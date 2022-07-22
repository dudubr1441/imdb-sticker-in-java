package controller;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import javax.imageio.ImageIO;
import java.io.IOException;

import entity.MovieIMDb;
import java.net.URL;


public class ImageStickes {
    public BufferedImage gifWhatsapp(MovieIMDb movieImdb){
        BufferedImage imageNova = null;
        try {
            URL url = new URL(movieImdb.getImage());
			BufferedImage imagemOriginal = ImageIO.read(url);
            int height = 800;
            int width = 600;
            Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 80);
            Color color = new Color(255,255,255);
			imageNova = new BufferedImage(width,height+200,BufferedImage.TRANSLUCENT);
			Graphics2D GR2 = imageNova.createGraphics();
			GR2.drawImage(imagemOriginal, 0, 0,width,height,null);
			GR2.setColor(color);
			GR2.setFont(font);
			GR2.drawString(mensageStickerMovie(movieImdb), (width/2)-(GR2.getFontMetrics().getHeight()*2),height+100);
		} catch (IOException e) {
			System.err.println("error : "+e);
		}
        return imageNova;
    }
    private String mensageStickerMovie(MovieIMDb movie){
        int rank = Integer.parseInt(movie.getRank());
            if (rank<51) {
                return "Best Movie";
            } else {
                if (rank<99) {
                    return "God movie";
                } else {
                        return "good Movie";
                    }
                }
            }
}
