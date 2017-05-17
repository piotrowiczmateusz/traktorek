/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi.NeuralNetwork;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author magic
 */
public class NeuralNetwork implements Serializable {

    private String name;
    private List<Layer> layers;
    private Layer input;
    private Layer output;
    
    //konstruktor sieci
    public NeuralNetwork(String name) {
        this.name = name;
        layers = new ArrayList<Layer>();
    }

    /*public NeuralNetwork copy() {
        NeuralNetwork copy = new NeuralNetwork(this.name);

        Layer previousLayer = null;
        for(Layer layer : layers) {

            Layer layerCopy;
            //przypisywanie neuronu do biasa oraz tworzenie kopii biasa
            //tworzenie kopii warstwy jeśli warstwa ma bias
            if(layer.hasBias()) {
                Neuron bias = layer.getNeurons().get(0);
                Neuron biasCopy = new Neuron(bias.getActivationStrategy().copy());
                biasCopy.setOutput(bias.getOutput());
                layerCopy = new Layer(null, biasCopy);
            }

            else {
                layerCopy = new Layer();
            }
            //ustawianie poprzedniej warstwy
            layerCopy.setPreviousLayer(previousLayer);

            int biasCount = layerCopy.hasBias() ? 1 : 0;
            
            //tworzenie kopii neuronów, ustawanie dla skopiowanych neurownów wyjścia i błędu
            for(int i = biasCount; i < layer.getNeurons().size(); i++) {
                Neuron neuron = layer.getNeurons().get(i);

                Neuron neuronCopy = new Neuron(neuron.getActivationStrategy().copy());
                neuronCopy.setOutput(neuron.getOutput());
                neuronCopy.setError(neuron.getError());
                
                if(neuron.getInputs().size() == 0) {
                    layerCopy.addNeuron(neuronCopy);
                }

                else {
                    double[] weights = neuron.getWeights();
                    layerCopy.addNeuron(neuronCopy, weights);
                }
            }
            //dodwanie kopi warstwy oraz przypisywanie do poprzedniej warstwy, warstwy skopiowanej
            copy.addLayer(layerCopy);
            previousLayer = layerCopy;
        }

        return copy;
    }*/
    
    //Dodawanie nowej warstwy do sieci
    public void addLayer(Layer layer) {
        layers.add(layer);

        if(layers.size() == 1) {
            input = layer;
        }

        if(layers.size() > 1) {
            //czyszczenie flagi wyjścia na poprzedniej warstwie wyjścia, ale tylko jeśli jest więcej niż 1 warstwa
            Layer previousLayer = layers.get(layers.size() - 2);
            previousLayer.setNextLayer(layer);
        }

        output = layers.get(layers.size() - 1);
    }
    
    //ustawianie wejścia w sieci
    public void setInputs(double[] inputs) {
        if(input != null) {

            int biasCount = input.hasBias() ? 1 : 0;

            if(input.getNeurons().size() - biasCount != inputs.length) {
                throw new IllegalArgumentException("The number of inputs must equal the number of neurons in the input layer");
            }
            //Gdy ilość neruonów jest równa długości wejścia
            else {
                List<Neuron> neurons = input.getNeurons();
                for(int i = biasCount; i < neurons.size(); i++) {
                    neurons.get(i).setOutput(inputs[i - biasCount]);
                }
            }
        }
    }
    
    private static int[] getPixelData(BufferedImage img, int x, int y) {
        int argb = img.getRGB(x, y);

        int rgb[] = new int[] {
            (argb >> 16) & 0xff, //red
            (argb >>  8) & 0xff, //green
            (argb      ) & 0xff  //blue
        };
        //System.out.println("rgb: " + rgb[0] + " " + rgb[1] + " " + rgb[2]);
        return rgb;
    }
    
    public double[] getPixels(String nazwa) {
        BufferedImage img = null;
        double[][] pixelData;
        pixelData = new double [1600][3];
        try { 
              //System.out.println(System.getProperty("user.dir") + "\\src\\graphics\\"+ nazwa + ".png");
              img = ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\graphics\\"+ nazwa + ".png"));
              pixelData = new double[img.getHeight() * img.getWidth()][3];
              int[] rgb;
              int counter = 0;
              for(int i=0; i < img.getWidth(); i++){
                  for(int j=0; j < img.getHeight(); j++){
                      rgb = getPixelData(img, i, j);

                      for(int k = 0; k < rgb.length; k++){
                      pixelData[counter][k] = rgb[k];
                      //System.out.println("Pixele: " + (int) pixelData[counter][k]);
                  }
                  counter++;
                }
              }
          } catch (IOException e){
              e.printStackTrace();
          }
        
        return flatArray(pixelData);
        //return pixelData;
    }
    
    public double[] flatArray(double[][] a) {
        
        List<Double> toReturn = new ArrayList<Double>();
            for (double[] sublist : Arrays.asList(a)) {
                for (double elem : sublist) {
                    toReturn.add(elem);
                }
            }
        double[] target = new double[toReturn.size()];
        for (int i = 0; i < target.length; i++) {
           target[i] = toReturn.get(i);                // java 1.5+ style (outboxing)
        }
        return target;
    }
    
    
    public String getName() {
        return name;
    }
    
    //zwraca wyjście z sieci
    public double[] getOutput() {

        double[] outputs = new double[output.getNeurons().size()];

        for(int i = 1; i < layers.size(); i++) {
            Layer layer = layers.get(i);
            layer.feedForward();
        }

        int i = 0;
        for(Neuron neuron : output.getNeurons()) {
            outputs[i] = neuron.getOutput();
            i++;
        }

        return outputs;
    }
    
    //zwraca warstwe
    public List<Layer> getLayers() {
        return layers;
    }

    public void reset() {
        for(Layer layer : layers) {
            for(Neuron neuron : layer.getNeurons()) {
                for(Synapse synapse : neuron.getInputs()) {
                    synapse.setWeight((Math.random() * 1) - 0.5);
                }
            }
        }
    }
    
    //funkcja wyciagajaca wagi synaps i zapisująca je do listy
    public double[] getWeights() {

        List<Double> weights = new ArrayList<Double>();

        for(Layer layer : layers) {

            for(Neuron neuron : layer.getNeurons()) {
                //dodawanie wagi do listy
                for(Synapse synapse: neuron.getInputs()) {
                    weights.add(synapse.getWeight());
                }
            }
        }

        double[] allWeights = new double[weights.size()];
        
        //zapisywanie do tablicy wag z wszystkich synaps
        int i = 0;
        for(Double weight : weights) {
            allWeights[i] = weight;
            i++;
        }

        return allWeights;
    }
}
