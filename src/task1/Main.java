package task1;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {


        System.out.printf("Main Thread: begin\n");




        long startTimeInput = System.currentTimeMillis();
        CompletableFuture<Void> DeclareHeader = CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();
            System.out.println("Generating 2D Array...");
            System.out.println("DeclareHeader: "+ (System.currentTimeMillis()-startTime)+" ms");
        });



        CompletableFuture<int[][]> GenerateArray = CompletableFuture.supplyAsync(() -> {
            long startTime = System.currentTimeMillis();
            int[][] integerArray = new int[3][3];
            System.out.println("GenerateArray: "+ (System.currentTimeMillis()-startTime)+" ms");
            return integerArray;
        });

        CompletableFuture<int[][]> CreateMeasures =GenerateArray.thenApplyAsync(integerArray -> {
            long startTime = System.currentTimeMillis();
            defineMeasures();
            System.out.println("CreateMeasures: "+ (System.currentTimeMillis()-startTime)+" ms");
            return integerArray;
        });



        CompletableFuture<int[]> Column1 = CreateMeasures.thenApplyAsync(integerArray -> {
            long startTime = System.currentTimeMillis();
            int[] array = fillColumn();
            System.out.println("Column1: "+ (System.currentTimeMillis()-startTime)+" ms");
            return array;
        });

        CompletableFuture<int[]> Column2 = CreateMeasures.thenApplyAsync(integerArray -> {
            long startTime = System.currentTimeMillis();
            int[] array = fillColumn();
            System.out.println("Column2: "+ (System.currentTimeMillis()-startTime)+" ms");
            return array;
        });

        CompletableFuture<int[]> Column3 = CreateMeasures.thenApplyAsync(integerArray -> {
            long startTime = System.currentTimeMillis();
            int[] array = fillColumn();
            System.out.println("Column3: "+ (System.currentTimeMillis()-startTime)+" ms");
            return array;
        });

        CompletableFuture<int[][]> UniteArray = CompletableFuture.allOf(Column1, Column2, Column3).thenApplyAsync(result -> {
            long startTime = System.currentTimeMillis();
            try{

                int[][] integerArray = GenerateArray.get();

                integerArray[0] = Column1.get();
                integerArray[1] = Column2.get();
                integerArray[2] = Column3.get();

                for(int i = 0; i<3; i++){
                    for(int j = 0; j<3; j++){
                        System.out.printf("%d ", integerArray[i][j]);
                    }
                    System.out.printf("\n");
                }
                System.out.println("UniteArray: "+ (System.currentTimeMillis()-startTime)+" ms");
                return integerArray;

            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }




        });

        UniteArray.join();

        long finishTimeGenerate = System.currentTimeMillis()-startTimeInput;
        long startTimeOutput = System.currentTimeMillis();

        CompletableFuture<int[]> SaveColumn1 = UniteArray.thenApplyAsync(integerArray -> {
            long startTime = System.currentTimeMillis();
            int[] array = saveColumn(integerArray, 0);
            System.out.println("SaveColumn1: "+ (System.currentTimeMillis()-startTime)+" ms");
            return array;
        });
        CompletableFuture<int[]>  SaveColumn2 = UniteArray.thenApplyAsync(integerArray -> {

            long startTime = System.currentTimeMillis();
            int[] array = saveColumn(integerArray, 1);
            System.out.println("SaveColumn2: "+ (System.currentTimeMillis()-startTime)+" ms");
            return array;
        });
        CompletableFuture<int[]>  SaveColumn3 = UniteArray.thenApplyAsync(integerArray -> {

            long startTime = System.currentTimeMillis();
            int[] array = saveColumn(integerArray, 2);
            System.out.println("SaveColumn3: "+ (System.currentTimeMillis()-startTime)+" ms");
            return array;
        });



        CompletableFuture<Void>  ToStringColumn1 = SaveColumn1.thenAcceptAsync(column -> {
            long startTime = System.currentTimeMillis();
            System.out.printf("Перший стовпчик "+ column[0]+" "+column[1]+" "+column[2]+" \n");
            System.out.println("ToStringColumn1: "+ (System.currentTimeMillis()-startTime)+" ms");

        });
        CompletableFuture<Void>  ToStringColumn2 = SaveColumn2.thenAcceptAsync(column -> {
            long startTime = System.currentTimeMillis();
            System.out.printf("Другий стовпчик "+ column[0]+" "+column[1]+" "+column[2]+" \n");
            System.out.println("ToStringColumn2: "+ (System.currentTimeMillis()-startTime)+" ms");
        });

        CompletableFuture<Void>  ToStringColumn3 = SaveColumn3.thenAcceptAsync(column -> {
            long startTime = System.currentTimeMillis();
            System.out.printf("Третій стовпчик "+ column[0]+" "+column[1]+" "+column[2]+" \n");
            System.out.println("ToStringColumn3: "+ (System.currentTimeMillis()-startTime)+" ms");
        });



        ToStringColumn1.join();
        ToStringColumn2.join();
        ToStringColumn3.join();








        CompletableFuture<Void>  FinishTask2 = SaveColumn3.thenRunAsync(() -> {
            long startTime = System.currentTimeMillis();
            String art = """
⠄⠄⠄⠄⠄⠄⠄⢀⣀⣠⣤⠴⠶⠶⠶⠶⠶⠶⠶⢤⣄⣀⡀⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⣠⣶⠟⠋⠁⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠉⠙⠶⣄⡀⠄⠄⠄⠄
⠄⠄⠄⣠⡾⠟⠁⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠈⠻⣆⠄⠄⠄
⠄⠄⣼⡟⠄⠄⠄⠄⠄⠄⠄⢀⣤⣶⡶⢦⡀⠄⠄⠄⠄⠄⠄⠖⠻⣶⠞⢧⠄⠄
⠄⣼⠏⠄⠄⠄⠄⠄⠄⠄⠐⠛⠋⠁⠄⠄⠄⠄⠄⠄⠄⢀⣤⣤⣄⠄⠄⠨⣧⠄
⢸⡏⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠙⡏⠄⠄⠄⠸⡇
⣿⠁⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⡄⠄⠰⠄⠄⠄⠄⠄⢀⡇⠄⢀⡘⢣⣿
⡿⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠠⣄⠄⠄⠦⠄⢀⣠⣤⣶⣿⠿⣶⣦⣴⠟⢹
⢿⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠉⠛⠛⠛⠛⠉⠁⠄⠄⠄⠄⠜⠁⠄⣾
⠈⢧⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢰⠇
⠄⠈⠑⢄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢀⡴⠋⠄
⠄⠄⠄⠄⠐⠄⡀⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢀⣠⡴⠏⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠐⠂⠤⠤⣄⣀⣀⣀⣠⣤⣤⣤⡶⠶⠟⠋⠁⠄⠄⠄⠄⠄
                    """;
            System.out.printf(art);

            System.out.println("FinishTask2: "+ (System.currentTimeMillis()-startTime)+" ms");
        });

        System.out.println("Main Thread: end");

        long finishTimeOutput= System.currentTimeMillis()-startTimeOutput;
        long resultTime = System.currentTimeMillis()-startTimeInput;



        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

        System.out.printf("\n-----------------------------------------------------------------------------------------\n");
        System.out.printf("Total generation time: %s ms\n",finishTimeGenerate);
        System.out.printf("Total output time: %s ms\n",finishTimeOutput);
        System.out.printf("Total session time: %s ms\n",resultTime);


    }




    public static void defineMeasures(){
        int lowestMeasure = 0;
        int highestMeasure = 0;
        while (true){
            Scanner gloriousNImport = new Scanner(System.in);
            System.out.println("Enter lowest measure in range [1, 1000]:");
            try{
                lowestMeasure = gloriousNImport.nextInt();
                if (lowestMeasure>1000 || lowestMeasure<1){
                    throw new InputMismatchException("Input error!");
                }

            }catch (InputMismatchException e){
                System.out.println("Proposed measure is out of range!");
                System.out.println("Try again!");

            }
            System.out.println("Enter highest measure in range [1, 1000]:");
            try{
                highestMeasure = gloriousNImport.nextInt();
                if (highestMeasure>1000 || highestMeasure<1 || highestMeasure<lowestMeasure){
                    throw new InputMismatchException("Input error!");
                }

                System.out.println();
                break;

            }catch (InputMismatchException e){
                System.out.println("Proposed measure is out of range!");
                System.out.println("Try again!");

            }

        }
        Measure.low = lowestMeasure;
        Measure.high = highestMeasure;


    }
    public static int[] fillColumn(){
        int[] col = new int[3];

        for (int i=0; i<3; i++){
            Random kaiserRandom = new Random();
            col[i] = kaiserRandom.nextInt(Measure.high+1- Measure.low)+ Measure.low;
        }
        return col;
    }

    public static int[] saveColumn(int[][] array, int column){
        int[] col = new int[3];

        for (int i=0; i<3; i++){
            col[i] = array[i][column];
        }

        return col;
    }


}


