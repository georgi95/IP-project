import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Iterate {
	
	private static final int NODE_TYPE_ELEMENT = 1;

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException, TransformerException {

		final Document document = openDocument("src/input.xml");
		process(document);
		saveDocument(document, "src/output.xml");
	}


	private static void saveDocument(Document document, String filename) throws FileNotFoundException, TransformerException {
		final TransformerFactory factory = TransformerFactory.newInstance();
		factory.setAttribute("indent-number", 2);
		final Transformer transformer = factory.newTransformer();
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		final StreamResult out = new StreamResult(new FileOutputStream(
				new File(filename)));
		transformer.transform(new DOMSource(document), out);

	}

	private static void process(Document document) {
		final Node root = document.getFirstChild();
		final NodeList children = root.getChildNodes();
		
		for (int i = 0; i < children.getLength(); i++) {
			final Node item = children.item(i); 
						
			if(item.getNodeType() == NODE_TYPE_ELEMENT) {
				final Element element = (Element) item;
				String txt = element.getTextContent(); 
				
				 List<String> list = Arrays.asList(txt.split(" "));
				 Element countChild = document.createElement("count"); 
				 root.appendChild(countChild);
				 
			        Set<String> uniqueWords = new HashSet<String>(list);
			        for (String word : uniqueWords) {
			            //System.out.println(word + ": " + Collections.frequency(list, word));
			    		Element newChild = document.createElement("word"); 
			    		countChild.appendChild(newChild);
			            newChild.setAttribute("count",Integer.toString(Collections.frequency(list, word)));
			            newChild.setTextContent(word);
			            
				    }  
			        
			       break;
			}			
	  }
}

	
	public static void countLetters(String txt){
		 
		int SL = txt.length();
		if(SL > 0) System.out.println(SL);
		
	}
	
	public static void countWords(String str){


        int count = 0;
        for(int e = 0; e < str.length(); e++){
            if(str.charAt(e) != ' '){
                count++;
                while(str.charAt(e) != ' ' && e < str.length()-1){
                    e++;
                }
            }
        }
        if(count > 0 ) System.out.println(count);
        
    }
	

	private static Document openDocument(String filename)
			throws ParserConfigurationException, SAXException, IOException {

		final DocumentBuilderFactory factory = DocumentBuilderFactory
				.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();

		return builder.parse(new File(filename));
	}
}
