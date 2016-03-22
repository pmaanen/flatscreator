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
	private float offset = 0;
	private int count = 1;
	private boolean doubleFlap;

	public Flat(String imagePath, boolean doubleFlap) throws IOException,
			DocumentException {
		this.doubleFlap = doubleFlap;
		image = PngImage.getImage(imagePath);
		if (image.getImageMask() == null) {
			throw new IOException("Image has no transparency");
		}
		shadow = Image.getInstance(BLACK, null);
		shadow.setImageMask(image.getImageMask());
	}

	private void resize() {
		image.scaleToFit(width, height);
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
		image.setAbsolutePosition(x + width / 3 * 4 + offset, y);
		content.addImage(image);
		shadow.setAbsolutePosition(x + width / 3 * 7 + offset, y);
		content.addImage(shadow, -image.getScaledWidth(), 0, 0,
				image.getScaledHeight(), x + width / 3 * 7 + offset
						+ image.getScaledWidth(), y);

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
		this.width = width * Sheet.MM;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
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
