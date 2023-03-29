import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class stickerGenerator {
    public void create(InputStream inputStream, String name , String description ){
        try {
            BufferedImage origin = ImageIO.read(inputStream);
            int width = 512;
            int height = 512 * origin.getHeight() / origin.getWidth();
            final double Increment = height * 0.1;
            int heightIncrement = height + (int) Increment;
            BufferedImage newImage =  new BufferedImage(width, heightIncrement, BufferedImage.TRANSLUCENT);
            Graphics2D graphics = (Graphics2D) newImage.getGraphics();
            graphics.drawImage(origin, 0, 0,width, height, null);

            String imgAlura = "src/public/logo/Alura.png";
            BufferedImage logoAlura = ImageIO.read(new File(imgAlura));
            int widthAlura = (int) (width * 0.15 * 3.56);
            int heightAlura = (int) (width * 0.15 * 1);
            graphics.drawImage(logoAlura, 0, 0,  widthAlura, heightAlura, null);

            String imgEu = "src/public/logo/Eu.png";
            BufferedImage logoEu = ImageIO.read(new File(imgEu));
            int widthEu = (int) (width * 0.4 * 1);
            int heightEu = (int) (width * 0.4 * 1.66);
            graphics.drawImage(logoEu, 0, height - heightEu,  widthEu, heightEu,null);

            var font = new Font("Impact" ,Font.BOLD, (int) Increment);
            graphics.setFont(font);
            graphics.setColor(Color.BLACK);
            FontMetrics metrics = graphics.getFontMetrics(font);
            int widthText = graphics.getFontMetrics().stringWidth(description);
            int heightText = metrics.getHeight();
            graphics.drawString(description, width/2 - widthText/2, (int) (height + Increment/4 + heightText/2));

            
            Color green = Color.GREEN;
            graphics.setColor(green);
            int outlineWidth = 2; 
            Stroke stroke = new BasicStroke(outlineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            graphics.setStroke(stroke);
            var fontBorder = new Font("Impact" ,Font.BOLD, (int) Increment);
            FontRenderContext frc = graphics.getFontRenderContext();
            TextLayout layout = new TextLayout(description, fontBorder, frc) ;
            int textX = (width - (int)layout.getBounds().getWidth()) / 2;
            int textY = (int) (height + Increment/4 + heightText/2);
            graphics.setColor(green);
            graphics.drawString(description, textX, textY);

            String pathOutput = "src/public/output/";
            try {
                ImageIO.write(newImage, "png", new File( pathOutput + name + ".png"));
            } catch (Exception e) {
                Path path = Paths.get(pathOutput);
                Files.createDirectory(path);
                ImageIO.write(newImage, "png", new File( pathOutput + name + ".png"));
            }
            
        } catch (IOException e) {           
            e.printStackTrace();
        }
    }
}
