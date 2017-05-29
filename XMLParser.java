package decisiontree;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLParser {
    public static String[] parametry = new String[5];
    public static String currentAttribute = "";
    public static String currentValue = "";
    
    public static void getTractorParameters(){
        parametry[0] = "polowa";
        parametry[1] = "duza";
        parametry[2] = "droga";
        parametry[3] = "true";
        parametry[4] = "gotowe";
    }
    
    public static void setCurrentParameters(String attribute){
        if (attribute.equals("paliwo")){
            currentAttribute = "paliwo";
            currentValue = parametry[0];           
        }
        else if (attribute.equals("odl_zb")){
            currentAttribute = "odl_zb";
            currentValue = parametry[1];
        }
        else if (attribute.equals("roslina")){
            currentAttribute = "roslina";
            currentValue = parametry[2];
        }
        else if (attribute.equals("dojrzala")){
            currentAttribute = "dojrzala";
            currentValue = parametry[3];
        }
        else if (attribute.equals("czas")){
            currentAttribute = "czas";
            currentValue = parametry[4];
        }
        else{
            System.out.println("Błąd, Nie przyporządkowano atrubutu.");
        }
    }
    
    public static void getDecision() throws SAXException{
       
        getTractorParameters();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("./src/decisiontree/out.xml");
            NodeList attributeList = doc.getElementsByTagName("Test");            
            Node lvlOne = attributeList.item(0); //pobieramy korzeń drzewa - atrybut
            if(lvlOne.getNodeType()==Node.ELEMENT_NODE){
                Element rootAttr = (Element) lvlOne;
                String attribute = rootAttr.getAttribute("attribute");    
                setCurrentParameters(attribute);
            }
            boolean end = false;
            int i=0;
            while((i<attributeList.getLength()) && (end==false)){
                lvlOne = attributeList.item(i);
                if(lvlOne.getNodeType()==Node.ELEMENT_NODE){
                    Element rootAttr = (Element) lvlOne;
                    String tag = rootAttr.getTagName();
                    if (tag.equals("Output")){
                        String decision = rootAttr.getAttribute("decision");
                        System.out.println("Koniec poszukiwań. Decyzja: " + decision);
                        end = true;

                    }
                    else{
                        String attribute = rootAttr.getAttribute("attribute");
                        String value = rootAttr.getAttribute("value");                    
                        if((attribute.equals(currentAttribute)) &&(value.equals(currentValue))){
                            System.out.println("Atrybut root = " + attribute);
                            System.out.println("Parametr traktora: " + currentValue);
                            NodeList childList = rootAttr.getChildNodes();


                            for( int j=0; j<childList.getLength(); j++){
                                Node lvlTwo = childList.item(j);
                                if(lvlTwo.getNodeType()==Node.ELEMENT_NODE){
                                    Element secondAttr = (Element) lvlTwo;
                                    tag = secondAttr.getTagName();
                                    if (tag.equals("Output")){
                                        String decision = secondAttr.getAttribute("decision");
                                        System.out.println("Koniec poszukiwań. Decyzja: " + decision);
                                        end = true;

                                    }
                                    else{
                                        attribute = secondAttr.getAttribute("attribute");
                                        value = secondAttr.getAttribute("value");
                                        setCurrentParameters(attribute);
                                        if((attribute.equals(currentAttribute)) &&(value.equals(currentValue))){
                                            System.out.println("Atrybut nr 2 = " + attribute);
                                            System.out.println("Parametr traktora: " + currentValue);                               
                                            NodeList childListTwo = secondAttr.getChildNodes();


                                            for( int k=0; k<childListTwo.getLength(); k++){
                                                Node lvlTree = childListTwo.item(k);
                                                if(lvlTree.getNodeType()==Node.ELEMENT_NODE){
                                                    Element thirdAttr = (Element) lvlTree;
                                                    tag = thirdAttr.getTagName();
                                                    if (tag.equals("Output")){
                                                    String decision = thirdAttr.getAttribute("decision");
                                                    System.out.println("Koniec poszukiwań. Decyzja: " + decision);
                                                    end = true;

                                                    }
                                                    else{
                                                        attribute = thirdAttr.getAttribute("attribute");
                                                        value = thirdAttr.getAttribute("value");
                                                        setCurrentParameters(attribute);
                                                        if((attribute.equals(currentAttribute)) &&(value.equals(currentValue))){
                                                            System.out.println("Atrybut nr 3 = " + attribute);
                                                            System.out.println("Parametr traktora: " + currentValue);
                                                            NodeList childListTree = thirdAttr.getChildNodes();


                                                            for( int l=0; l<childListTree.getLength(); l++){
                                                                Node lvlFour = childListTree.item(l);
                                                                if(lvlFour.getNodeType()==Node.ELEMENT_NODE){
                                                                    Element fourthAttr = (Element) lvlFour;
                                                                    tag = fourthAttr.getTagName();
                                                                    if (tag.equals("Output")){
                                                                        String decision = fourthAttr.getAttribute("decision");
                                                                        System.out.println("Koniec poszukiwań. Decyzja: " + decision);
                                                                        end = true;

                                                                    }
                                                                    else{
                                                                        attribute = fourthAttr.getAttribute("attribute");
                                                                        value = fourthAttr.getAttribute("value");
                                                                        setCurrentParameters(attribute);
                                                                        if((attribute.equals(currentAttribute)) &&(value.equals(currentValue))){
                                                                            System.out.println("Atrybut nr 4 = " + attribute);
                                                                            System.out.println("Parametr traktora: " + currentValue);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                i++;
            }
        } catch (ParserConfigurationException e){
            e.printStackTrace();
        } catch (SAXException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }    
}