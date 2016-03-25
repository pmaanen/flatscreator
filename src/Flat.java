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

public class Flat {
	private static final BufferedImage BLACK = new BufferedImage(1, 1,
			BufferedImage.TYPE_INT_ARGB);
	private static final int FONT_SIZE = 12;
	private BaseFont FONT = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252",
			false);
	private String name = "";
	private Image image;
	private Image shadow;
	private float width = 19f * Sheet.MM;
	private float height = 32f * Sheet.MM;
    	private float imageWidth;
	private float imageHeigh;
	private float offset = 0;
    private int count = 1;
    private boolean autoSize=true;
    private boolean drawShadow=true;
	private boolean doubleFlap;

    public Flat(String imagePath, boolean doubleFlap, boolean autoSize) throws IOException,
									       DocumentException {
	this.autoSize=autoSize;
	this.doubleFlap = doubleFlap;
		image = PngImage.getImage(imagePath);
		if (image.getImageMask() == null) {
			throw new IOException("Image has no transparency");
		}
		imageWidth = image.getWidth();
	        imageHeight = image.getHeight();
		shadow = Image.getInstance(BLACK, null);
		shadow.setImageMask(image.getImageMask());
	}

	private void resize() {
	    if(!autoSize){
		image.scaleToFit(width, height);
		if (image.getScaledWidth() < width) {
			offset = (width - image.getScaledWidth()) / 2;
		}
		shadow.scaleAbsolute(image.getScaledWidth(), image.getScaledHeight());
	    }
	    else{
		image.scaleToFit(imageWidth, imageHeight);
		if (image.getScaledWidth() < imageWidth) {
		    offset = (imageWidth - image.getScaledWidth()) / 2;
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
	
		Image mirrored=image;
		mirrored.setAbsolutePosition(x + width / 3 * 7 + offset, y);
		mirrored.scalePercent(-100,100);
		content.addImage(mirrored);
		if(drawShadow){
		shadow.setAbsolutePosition(x + width / 3 * 10 + offset, y);
		content.addImage(shadow, -image.getScaledWidth(), 0, 0,
				image.getScaledHeight(), x + width / 3 * 10 + offset
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
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
	    if(!autoSize)
		this.height = height * Sheet.MM;
	    resize();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
