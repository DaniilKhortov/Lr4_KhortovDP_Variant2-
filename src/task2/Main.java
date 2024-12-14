package task2;



import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {

        long startTimeSession= System.currentTimeMillis();

        CompletableFuture<Void> DeclareHeader = CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();
            System.out.println("Generating Array...");
            Measure.addTime("DeclareHeader: "+ (System.currentTimeMillis()-startTime)+" ms");
            System.out.println("DeclareHeader: "+ (System.currentTimeMillis()-startTime)+" ms");
        });



        CompletableFuture<int[]> GenerateArray = CompletableFuture.supplyAsync(() -> {
            long startTime = System.currentTimeMillis();
            int[] integerArray = new int[20];
            Measure.addTime("GenerateArray: "+ (System.currentTimeMillis()-startTime)+" ms");
            System.out.println("GenerateArray: "+ (System.currentTimeMillis()-startTime)+" ms");
            return integerArray;
        });

        CompletableFuture<int[]> CreateMeasures =GenerateArray.thenApplyAsync(integerArray -> {
            long startTime = System.currentTimeMillis();
            defineMeasures();
            Measure.addTime("CreateMeasures: "+ (System.currentTimeMillis()-startTime)+" ms");
            System.out.println("CreateMeasures: "+ (System.currentTimeMillis()-startTime)+" ms");
            return integerArray;
        });

        CompletableFuture<int[]> FillArray= CreateMeasures.thenApplyAsync(integerArray -> {
            long startTime = System.currentTimeMillis();


            for (int i=0; i<20; i++){
                Random kaiserRandom = new Random();
                integerArray[i] = kaiserRandom.nextInt(Measure.high+1- Measure.low)+ Measure.low;
            }
            Measure.addTime("FillArray: "+ (System.currentTimeMillis()-startTime)+" ms");
            System.out.println("FillArray: "+ (System.currentTimeMillis()-startTime)+" ms");
            return integerArray;
        });

        CompletableFuture<Void> DisplayArray= CreateMeasures.thenAcceptAsync(integerArray ->{
            long startTime = System.currentTimeMillis();
            System.out.printf("Starting array: ");
            for(int i=0; i<integerArray.length; i++){
                System.out.printf(integerArray[i]+" ");
            }
            Measure.addTime("DisplayArray: "+ (System.currentTimeMillis()-startTime)+" ms");
            System.out.println("\nDisplayArray: "+ (System.currentTimeMillis()-startTime)+" ms");
        });
        CompletableFuture<Integer> FindMin= CreateMeasures.thenApplyAsync(integerArray -> {
            long startTime = System.currentTimeMillis();
            int min = 1000;


            for (int i=0; i<20; i++){
                if (i%2!=0 && integerArray[i]<min){
                    min = integerArray[i];
                }

            }

            System.out.println("Min: "+ min);
            Measure.addTime("FindMin: "+ (System.currentTimeMillis()-startTime)+" ms");
            System.out.println("FindMin: "+ (System.currentTimeMillis()-startTime)+" ms");
            return min;
        });
        CompletableFuture<Integer> FindMax= CreateMeasures.thenApplyAsync(integerArray -> {
            long startTime = System.currentTimeMillis();
            int max = 0;


            for (int i=0; i<20; i++){
                if (i%2==0 && integerArray[i]>max){
                    max = integerArray[i];
                }

            }
            System.out.println("Max: "+ max);
            Measure.addTime("FindMax: "+ (System.currentTimeMillis()-startTime)+" ms");
            System.out.println("FindMax: "+ (System.currentTimeMillis()-startTime)+" ms");
            return max;
        });

        FindMax.join();
        FindMin.join();

        CompletableFuture<Void> SumUp= CreateMeasures.thenAcceptAsync(integerArray -> {
            long startTime = System.currentTimeMillis();
            try {
                int min = FindMin.get();
                int max = FindMax.get();
                int result = min+max;
                System.out.println("Result: "+ result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            Measure.addTime("SumUp: "+ (System.currentTimeMillis()-startTime)+" ms");
            System.out.println("SumUp: "+ (System.currentTimeMillis()-startTime)+" ms");

        });

        CompletableFuture<Void>  FinishTask2 = SumUp.thenRunAsync(() -> {
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

            Measure.addTime("FinishTask2: "+ (System.currentTimeMillis()-startTime)+" ms");
            System.out.println("FinishTask2: "+ (System.currentTimeMillis()-startTime)+" ms");
        });

        System.out.println("Main Thread: end");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }


        System.out.printf("\n-----------------------------------------------------------------------------------------\n");

        System.out.printf("Total session time: %s ms\n",System.currentTimeMillis()-startTimeSession);
        List<String> timeAsyncLocal = Measure.getTimeAsync();
        for(int i=0; i<timeAsyncLocal.size(); i++){
            System.out.println(timeAsyncLocal.get(i));
            
        }
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
        task1.Measure.low = lowestMeasure;
        Measure.high = highestMeasure;


    }
}
