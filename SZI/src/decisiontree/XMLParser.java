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
import szi.data.Komorka;

public class XMLParser {
    public static String[] parametry = new String[4];
    public static String currentAttribute = "";
    public static String currentValue = "";
    
    public static void setCurrentParameters(String attribute){
        if (attribute.equals("fuel")){
            currentAttribute = "fuel";
            currentValue = parametry[0];           
        }
        else if (attribute.equals("distance")){
            currentAttribute = "distance";
            currentValue = parametry[1];
        }
        else if (attribute.equals("surface")){
            currentAttribute = "surface";
            currentValue = parametry[2];
        }
        else if (attribute.equals("maturity")){
            currentAttribute = "maturity";
            currentValue = parametry[3];
        }
        else{
            System.out.println("Błąd, Nie przyporządkowano atrubutu. " + attribute);
        }
    }
    
    public static void getDecision(String fuel, String distance, String surface, String plantMaturity) throws SAXException{
        parametry[0] = fuel;
        parametry[1] = distance;
        parametry[2] = surface;
        parametry[3] = plantMaturity;
        System.out.println("Parser - otrzymane parametry to: " + 
                parametry[0] + ", " + parametry[1] + ", " + parametry[2] + ", " + parametry[3]);
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