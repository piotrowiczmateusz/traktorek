/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi.NeuralNetwork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author magic
 */
public class Layer implements Serializable {

    private List<Neuron> neurons;
    private Layer previousLayer;
    private Layer nextLayer;
    private Neuron bias;

    public Layer() {
        neurons = new ArrayList<Neuron>();
        previousLayer = null;
    }

    public Layer(Layer previousLayer) {
        this();
        this.previousLayer = previousLayer;
    }

    public Layer(Layer previousLayer, Neuron bias) {
        this(previousLayer);
        this.bias = bias;
        neurons.add(bias);
    }

    public List<Neuron> getNeurons() {
        return this.neurons;
    }
    
    //dodwanie neuronu do warstwy
    public void addNeuron(Neuron neuron) {

        neurons.add(neuron);

        if(previousLayer != null) {
            for(Neuron previousLayerNeuron : previousLayer.getNeurons()) {
                neuron.addInput(new Synapse(previousLayerNeuron, (((Math.random())*1)-0.5))); // inicjalizacja z randomowymi wagami pomiędzy 1 i -1
            }
        }
    }
    
    //dodwanie neuronu z wagami do warstwy
    public void addNeuron(Neuron neuron, double[] weights) {

        neurons.add(neuron);

        if(previousLayer != null) {

            if(previousLayer.getNeurons().size() != weights.length) {
                throw new IllegalArgumentException("The number of weights supplied must be equal to the number of neurons in the previous layer");
            }

            else {
                //wyciągamy wszystkie neurony z poprzedniej warstwy i dodajemy synapse z waga do określonego neuronu
                List<Neuron> previousLayerNeurons = previousLayer.getNeurons();
                for(int i = 0; i < previousLayerNeurons.size(); i++) {
                    neuron.addInput(new Synapse(previousLayerNeurons.get(i), weights[i]));
                }
            }

        }
    }
    //funkcja która uruchamia na każdym neuronie funkcje aktywacji wraz z przeliczniem sumy wag
    public void feedForward() {

        int biasCount = hasBias() ? 1 : 0;

        for(int i = biasCount; i < neurons.size(); i++) {
            neurons.get(i).activate();
        }
    }
    
    //zwracanie poprzedniej warstwy
    public Layer getPreviousLayer() {
        return previousLayer;
    }
    
    //ustawianie poprzedniej warstwy
    void setPreviousLayer(Layer previousLayer) {
        this.previousLayer = previousLayer;
    }
    
    //zwracanie następnej warstwy
    public Layer getNextLayer() {
        return nextLayer;
    }
    
    //ustawianie nowej warstwy
    void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }

    public boolean isOutputLayer() {
        return nextLayer == null;
    }

    public boolean hasBias() {
        return bias != null;
    }
}