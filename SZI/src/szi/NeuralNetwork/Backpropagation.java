/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi.NeuralNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author magic
 */
class Backpropagation {
    private NeuralNetwork neuralNetwork;
    private double learningRate;
    private double momentum;
    private double characteristicTime;
    private double currentEpoch;

    public Backpropagation(NeuralNetwork neuralNetwork, double learningRate, double momentum, double characteristicTime) {
        this.neuralNetwork = neuralNetwork;
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.characteristicTime = characteristicTime;
    }

    public void train(TrainingData generator, double errorThreshold) {

        double error;
        double sum = 0.0;
        double average = 1.0;
        int epoch = 0;
        int samples = 30;
        double[] errors = new double[samples];

        do {
            TrainingData trainingData = generator;
            error = backpropagate(trainingData.getInputs(), trainingData.getOutputs());

            sum -= errors[epoch % samples];
            errors[epoch % samples] = error;
            sum += errors[epoch % samples];

            if(epoch > samples) {
                average = error / samples;
            }

            //System.out.println("Error for epoch " + epoch + ": " + error + " Average: "+ average + (characteristicTime > 0 ? " Learning rate: " + learningRate / (1 + (currentEpoch / characteristicTime)): ""));
            epoch++;
            currentEpoch = epoch;
        } while(average > errorThreshold);
    }

    public double backpropagate(double[][] inputs, double[][] expectedOutputs) {

        double error = 0;

        Map<Synapse, Double> synapseNeuronDeltaMap = new HashMap<Synapse, Double>();
        List<Integer> samples = new ArrayList<Integer>();

        while (samples.size() < inputs.length) {

            Random r = new Random();
            int i = r.nextInt(inputs.length);

            while(samples.contains(i)){
                i=r.nextInt(inputs.length);
            }

            samples.add(i);

            double[] input = inputs[i];
            double[] expectedOutput = expectedOutputs[i];

            List<Layer> layers = neuralNetwork.getLayers();

            neuralNetwork.setInputs(input);
            double[] output = neuralNetwork.getOutput();

            //Ustalenie błędów dla poszczególnych neuronów z warstw ukrytych
            for (int j = layers.size() - 1; j > 0; j--) {
                Layer layer = layers.get(j);

                for (int k = 0; k < layer.getNeurons().size(); k++) {
                    Neuron neuron = layer.getNeurons().get(k);
                    double neuronError = 0;

                    if (layer.isOutputLayer()) {
                        neuronError = neuron.getDerivative() * (output[k] - expectedOutput[k]);
                    } else {
                        neuronError = neuron.getDerivative();

                        double sum = 0;
                        List<Neuron> downstreamNeurons = layer.getNextLayer().getNeurons();
                        for (Neuron downstreamNeuron : downstreamNeurons) {

                            int l = 0;
                            boolean found = false;
                            while (l < downstreamNeuron.getInputs().size() && !found) {
                                Synapse synapse = downstreamNeuron.getInputs().get(l);

                                if (synapse.getSourceNeuron() == neuron) {
                                    sum += (synapse.getWeight() * downstreamNeuron.getError());
                                    found = true;
                                }

                                l++;
                            }
                        }

                        neuronError += sum;
                    }

                    neuron.setError(neuronError);
                }
            }

            //Używająć wyżej obliczonych błędów dla każdego neuronu uaktualniamy wagi sieci
            for(int j = layers.size() - 1; j > 0; j--) {
                Layer layer = layers.get(j);

                for(Neuron neuron : layer.getNeurons()) {

                    for(Synapse synapse : neuron.getInputs()) {

                        double delta = learningRate * neuron.getError() * synapse.getSourceNeuron().getOutput();

                        if(synapseNeuronDeltaMap.get(synapse) != null) {
                            double previousDelta = synapseNeuronDeltaMap.get(synapse);
                            delta += momentum * previousDelta;
                        }

                        synapseNeuronDeltaMap.put(synapse, delta);
                        synapse.setWeight(synapse.getWeight() - delta);
                    }
                }
            }

            output = neuralNetwork.getOutput();
            error += error(output, expectedOutput);
        }

        return error;
    }

    public double error(double[] actual, double[] expected) {

        if (actual.length != expected.length) {
            throw new IllegalArgumentException("The lengths of the actual and expected value arrays must be equal");
        }

        double sum = 0;

        for (int i = 0; i < expected.length; i++) {
            sum += Math.pow(expected[i] - actual[i], 2);
        }

        return sum / 2;
    }
}
