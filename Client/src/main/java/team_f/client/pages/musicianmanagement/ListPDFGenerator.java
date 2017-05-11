package team_f.client.pages.musicianmanagement;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import team_f.client.pages.monthpublish.FooterHandler;
import team_f.jsonconnector.entities.EventDuty;
import team_f.jsonconnector.entities.Person;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by w7pro on 11.05.2017.
 */
public class ListPDFGenerator {
    private static final URL IMAGE = ClassLoader.getSystemResource("Logo2.jpg");
    private List<Person> _list;
   /* private final PdfFont _regular;
    private final PdfFont _bold;

    public ListPDFGenerator(List<Person> list,String directory) throws Exception {
        final String DEST = parseToPath(directory);
        _regular = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        _bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);

        _list = list;
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        manipulatePdf(DEST);
    }

    protected String parseToPath(String directory){
        StringBuilder finalPath=new StringBuilder();
        for(int i=0; i<directory.length();i++){
            if (directory.charAt(i)=='\\') {
                finalPath.append('/');
            }else{
                finalPath.append(directory.charAt(i));
            }
        }
        return finalPath.toString();
    }

    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterHandler());
        Image img = new Image(ImageDataFactory.create(IMAGE));
        img.scaleToFit(220, 120);
        img.setFixedPosition(600 , 500 );
        doc.add(img);

        Paragraph p=new Paragraph("Musician List");
        p.setFont(_bold);
        p.setFontSize(20);
        p.setMarginTop(35);
        doc.add(p);


        Table table = new Table(new float[]{1,1,1,1,1,1,1,1,1,1});

        table.addHeaderCell(new Cell().setFont(_bold).add("ID"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Initials"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Gender"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Start Date"));
        table.addHeaderCell(new Cell().setFont(_bold).add("End Date"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Location"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Conductor"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Description"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Points"));
        table.addHeaderCell(new Cell().setFont(_bold).add("Event Status"));

       if(_list != null && !_list.isEmpty()) {
            for (Person person : _list) {
                if (person.getPersonID() > 0) {
                    table.addCell(String.valueOf(person.getPersonID()));
                }
                else {
                    table.addCell(" ");
                }

                if (person.getEventType() != null) {
                    table.addCell(person.getEventType().toString());
                } else {
                    table.addCell(" ");
                }

                if (person.getName() != null) {
                    table.addCell(person.getName().toString());
                } else {
                    table.addCell(" ");
                }

                if (person.getStartTime() != null) {
                    table.addCell(person.getStartTime().toString());
                } else {
                    table.addCell(" ");
                }

                if (person.getEndTime() != null) {
                    table.addCell(person.getEndTime().toString());
                } else {
                    table.addCell(" ");
                }

                if (person.getLocation() != null) {
                    table.addCell(event.getLocation().toString());
                } else {
                    table.addCell(" ");
                }

                if (event.getConductor() != null) {
                    table.addCell(event.getConductor().toString());
                } else {
                    table.addCell(" ");
                }

                if (event.getDescription() != null) {
                    table.addCell(event.getDescription().toString());
                } else {
                    table.addCell(" ");
                }

                table.addCell(String.valueOf(event.getDefaultPoints()));

                if (event.getEventStatus() != null) {
                    table.addCell(event.getEventStatus().toString());
                } else {
                    table.addCell(" ");
                }
            }
            table.setMarginTop(15);
            doc.add(table);

        }

        doc.close();
        pdfDoc.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setGraphic(new ImageView("check.png"));
        alert.setHeaderText("Successfully converted selected Month to PDF");
        alert.showAndWait();

    }


}*/

}