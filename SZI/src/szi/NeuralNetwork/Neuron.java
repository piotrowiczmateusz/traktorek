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
public class Neuron implements Serializable {

    private List<Synapse> inputs;
    private ActivationStrategy activationStrategy;
    private double output;
    private double derivative;
    private double weightedSum;
    private double error;

    public Neuron(ActivationStrategy activationStrategy) {
        inputs = new ArrayList<Synapse>();
        this.activationStrategy = activationStrategy;
        error = 0;
    }
    
    //dodawanie synapsy
    public void addInput(Synapse input) {
        inputs.add(input);
    }
    
    //zwrócenie listy synaps
    public List<Synapse> getInputs() {
        return this.inputs;
    }
    
    //zwrócenie wag synaps
    public double[] getWeights() {
        double[] weights = new double[inputs.size()];

        int i = 0;
        for(Synapse synapse : inputs) {
            weights[i] = synapse.getWeight();
            i++;
        }

        return weights;
    }
    
    //przeliczanie sumy wag
    private void calculateWeightedSum() {
        weightedSum = 0;
        for(Synapse synapse : inputs) {
            weightedSum += synapse.getWeight() * synapse.getSourceNeuron().getOutput();
        }
    }
    
    public void activate() {
        calculateWeightedSum();
        output = activationStrategy.activate(weightedSum);
        derivative = activationStrategy.derivative(output);
    }

    public double getOutput() {
        return this.output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double getDerivative() {
        return this.derivative;
    }

    public ActivationStrategy getActivationStrategy() {
        return activationStrategy;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}