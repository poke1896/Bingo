/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Random;

/**
 * Esta clase genera cartones de bingo en forma de matriz 5x5 de enteros.
 * 
 * @author luisf
 * @version 24/05/2022
 */
public class Carton {

    private int[][] carton;
    /**
     * Este método verifica si el entero numero está en el cartón y si está cambia ese número
     * por un 0
     * @param numero Es el número a verificar
     * @return true si el número estaba en el cartón y false si no.
     */
    public boolean verificarNumero(int numero){
        for(int i = 0; i < 5; i ++){
            for(int j = 0; j < 5; j++){
                if(carton[i][j] == numero){
                    carton[i][j] = 0;
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Este método verifica si ya salieron todos los números del cartón
     * @return true si ya salieron todos los números, false si no
     */
    public boolean verificarPremioCartonLleno(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(carton[i][j] != 0){
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Este método verifica si en el cartón ya salieron todos los números para formar una L
     * @return true si forma una L, false si no
     */
    public boolean verificarPremioEnL(){
        
        if(carton[1][0] != 0){
            return false;
        }
        if(carton[2][0] != 0){
            return false;
        }
        if(carton[3][0] != 0){
            return false;
        }
        if(carton[4][0] != 0){
            return false;
        }
        for(int i = 0; i < 5; i++){
            if(carton[0][i] != 0){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Crea cartones
     */
    public Carton() {
        carton = new int[5][5];
        int numero = 0;
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            numero = random.nextInt(15 - 1 + 1) + 1;
            if (!verificarIgual(numero, carton[0])) {
                carton[0][i] = numero;
            } else {
                i--;
            }

        }
        for (int i = 0; i < 5; i++) {
            numero = random.nextInt(30 - 16 + 1) + 16;
            if (!verificarIgual(numero, carton[1])) {
                carton[1][i] = numero;
            } else {
                i--;
            }

        }
        for (int i = 0; i < 5; i++) {
            numero = random.nextInt(45 - 31 + 1) + 31;
            if (!verificarIgual(numero, carton[2])) {
                carton[2][i] = numero;
            } else {
                i--;
            }

        }
        for (int i = 0; i < 5; i++) {
            numero = random.nextInt(60 - 46 + 1) + 46;
            if (!verificarIgual(numero, carton[3])) {
                carton[3][i] = numero;
            } else {
                i--;
            }

        }
        for (int i = 0; i < 5; i++) {
            numero = random.nextInt(75 - 61 + 1) + 61;;
            if (!verificarIgual(numero, carton[4])) {
                carton[4][i] = numero;
            } else {
                i--;
            }

        }
        carton[2][2] = 0;
        
        
        

    }

    public int[][] getCarton() {
        return carton;
    }

    public void setCarton(int[][] carton) {
        this.carton = carton;
    }
    /**
     * Verifica si un número pertenece a un vector
     * @param numero Número por verificar
     * @param vector Vector sobre el cual se va a trabajar
     * @return true si el número pertenece al vector, false si no
     */
    private boolean verificarIgual(int numero, int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            if (numero == vector[i]) {
                return true;
            }

        }
        return false;
    }
}
