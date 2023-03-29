import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class App {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        String mostFilmesLinks = "https://gist.githubusercontent.com/lucasfugisawa/cbb0d10ee3901bd0541468e431c629b3/raw/1fe1f3340dfe5b5876a209c0e8226d090f6aef10/Top250Movies.json";
        var req = new Fetch();
        var resp = req.get(mostFilmesLinks);
        var json = new JsonParser();
        stickerGenerator gerador = new stickerGenerator();
        List<Map<String, String>> list = json.parse(resp);
        for (Map<String,String> item : list) {
            String regex = "._V1.*$";
            String imgUrl = item.get("image").replaceAll(regex,"._V1_UX512_CR0,3,512,704_AL_.jpg");
            InputStream inputStream = new URL(imgUrl).openStream();
            String  description = "";
            double rank = Double.parseDouble(item.get("rank"));
            System.out.println(rank + "/" + list.size());
            if(rank < (250*1/3) ){
                description = "Muito F*d*";
            } else if (rank < (250*2/3) ) {
                description = "Mais ou menos";
            } else {
                description = "ehhhh...";
            }
            String name = item.get("title");
            String nameNormalize = name.replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll(" ", "_");
            System.out.println(nameNormalize);
            gerador.create(inputStream, nameNormalize, description);
         }
    }
}
