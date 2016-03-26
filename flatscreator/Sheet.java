package flatscreator;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class Sheet implements java.io.Serializable {
    private static long serialVersionUID = 1L;
    public static final float MM = PageSize.A4.getWidth() / 210f;
    private List<Flat> flats;
    private float bLeft, bRight, bBottom, bTop;
    public Sheet(float left, float right, float bottom, float top) {
	bLeft = left * MM;
	bRight = right * MM;
	bBottom = bottom * MM;
	bTop = top * MM;
	flats = new ArrayList<Flat>();
    }
    /*
    public Sheet(Sheet sheet) throws  IOException,DocumentException{
	this.bLeft = sheet.bLeft;
	this.bRight = sheet.bRight;
	this.bBottom = sheet.bBottom;
	this.bTop = sheet.bBottom;
	for( Flat iFlat : sheet.flats){
	    this.flats.add(new Flat(iFlat));
	}
    }
    */
    
    public void initializeFlats() throws  IOException,DocumentException {
	for (Flat iFlat: flats){
	    iFlat.initialize();
	}
    }
    
    public Flat getFlat(int i) {
	return flats.get(i);
    }
    
    public void addFlat(Flat f) {
	flats.add(f);
    }
    
    public void output(File file) throws FileNotFoundException,
					 DocumentException {
	Document doc = new Document(PageSize.A4, bLeft, bRight, bTop, bBottom);
	PdfWriter writer = null;
		writer = PdfWriter.getInstance(doc, new FileOutputStream(file));
		doc.open();

		PdfContentByte content = writer.getDirectContent();

		float x = bLeft;
		float y = bBottom;
		for (Flat f : flats) {
			for (int i = 0; i < f.getCount(); i++) {
				if (f.getWidth() + x > PageSize.A4.getWidth() - bRight + 0.01f) {
					x = bLeft;
					y += f.getHeight();
				}
				if (y + bTop + f.getHeight() - PageSize.A4.getHeight() > -0.01f) {
					doc.newPage();
					y = bBottom;
					x = bLeft;
				}
				f.draw(content, x, y);
				x += f.getWidth();
			}
		}

		doc.close();
	}
    public final void writeObject(String fileName) throws IOException {
	try
	    {
		FileOutputStream fileOut =
		    new FileOutputStream(fileName);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();
		//System.out.printf("Serialized data is saved in /tmp/employee.ser");
	    }catch(IOException i)
	    {
		i.printStackTrace();
	    }
    }
}
