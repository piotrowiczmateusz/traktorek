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

    public runNetwork(double learningRate, double momentum, double meanError) throws IOException {
        neuralNetwork = new NeuralNetwork("Traktor Network");
        Neuron inputBias = new Neuron(new LinearActivationStrategy());
        inputBias.setOutput(1);

        Layer inputLayer = new Layer(null, inputBias);

        for(int i = 0; i < 2; i++) {
            Neuron neuron = new Neuron(new SigmoidActivationStrategy());
            neuron.setOutput(0);
            inputLayer.addNeuron(neuron);
        }

        Neuron hiddenBias = new Neuron(new LinearActivationStrategy());
        hiddenBias.setOutput(1);

        Layer hiddenLayer = new Layer(inputLayer, hiddenBias);

        long numberOfHiddenNeurons = 13;

        for(int i = 0; i < numberOfHiddenNeurons; i++) {
            Neuron neuron = new Neuron(new SigmoidActivationStrategy());
            neuron.setOutput(0);
            hiddenLayer.addNeuron(neuron);
        }

        Layer outputLayer = new Layer(hiddenLayer);

        Neuron neuron = new Neuron(new SigmoidActivationStrategy());
        neuron.setOutput(0);
        outputLayer.addNeuron(neuron);


        neuralNetwork.addLayer(inputLayer);
        neuralNetwork.addLayer(hiddenLayer);
        neuralNetwork.addLayer(outputLayer);

        trainingDataGenerator = new TrainingData(initInputs(),initOutputs());
        backpropagator = new Backpropagation(neuralNetwork, learningRate, momentum, 0);
        backpropagator.train(trainingDataGenerator, meanError);
    }
    private static double[][] initInputs() throws IOException{
        double[][] trainingData= new double [70][2];//= new String[10][];
        String FILE_PATH = "D:\\Projekty\\traktorek\\SZI\\src\\szi\\NeuralNetwork\\inputData1.csv";
        FileReader fileReader = new FileReader(FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int i = 0;
        try {
            String textLine = bufferedReader.readLine();
            do {
                //System.out.println(textLine);
                String text[] = null;
                text = textLine.split(";");
                for(int j=0; j<text.length; j++){
                    trainingData[i][j]=Double.parseDouble(text[j]);
                }
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
        double[][] labels = new double[70][1] ;//= new String[10][];
        String FILE_PATH = "D:\\Projekty\\traktorek\\SZI\\src\\szi\\NeuralNetwork\\outputData1.csv";
        FileReader fileReader = new FileReader(FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int i = 0;
        try {
            String textLine = bufferedReader.readLine();
            do {
                // System.out.println(textLine);
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
