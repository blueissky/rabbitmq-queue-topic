package Maple.duan.ApacheMQ.topicTest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.camel.impl.converter.ToStringTypeConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
public class DomCreateDoucment {
	/**
	 * create dom and transfor to xml string
	 * @param args
	 * @throws Exception
	 */
	public static String createDom() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		//构建XML中的节点
		Element root = doc.createElement("font");
		/**------<name>----------**/
		Element nameElement = doc.createElement("name");
		Text nameValue = doc.createTextNode("san");
		/**--------<size>--------**/
		Element sizeElement = doc.createElement("size");
		Text sizeValue = doc.createTextNode("14");
//		sizeElement.setAttribute("unit", "px");
       //按顺序添加各个节点
		doc.appendChild(root);
		/**-------<name>----------**/
		root.appendChild(nameElement);
		nameElement.appendChild(nameValue);
		/**--------<size>---------**/
		root.appendChild(sizeElement);
		sizeElement.appendChild(sizeValue);

		Transformer t=TransformerFactory.newInstance().newTransformer();
		//设置换行和缩进
		t.setOutputProperty(OutputKeys.INDENT,"yes");
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		
		StringWriter strWtr = new StringWriter();  
        StreamResult strResult = new StreamResult(strWtr);
        t.transform(new DOMSource(doc), strResult);
        String str=strResult.getWriter().toString();
        System.out.println(str);
        strWtr.close();
        return str;
//		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(new File("f:/text.xml"))));
	}
	 /*  
     * 把dom文件转换为xml字符串  
     */  
    public static String toStringFromDoc(Document document) {  
        String result = null;  
  
        if (document != null) {  
            StringWriter strWtr = new StringWriter();  
            StreamResult strResult = new StreamResult(strWtr);  
            TransformerFactory tfac = TransformerFactory.newInstance();  
            try {  
                Transformer t = tfac.newTransformer();  
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");  
                t.setOutputProperty(OutputKeys.INDENT, "yes");  
                t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,  
                // text  
                t.setOutputProperty(  
                        "{http://xml.apache.org/xslt}indent-amount", "4");  
                t.transform(new DOMSource(document.getDocumentElement()),  
                        strResult);  
            } catch (Exception e) {  
                System.err.println("XML.toString(Document): " + e);  
            }  
            result = strResult.getWriter().toString();  
            try {  
                strWtr.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
  
        return result;  
    }  
}
