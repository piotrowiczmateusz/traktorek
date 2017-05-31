/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi.NeuralNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author magic
 */
public class runNetwork {
    public NeuralNetwork neuralNetwork;
    public TrainingData trainingDataGenerator;
    public Backpropagation backpropagator;
    
    public runNetwork(){}

    public runNetwork(double learningRate, double momentum, double meanError, String name) throws IOException {
        //inicjalizowanie sieci neuronowej, warstwy wejścia, biasa wejściowego
        if(name == "Droga"){
            neuralNetwork = new NeuralNetwork("Droga Network");
        }
        else if(name == "Rosliny"){
            neuralNetwork = new NeuralNetwork("Traktor Network");
        }
        Neuron inputBias = new Neuron(new LinearActivationStrategy());
        inputBias.setOutput(1);

        Layer inputLayer = new Layer(null, inputBias);
        
        //dodawanie nowego neuronu do warstwy wejścia (przypisanie każdemu neuronowi funkcji aktywacji sigmoid)
        for(int i = 0; i < 4800; i++) {
            Neuron neuron = new Neuron(new SigmoidActivationStrategy());
            //neuron.setOutput(0);
            inputLayer.addNeuron(neuron);
        }
        
        //inicjalizowanie warstwy ukrytej, wraz z biasem
        Neuron hiddenBias = new Neuron(new LinearActivationStrategy());
        hiddenBias.setOutput(1);

        Layer hiddenLayer = new Layer(inputLayer, hiddenBias);
        
        //ilość neuronów w warstwie ukrytej
        long numberOfHiddenNeurons = 15;
        
        //dodawanie neuronów do warstwy ukrytej(przypisanie każdemu neuronowi funkcji aktywacji sigmoid)
        for(int i = 0; i < numberOfHiddenNeurons; i++) {
            Neuron neuron = new Neuron(new SigmoidActivationStrategy());
            //neuron.setOutput(0);
            hiddenLayer.addNeuron(neuron);
        }
        
        //inicjalizowanie warstwy wyjścia, dodwanie neuronów do warstwy wyjścia(przypisanie każdemu neuronowi funkcji aktywacji sigmoid)
        Layer outputLayer = new Layer(hiddenLayer);

        Neuron neuron = new Neuron(new SigmoidActivationStrategy());
        //neuron.setOutput(0);
        outputLayer.addNeuron(neuron);

        //dodwanie wszystkich utworzonych warstw do sieci neuronowej
        neuralNetwork.addLayer(inputLayer);
        neuralNetwork.addLayer(hiddenLayer);
        neuralNetwork.addLayer(outputLayer);
        
        //wprowadznie danych treningowych(wejściowych i wyjściowych)
        //inicjalizacja klasy od propagacji wstecznej
        //odpalenie funkcji trenującej korzystającej z algorytmu propagacji wstecznej
        if(name == "Droga") {
            trainingDataGenerator = new TrainingData(initInputsRoad(),initOutputsRoad());
        }
        else if(name == "Rosliny") {
            trainingDataGenerator = new TrainingData(initInputs(),initOutputs());
        }
        backpropagator = new Backpropagation(neuralNetwork, learningRate, momentum, 0);
        backpropagator.train(trainingDataGenerator, meanError);
    }
    
    //odczyt danych treningowych z pliku
    private static double[][] initInputs() throws IOException{
        double[][] trainingData = new double [168][4800];
        NeuralNetwork n = new NeuralNetwork("Test");
        String FILE_PATH = System.getProperty("user.dir") + "\\src\\szi\\NeuralNetwork\\inputData1.csv";
        FileReader fileReader = new FileReader(FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int i = 0;
        try {
            String textLine = bufferedReader.readLine();
            do {
                //String nazwa = null;
                trainingData[i] = n.getPixels(textLine);
                i++;
                textLine = bufferedReader.readLine();
            } while(textLine != null);
        }
        finally{
            bufferedReader.close();
        }
        return trainingData;
    }
    
    //odczyt danych treningowych z pliku
    private static double[][] initInputsRoad() throws IOException{
        double[][] trainingData = new double [130][4800];
        NeuralNetwork n = new NeuralNetwork("Test");
        String FILE_PATH = System.getProperty("user.dir") + "\\src\\szi\\NeuralNetwork\\inputData2.csv";
        FileReader fileReader = new FileReader(FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int i = 0;
        try {
            String textLine = bufferedReader.readLine();
            do {
                trainingData[i] = n.getPixels(textLine);
                i++;
                textLine = bufferedReader.readLine();
            } while(textLine != null);
        }
        finally{
            bufferedReader.close();
        }
        return trainingData;
    }
    
    private static double[][] initOutputs() throws IOException{
        double[][] labels = new double[168][1] ;
        String FILE_PATH = System.getProperty("user.dir") + "\\src\\szi\\NeuralNetwork\\outputData1.csv";
        FileReader fileReader = new FileReader(FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int i = 0;
        try {
            String textLine = bufferedReader.readLine();
            do {
                labels[i][0]=Double.parseDouble(textLine);
                i++;
                textLine = bufferedReader.readLine();
            } while(textLine != null);
        }
        finally{
            bufferedReader.close();
        }
        return labels;
    }
    
    private static double[][] initOutputsRoad() throws IOException{
        double[][] labels = new double[130][1] ;
        String FILE_PATH = System.getProperty("user.dir") + "\\src\\szi\\NeuralNetwork\\outputData2.csv";
        FileReader fileReader = new FileReader(FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int i = 0;
        try {
            String textLine = bufferedReader.readLine();
            do {
                labels[i][0]=Double.parseDouble(textLine);
                i++;
                textLine = bufferedReader.readLine();
            } while(textLine != null);
        }
        finally{
            bufferedReader.close();
        }
        return labels;
    }
}
