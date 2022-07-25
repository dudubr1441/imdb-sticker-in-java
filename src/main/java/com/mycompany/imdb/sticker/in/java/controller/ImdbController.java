package com.mycompany.imdb.sticker.in.java.controller;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import com.mycompany.imdb.sticker.in.java.controller.ImageStickes;
import com.mycompany.imdb.sticker.in.java.entity.MovieIMDb;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;


public class ImdbController {

    private final ImageStickes imageStickes = new ImageStickes();

    private String formatLinkImageMovie(String nameImage){
        String nameFormat = null;
        String namedelete;
        if(nameImage.contains("@")){
        namedelete = nameImage.substring(nameImage.lastIndexOf("@"), nameImage.length());
        nameFormat = nameImage.replace(namedelete,"@.jpg");
        }else{
        namedelete = nameImage.substring(nameImage.lastIndexOf("._V"), nameImage.length());
        nameFormat = nameImage.replace(namedelete,".jpg");
        }
        return nameFormat;
    }

    private ArrayList<MovieIMDb> listTopMovies(String password) throws IOException{
        ArrayList<MovieIMDb> listMovie = new ArrayList<>();
        ImdbConnection imdbconnection = new ImdbConnection();
        Gson gson = new Gson();
        JSONObject jsObject = imdbconnection.JsonIMDB("top250movies",password);
        JSONArray jArray = jsObject.getJSONArray("items");
        for (int i = 0; i < jArray.length(); i++) {
            MovieIMDb movieIMDB = gson.fromJson(jArray.getJSONObject(i).toString(),MovieIMDb.class);
            movieIMDB.setImage(formatLinkImageMovie(movieIMDB.getImage()));
            listMovie.add(movieIMDB);
        }
        return listMovie;
    }
    
    public void SaveAllTopMovies(String passwordIMDb) throws IOException{
        ArrayList<MovieIMDb> listMovies = listTopMovies(passwordIMDb);
        for (MovieIMDb movie : listMovies) {
            BufferedImage imageNew;
            try {
                //directory path
                imageNew = imageStickes.gifWhatsapp(movie);
                String path = "Stickers exit/" + movie.getTitle().replaceAll("[:\\\\/*\"?|<>']", "-") + ".gif";
                System.out.println(movie.getRank());
                System.out.println(movie.getTitle().replaceAll("[:\\\\/*\"?|<>']", "-"));
                System.out.println(movie.getImage());
                System.out.println(imageNew);
                System.out.println(path);
                if(imageNew!=null){
                    ImageIO.write(imageNew, "gif", new File(path));
                }
            }catch (IOException e) {
                System.err.println("error save Movies: "+e);
            }
        } 
     }
}
