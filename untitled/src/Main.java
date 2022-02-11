public class Main {
    public static void main(String[] args) {
        int[][] getallenMatrix = {{5,17,8}, {1,4,3}, {8,7,4}};
        for (int i = 0; i < getallenMatrix.length; i++){
            for (int j = 0; j < getallenMatrix.length; j++){
                if (i*j > 1){
                    System.out.println();
                }
                if (j < (i % (getallenMatrix.length/2))+1){
                    System.out.print("*");
                }
                System.out.print(getallenMatrix[j][i]);
            }
            System.out.print("_");
        }
        System.out.println();
    }
}
