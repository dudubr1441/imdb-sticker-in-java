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

    static ImageStickes imageStickes = new ImageStickes();

    private String formatLinkImageMovie(String nameImage){
        String nameFormat = nameImage;
        if(nameImage.contains("@")){
        String namedelete = nameImage.substring(nameImage.lastIndexOf("@"), nameImage.length());
        nameFormat = nameImage.replace(namedelete,"@.jpg");
        }else{
        String namedelete = nameImage.substring(nameImage.lastIndexOf("._V"), nameImage.length());
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
        jArray.forEach(movie->{listMovie.add((gson.fromJson(movie.toString(),MovieIMDb.class)));});
        listMovie.forEach(movie->{
            movie.setImage(formatLinkImageMovie(movie.getImage()));
            System.out.println(movie.getImage());
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
                 String path = "D:/programação/figurinhas java IMDB/originals images/"+movie.getTitle().replaceAll("[:\\\\/*\"?|<>']", "-")+".gif";
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
