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
    
    // Zbieranie
    
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
            System.out.println("Błąd, Nie przyporządkowano atrubutu. +" + attribute+"+");
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
            Document doc = builder.parse("./src/decisiontree/zbieranie.xml");
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
                            System.out.println("Atrybut nr 1 = " + attribute + " (" + currentValue + ")");
                            
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
                                            System.out.println("Atrybut nr 2 = " + attribute + " (" + currentValue + ")");
                                                                       
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
                                                            System.out.println("Atrybut nr 3 = " + attribute + " (" + currentValue + ")");
                                                          
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
                                                                            System.out.println("Atrybut nr 4 = " + attribute + " (" + currentValue + ")");
                                                                          
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

    // Nawadnianie
    
    public static String[] parametryWoda = new String[7];
    
    public static void setCurrentParametersWater(String attribute) {
        if (attribute.equals("fuel")){
            currentAttribute = "fuel";
            currentValue = parametryWoda[0];           
        }
        if (attribute.equals("water")){
            currentAttribute = "water";
            currentValue = parametryWoda[1];           
        }
        else if (attribute.equals("surface")){
            currentAttribute = "surface";
            currentValue = parametryWoda[2];
        }
        else if (attribute.equals("maturity")){
            currentAttribute = "maturity";
            currentValue = parametryWoda[3];
        }
        else if (attribute.equals("fuel_distance")){
            currentAttribute = "fuel_distance";
            currentValue = parametryWoda[4];
        }
        else if (attribute.equals("water_distance")){
            currentAttribute = "water_distance";
            currentValue = parametryWoda[5];
        }
 
        else if (attribute.equals("weather")){
            currentAttribute = "weather";
            currentValue = parametryWoda[6];           
        }
        
        else{
//            System.out.println("Błąd, Nie przyporządkowano atrubutu. -" + attribute+"-");
        }
    }

    public static void getDecisionWater(String fuel, String water, String distance, String waterDistance, String surface, String maturity, String weather) throws SAXException {
        parametryWoda[0] = fuel;
        parametryWoda[1] = water;
        parametryWoda[2] = surface;
        parametryWoda[3] = maturity;
        parametryWoda[4] = distance;
        parametryWoda[5] = waterDistance;
        parametryWoda[6] = weather;
        System.out.println("Parser - otrzymane parametry to: " + 
                parametryWoda[0] + ", " + parametryWoda[1] + ", " + 
                parametryWoda[2] + ", " + parametryWoda[3] + ", " +
                parametryWoda[4] + ", " + parametryWoda[5] + ", " + 
                parametryWoda[6]);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("./src/decisiontree/nawadnianie.xml");
            NodeList attributeList = doc.getElementsByTagName("Test");            
            Node lvlOne = attributeList.item(0); //pobieramy korzeń drzewa - atrybut
            if(lvlOne.getNodeType()==Node.ELEMENT_NODE){
                Element rootAttr = (Element) lvlOne;
                String attribute = rootAttr.getAttribute("attribute");    
                setCurrentParametersWater(attribute);
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
                            System.out.println("Atrybut nr 1 = " + attribute + " (" + currentValue + ")");
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
                                        setCurrentParametersWater(attribute);
                                        if((attribute.equals(currentAttribute)) &&(value.equals(currentValue))){
                                            System.out.println("Atrybut nr 2 = " + attribute + " (" + currentValue +")");                        
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
                                                        setCurrentParametersWater(attribute);
                                                        if((attribute.equals(currentAttribute)) &&(value.equals(currentValue))){
                                                            System.out.println("Atrybut nr 3 = " + attribute+ " (" + currentValue + ")");
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
                                                                        setCurrentParametersWater(attribute);
                                                                        if((attribute.equals(currentAttribute)) &&(value.equals(currentValue))){
                                                                            System.out.println("Atrybut nr 4 = " + attribute + " (" + currentValue + ")"); 
                                                                            
                                                                            NodeList childListFour = fourthAttr.getChildNodes();


                                                            for( int m=0; m<childListFour.getLength(); m++){
                                                                Node lvlFive = childListFour.item(m);
                                                                if(lvlFive.getNodeType()==Node.ELEMENT_NODE){
                                                                    Element fifthAttr = (Element) lvlFive;
                                                                    tag = fifthAttr.getTagName();
                                                                    if (tag.equals("Output")){
                                                                        String decision = fifthAttr.getAttribute("decision");
                                                                        System.out.println("Koniec poszukiwań. Decyzja: " + decision);
                                                                        end = true;

                                                                    }
                                                                    else{
                                                                        attribute = fifthAttr.getAttribute("attribute");
                                                                        value = fifthAttr.getAttribute("value");
                                                                        setCurrentParametersWater(attribute);
                                                                        if((attribute.equals(currentAttribute)) &&(value.equals(currentValue))){
                                                                            System.out.println("Atrybut nr 5 = " + attribute+ " (" + currentValue + ")");                                                
                                                                            
                                                                            NodeList childListFive = fourthAttr.getChildNodes();


                                                            for( int n=0; n<childListFive.getLength(); n++){
                                                                Node lvlSix = childListFive.item(n);
                                                                if(lvlSix.getNodeType()==Node.ELEMENT_NODE){
                                                                    Element sixthAttr = (Element) lvlSix;
                                                                    tag = sixthAttr.getTagName();
                                                                    if (tag.equals("Output")){
                                                                        String decision = sixthAttr.getAttribute("decision");
                                                                        System.out.println("Koniec poszukiwań. Decyzja: " + decision);
                                                                        end = true;

                                                                    }
                                                                    else{
                                                                        attribute = sixthAttr.getAttribute("attribute");
                                                                        value = sixthAttr.getAttribute("value");
                                                                        setCurrentParametersWater(attribute);
                                                                        if((attribute.equals(currentAttribute)) &&(value.equals(currentValue))){
                                                                            System.out.println("Atrybut nr 6 = " + attribute + " (" + currentValue + ")");
                                                                          
                                                                            
                                                                             NodeList childListSix = sixthAttr.getChildNodes();


                                                            for( int o=0; o<childListSix.getLength(); o++){
                                                                Node lvlSeven = childListSix.item(o);
                                                                if(lvlSeven.getNodeType()==Node.ELEMENT_NODE){
                                                                    Element seventhAttr = (Element) lvlSeven;
                                                                    tag = seventhAttr.getTagName();
                                                                    if (tag.equals("Output")){
                                                                        String decision = seventhAttr.getAttribute("decision");
                                                                        System.out.println("Koniec poszukiwań. Decyzja: " + decision);
                                                                        end = true;

                                                                    }
                                                                    else{
                                                                        attribute = seventhAttr.getAttribute("attribute");
                                                                        value = seventhAttr.getAttribute("value");
                                                                        setCurrentParametersWater(attribute);
                                                                        if((attribute.equals(currentAttribute)) &&(value.equals(currentValue))){
                                                                            System.out.println("Atrybut nr 7 = " + attribute + " (" + currentValue + ")");                                                                          
                                                                            
                                                                            NodeList childListSeven = seventhAttr.getChildNodes();
                                                                            
                                                            for( int p=0; p<childListSeven.getLength(); p++){
                                                                Node lvlEight = childListSeven.item(o);
                                                                if(lvlEight.getNodeType()==Node.ELEMENT_NODE){
                                                                    Element eighthAttr = (Element) lvlEight;
                                                                    tag = eighthAttr.getTagName();
                                                                    if (tag.equals("Output")){
                                                                        String decision = eighthAttr.getAttribute("decision");
                                                                        System.out.println("Koniec poszukiwań. Decyzja: " + decision);
                                                                        end = true;

                                                                    }
                                                                    else{
                                                                        attribute = eighthAttr.getAttribute("attribute");
                                                                        value = eighthAttr.getAttribute("value");
                                                                        setCurrentParametersWater(attribute);
                                                                        if((attribute.equals(currentAttribute)) &&(value.equals(currentValue))){
                                                                            System.out.println("Atrybut nr 8 = " + attribute);
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