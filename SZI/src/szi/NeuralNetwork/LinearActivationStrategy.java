/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi.NeuralNetwork;

import java.io.Serializable;

/**
 *
 * @author magic
 */
public class LinearActivationStrategy implements ActivationStrategy, Serializable {

    public double activate(double weightedSum) {
        return weightedSum;
    }


    public double derivative(double weightedSum) {
        return 1;
    }


    public ActivationStrategy copy() {
        return new LinearActivationStrategy();
    }
}
