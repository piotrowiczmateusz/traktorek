package decisionTree;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
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
        private static int parametersNumber = 4;
        
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
 
	public static void main(String[] args) throws Exception {
            
            
                int[] paliwo = new int[100];
                for(int i = 0; i < 100; i++) {
                    paliwo[i] = i;
                }
                
                int[] odleglosc = new int[21];
                for(int i = 0; i < 21; i++) {
                    odleglosc[i] = i;
                }
                
                int[] odleglosc_zb = new int[21];
                for(int i = 0; i < 21; i++) {
                    odleglosc_zb[i] = i;
                }
                
                String[] roslina = new String[3];
                roslina[0] = "burak";
                roslina[1] = "kukurydza";
                roslina[2] = "droga";
                
                boolean[] dojrzala = new boolean[2];
                dojrzala[0] = true;
                dojrzala[1] = false;
                
                int[] czas = new int[10];
                for(int i = 0; i < 10; i++) {
                    czas[i] = i;
                }
                
                String wynik = "";
                
                for(int a = 0; a < 100; a = a + 10) {
                    for(int b = 0; b < 21; b = b + 3) {
                        for(int c = 0; c < 21; c++) {
                            for(int d = 0; d < 3; d++) {
                                for(int e = 0; e < 2; e++) {
                                     for(int f = 0; f < 10; f++) {
                                         wynik += paliwo[a]+ ", " +
                                                 odleglosc[b] +", " +
                                                 odleglosc_zb[c]+", " +
                                                 roslina[d]+", " + 
                                                 dojrzala[e]+", " +
                                                 czas[f] + "\n";
                                      
                                    }
                                }
                            }
                        }
                    }
                }
                
                PrintWriter out2 = new PrintWriter(DIR + "out2.txt");
                out2.println(wynik);
            
 
		BufferedReader datafile = readDataFile(DIR + "weather.txt");
 
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
 
		Instances[][] split = crossValidationSplit(data, parametersNumber);
		Instances[] trainingSplits = split[0];
		Instances[] testingSplits = split[1];
                
                J48 j48 = new J48();

                FastVector predictions = new FastVector();
                
                Evaluation validation = classify(j48, trainingSplits[parametersNumber-1], testingSplits[parametersNumber-1]);
                predictions.appendElements(validation.predictions());
                double accuracy = calculateAccuracy(predictions);
                System.out.println(j48);
                
                 /* ZAPISYWANIE DO PLIKU */

                PrintWriter out = new PrintWriter(DIR + "out.txt");
                out.println(j48.graph());

                /* OKNO Z GRAFEM */
                
                final javax.swing.JFrame jf = 
                  new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
                jf.setSize(500,400);
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