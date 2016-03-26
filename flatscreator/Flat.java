package flatscreator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.codec.PngImage;
import java.io.Serializable;

public class Flat implements java.io.Serializable {
    private static long serialVersionUID = 1L;
    transient public static final BufferedImage BLACK = new BufferedImage(1, 1,
								 BufferedImage.TYPE_INT_ARGB);
    public static final int FONT_SIZE = 12;
    transient public BaseFont FONT = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252",
						false);
    public String name = "";
    public String imagePath="";
    transient public Image image;
    transient public Image mirrored;
    transient public Image shadow;
    public float oldWidth = 19f * Sheet.MM;
    public float oldHeight = 32f * Sheet.MM;
    public float width = 19f * Sheet.MM;
    public float height = 32f * Sheet.MM;
    public float imageWidth;
    public float imageHeight;
    public float offset = 0;
    public int count = 1;
    public boolean autoSize;
    public boolean drawShadow;
    public boolean doubleFlap;
    
    public Flat(String imagePath, boolean doubleFlap, boolean autoSize, boolean drawShadow) throws IOException,
												   DocumentException {
	this.autoSize=autoSize;
	this.drawShadow=drawShadow;
	this.doubleFlap = doubleFlap;
	if(autoSize){
	    width=imageWidth;
	    height=imageHeight;
	}
	this.imagePath=imagePath;
	initialize();
    }
    
    public void initialize() throws IOException, 
				    DocumentException {
	image = PngImage.getImage(imagePath);
        if (image.getImageMask() == null) {
            throw new IOException("Image has no transparency");
        }
	shadow = Image.getInstance(BLACK, null);
        shadow.setImageMask(image.getImageMask());
        mirrored=PngImage.getImage(imagePath);
        mirrored.scalePercent(-100,100);
	imageWidth = image.getWidth();
        imageHeight = image.getHeight();
	FONT = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252",false);
    }
    

    
    public void resize() {
	if(autoSize){
	    width=imageWidth;
	    height=imageHeight;
	}
	image.scaleToFit(width, height);
	mirrored.scaleAbsolute(-image.getScaledWidth(), image.getScaledHeight());
	if (image.getScaledWidth() < width) {
	    offset = (width - image.getScaledWidth()) / 2;
	}
	shadow.scaleAbsolute(image.getScaledWidth(), image.getScaledHeight());
    }
    
    public void draw(PdfContentByte content, float x, float y)
	throws DocumentException {
	resize();
	
	PdfTemplate template = content.createTemplate(width / 3, height);
	template.beginText();
	template.setFontAndSize(FONT, FONT_SIZE);
	template.showTextAligned(PdfContentByte.ALIGN_CENTER, name,
				 (width / 3 + FONT.getAscentPoint(name, FONT_SIZE)) / 2,
				 height / 2, 90);
	template.endText();
	content.addTemplate(template, x, y);
	
	image.setAbsolutePosition(x + width / 3 + offset, y);
	content.addImage(image);
	
	//image.setAbsolutePosition(x + width / 3 * 4 + offset, y);
	mirrored.setAbsolutePosition(x + width / 3 * 7 + offset, y);
	content.addImage(mirrored);
	if(drawShadow){
	    shadow.setAbsolutePosition(x + width / 3 * 7 + offset, y);
	    content.addImage(shadow, -image.getScaledWidth(), 0, 0,
			     image.getScaledHeight(), x + width / 3 * 7 + offset
			     + image.getScaledWidth(), y);
	}
	content.setColorStroke(Color.black);
	content.saveState();
	content.rectangle(x, y, width / 3, height);
	content.rectangle(width / 3 + x, y, width, height);
	content.rectangle(width / 3 * 4 + x, y, width, height);
	content.rectangle(width / 3 * 7 + x, y, width, height);
	if (doubleFlap) {
	    content.rectangle(width / 3 * 10 + x, y, width / 3, height);
		}
	content.stroke();
	content.restoreState();
    }
    
    public String getName() {
	return name;
    }
    
    public void setDoubleFlap(boolean d) {
	doubleFlap = d;
	resize();
    }
    
    public void setDrawShadow(boolean d) {
	drawShadow = d;
	resize();
    }
    
    public void setAutoSize(boolean d) {
	autoSize = d;
	if(autoSize){
	    oldWidth=width;
	    oldHeight=height;
	    width=imageWidth;
	    height=imageHeight;
	}
	else{
	    width=oldWidth;
	    height=oldHeight;
	}
	resize();
    }
    
    
    public void setName(String name) {
	this.name = name;
    }
    
    public float getWidth() {
	float multi = 10;
	if (doubleFlap) {
	    multi = 11;
	}
	return width / 3f * multi;
    }
    
    public void setWidth(float width) {
	if(!autoSize)
	    this.width = width * Sheet.MM;
	else
	    this.height = imageHeight;
	resize();
    }
    
    public float getHeight() {
	return height;
    }
    
    public void setHeight(float height) {
	if(!autoSize)
	    this.height = height * Sheet.MM;
	else
	    this.height = imageHeight;
	resize();
    }
    
    public int getCount() {
	return count;
    }
    
    public void setCount(int count) {
	this.count = count;
    }    
       
}
/*
public class SerializableFlat implements java.io.Serializable {
    public String name = "";
    public float width = 19f * Sheet.MM;
    public float height = 32f * Sheet.MM;
    public float imageWidth;
    public float imageHeight;
    public float offset = 0;
    public int count = 1;
    public boolean autoSize;
    public boolean drawShadow;
    public boolean doubleFlap;
    public String filename="";
    public SerializableFlat(Flat flat){
	System.out.printf("Creating SerializableFlat");
	this.name=flat.name;
	this.autoSize=flat.autoSize;
	this.drawShadow=flat.drawShadow;
	this.doubleFlap = flat.doubleFlap;
	this.imageWidth=flat.imageWidth;
	this.imageHeight=flat.imageHeight;
	this.offset=flat.offset;
	this.width=flat.width;
	this.height=flat.height;
	this.count=flat.count;
	this.filename = flat.filename;
    }
}
*/