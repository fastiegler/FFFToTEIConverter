package teiObjects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Created by fstiegle on 20.05.2017.
 */
public class TEI
{
    /**
     * The object representing the XML
     */
    private Document xmlDocument;

    /**
     * The object representing the TEIHeader - Node
     */
    private TEIHeader teiheader;
    /**
     * The object representing the Text - Node
     */
    private Text text;

    public TEI() throws ParserConfigurationException {
        xmlDocument = createXMLDocument();
        createBasicTEIStructure();
    }

    public Document createXMLDocument() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }

    /**
     * Creates a new temp xml File in the systems default temp folder and writes the TEI object into the xml.
     *
     * @return The TempXML.xml File
     * @throws IOException
     * @throws TransformerException
     */
    public File wirteXMLDocToFile() throws IOException, TransformerException {
        File xmlFile = File.createTempFile("TempXML", ".xml");
        wirteXMLDocToFile(xmlFile);
        return xmlFile;
    }

    /**
     * Writes the TEI object into the given XML-File.
     *
     * @param outputFile The File in wich the TEI Object will be written
     * @throws TransformerException
     */
    public void wirteXMLDocToFile(File outputFile) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(xmlDocument);
        StreamResult result = new StreamResult(outputFile);
        transformer.transform(source, result);
    }

    /**
     * Creates a basic TEI structure like:
     *
     * <TEI>
     *     <teiHeader>
     *     </teiHeader>
     *     <text>
     *     </text>
     * </TEI>
     *
     */
    private void createBasicTEIStructure() {
        //creating the Root element <TEI>
        Element TEIRootElement = xmlDocument.createElement("TEI");
        TEIRootElement.setAttributeNS("http://www.tei-c.org/ns/1.0", "xmlns:xsd" , "http://www.tei-c.org/ns/1.0");
        xmlDocument.appendChild(TEIRootElement);

        //create the child node teiHeader
        Element teiheader = xmlDocument.createElement("teiHeader");
        TEIRootElement.appendChild(teiheader);
        this.teiheader = new TEIHeader(teiheader);

        //create the child note text
        Element text = xmlDocument.createElement("text");
        TEIRootElement.appendChild(text);
        this.text = new Text(text);
    }

    public Document getXmlDocument() {
        return xmlDocument;
    }

    public void setXmlDocument(Document xmlDocument) {
        this.xmlDocument = xmlDocument;
    }

    public TEIHeader getTeiheader() {
        return teiheader;
    }

    public void setTeiheader(TEIHeader teiheader) {
        this.teiheader = teiheader;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
