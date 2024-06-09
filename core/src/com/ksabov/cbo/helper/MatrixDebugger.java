package com.ksabov.cbo.helper;

public class MatrixDebugger {
    public static void printMarkers(int[][] rawMatrix) {
        for (int posX = 0; posX < rawMatrix.length; posX++) {
            for (int posY = 0; posY < rawMatrix[posX].length; posY++) {
                System.out.print(rawMatrix[posX][posY] + " ");
            }
            System.out.println("\n");
        }
    }
}
