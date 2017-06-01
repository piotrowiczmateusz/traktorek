package decisiontree;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
 
public class WekaTest {
    
        private static String DIR = "C:\\Users\\Mateusz\\Documents\\NetBeansProjects\\id3\\src\\weka\\";
        private static int parametersNumber = 7;
        
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
	public static Evaluation classify(Classifier model,
			Instances trainingSet, Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);
 
		model.buildClassifier(trainingSet);
		evaluation.evaluateModel(model, testingSet);
 
		return evaluation;
	}
 
	public static double calculateAccuracy(FastVector predictions) {
		double correct = 0;
 
		for (int i = 0; i < predictions.size(); i++) {
			NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
			if (np.predicted() == np.actual()) {
				correct++;
			}
		}
 
		return 100 * correct / predictions.size();
	}
 
	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
 
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
 
		return split;
	}
        
        public static void generateSetWater() throws IOException {
            
            String[] fuel = {"full", "much", "half", "little", "lack"};
            String[] water = {"full", "much", "half", "little", "lack"};
            String[] surface = {"Buraki", "Kukurydza", "Droga"};
            String[] maturity = {"developed", "well", "medium", "poorly"};
            String[] fuel_distance = {"far", "medium", "near"};
            String[] water_distance = {"far", "medium", "near"};
            String[] weather = {"sunny", "cloudy", "rainy"};
            
            FileWriter filestream;
            filestream = new FileWriter("C:\\Users\\Mateusz\\Documents\\traktorek\\SZI\\src\\decisiontree\\nawadnianie-generator.txt");
            BufferedWriter nawadnianie = new BufferedWriter(filestream);

            
            String gathering = "@relation gathering2\n" +
                                "\n" +
                                "@attribute fuel {full, much, half, little, lack}\n" +
                                "@attribute water {full, much, half, little, lack}\n" +
                                "@attribute surface {Buraki, Kukurydza, Droga}\n" +
                                "@attribute maturity {developed, well, medium, poorly}\n" +
                                "@attribute fuel_distance {far, medium, near}\n" +
                                "@attribute water_distance {far, medium, near}\n" +
                                "@attribute weather {sunny, cloudy, rainy}\n" +
                                "@attribute gather {yes, no}\n" +
                                "\n" +
                                "@data\n";
            
            nawadnianie.write(gathering);
            
            ArrayList<String> result = new ArrayList<String>();
            ArrayList<ArrayList<String>> resultArray = new ArrayList<ArrayList<String>>();

            for(int a = 0; a < fuel.length; a++) {
                for(int b = 0; b < water.length; b++) {
                    for(int c = 0; c < surface.length; c++) {
                        for(int d = 0; d < maturity.length; d++) {
                            for(int e = 0; e < fuel_distance.length; e++) {
                                for(int f = 0; f < water_distance.length; f++) {
                                    for(int g = 0; g < weather.length; g++) {
                                        result = new ArrayList<String>();
                                        result.add(fuel[a]);
                                        result.add(water[b]);
                                        result.add(surface[c]);
                                        result.add(maturity[d]);
                                        result.add(fuel_distance[e]);
                                        result.add(water_distance[f]);
                                        result.add(weather[g]);
                                        
                                        if(result.get(6) == "rainy") {
                                            result.add("no");
                                        }
                                        else if (result.get(2) == "Droga") {
                                            result.add("no");
                                        }
                                        else if (result.get(0) == "lack" && result.get(4) != "near") {
                                            result.add("no");
                                        }
                                        else if (result.get(0) == "little" && (result.get(4) != "near" || result.get(4) != "medium")) {
                                            result.add("no");
                                        }
                                        else if (result.get(0) == "half" && (result.get(4) != "near" || result.get(4) != "medium")) {
                                            result.add("no");
                                        }
                                        else if (result.get(1) == "lack" && result.get(5) != "near") {
                                            result.add("no");
                                        }
                                        else if (result.get(1) == "little" && (result.get(5) != "near" || result.get(5) != "medium")) {
                                            result.add("no");
                                        }
                                        else if (result.get(1) == "half" && (result.get(5) != "near" || result.get(5) != "medium")) {
                                            result.add("no");
                                        }
                                        else if (result.get(3) == "poorly") {
                                            result.add("no");
                                        }
                                        else {
                                            result.add("yes");
                                        }
                                        resultArray.add(result);
                                    } 
                                }
                            }
                        }
                    }
                }
            }
            
            ArrayList<String> selectedArray = new ArrayList<String>();
            for(int i =0; i < 1000; i++) {
                int randomNum = 0 + (int)(Math.random() * 8100); 
                String wynik = "";
                for(int j =0; j < 8; j++) {
                    if(j != 7) {
                       wynik += resultArray.get(randomNum).get(j) + ", ";
                    } else {
                        wynik += resultArray.get(randomNum).get(j);
                    }                 
                }
                nawadnianie.write(wynik+"\n");
                selectedArray.add(wynik);
            }
            nawadnianie.close();
        }
       
	public static void main(String[] args) throws Exception {
            
                generateSetWater();

		BufferedReader datafile = readDataFile("C:\\Users\\Mateusz\\Documents\\traktorek\\SZI\\src\\decisiontree\\nawadnianie.txt");
 
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
 
		Instances[][] split = crossValidationSplit(data, parametersNumber);
		Instances[] trainingSplits = split[0];
		Instances[] testingSplits = split[1];
                
                J48 j48 = new J48();

                FastVector predictions = new FastVector();
                
                Evaluation validation = classify(j48, trainingSplits[parametersNumber-1], testingSplits[parametersNumber-1]);          
                System.out.println(j48);
                
                 /* ZAPISYWANIE DO PLIKU */

                PrintWriter out = new PrintWriter(DIR + "out.txt");
                out.println(j48.graph());

                /* OKNO Z GRAFEM */
                
                final javax.swing.JFrame jf = 
                  new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
                jf.setSize(1366,768);
                jf.getContentPane().setLayout(new BorderLayout());
                TreeVisualizer tv = new TreeVisualizer(null,
                    j48.graph(),
                    new PlaceNode2());
                jf.getContentPane().add(tv, BorderLayout.CENTER);
                jf.addWindowListener(new java.awt.event.WindowAdapter() {
                  public void windowClosing(java.awt.event.WindowEvent e) {
                    jf.dispose();
                  }
                });

                jf.setVisible(true);
                tv.fitToScreen();	
        }
}