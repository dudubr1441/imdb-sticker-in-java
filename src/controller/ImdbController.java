package controller;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import controller.ImageStickes;
import entity.MovieIMDb;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;


public class ImdbController {

    private ImageStickes imageStickes = new ImageStickes();

    private String formatLinkImageMovie(String nameImage){
        String nameFormat = null;
        String namedelete;
        if(nameImage.indexOf("@")!=-1){
        namedelete = nameImage.substring(nameImage.lastIndexOf("@"), nameImage.length());
        nameFormat = nameImage.replace(namedelete,"@.jpg");
        }else{
        namedelete = nameImage.substring(nameImage.lastIndexOf("._V"), nameImage.length());
        nameFormat = nameImage.replace(namedelete,".jpg");
        }
        return nameFormat;
    }

    private ArrayList<MovieIMDb> listTopMovies(String password){
        ArrayList<MovieIMDb> listMovie = new ArrayList<>();
        try {
        //"k_r143sxm7"
        ImdbConnection imdbconnection = new ImdbConnection();
        Gson gson = new Gson();
        JSONObject jsObject = imdbconnection.JsonIMDB("top250movies",password);
        JSONArray jArray = jsObject.getJSONArray("items");
        //convert json in entity
        jArray.forEach(movie->{
        MovieIMDb movieIMDB = gson.fromJson(movie.toString(),MovieIMDb.class);     
        movieIMDB.setImage(formatLinkImageMovie(movieIMDB.getImage()));
        listMovie.add(movieIMDB);
        });
        //transform id image 
        } catch (IOException e) {
            System.err.println("error: "+e);
        }
        return listMovie;
    }
    
    public void SaveAllTopMovies(String passwordIMDb) throws IOException{
        ArrayList<MovieIMDb> listMovies = listTopMovies(passwordIMDb);
        listMovies.forEach(movie -> {
             BufferedImage imageNew;
             try { 
                 //directory path
                 imageNew = imageStickes.gifWhatsapp(movie);
                 String path = "Stickers exit/"+movie.getTitle().replaceAll("[:\\\\/*\"?|<>']", "-")+".gif";
                 System.out.println(movie.getRank());
                 System.out.println(movie.getTitle().replaceAll("[:\\\\/*\"?|<>']", "-"));
                 System.out.println(movie.getImage());
                 System.out.println(imageNew);
                 System.out.println(path);
                 if(imageNew!=null){
                 ImageIO.write(imageNew, "gif", new File(path));
                 }
             } catch (IOException e) {
                 System.err.println("error save Movies: "+e);
             }
        });
 
     }
}
