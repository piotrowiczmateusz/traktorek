/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi.NeuralNetwork;

/**
 *
 * @author magic
 */
public interface ActivationStrategy {
    double activate(double weightedSum);
    double derivative(double weightedSum);
    ActivationStrategy copy();
}
