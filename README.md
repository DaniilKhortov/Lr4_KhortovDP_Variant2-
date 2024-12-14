# LR4_Async_Khortov

# Завдання
<p><b>Варіант-2</b></p>
1. Напишіть програму, яка асинхронно генеруватиме двовимірний
масив розмірністю 3х3. Потім асинхронно виводитиме кожен
стовпчик цього масиву додаючи перед значеннями повідомлення
(наприклад, перший стовпець: 3, 21, 6). Далі продемонструйте роботу
методу thenRunAsync().
Результати та початковий масив потрібно виводити на екран
асинхронно.
Також після завершення кожної асинхронної задачі потрібно виводити
на екран час її виконання.
2. Напишіть програму, в якій асинхронно виконайте усі необхідні дії.
Ввести послідовність дійсних чисел (a1, a2, a3, ... an) та обчислити
min(a1, a3, a5, ... ) + max(a2, a4, a6, ... ). Початкову послідовність
генерувати рандомно, кількість елементів = 20.
Початкову послідовність та результат вивести на екран.
До кожного виводу додавати відповідне інформаційне повідомлення.
В кінці вивести час роботи усіх асинхронних операцій.



# Опис ініціалізації масиву
<p>На початку, програма просить користувача встановити діапазон генерованих значень.<\n></p>
 
Основні положення вводу:
<ul>
  <li>1.Згідно задачі, мінімальна можлива межа: 1, максимальна можлива межа: 1000.</li>
  <li>2.При введені невідповідних значень, програма попросить заново задати межі діапозону.</li>
  <li>3.Для збереження меж використовуються глобальні змінні</li>
</ul>



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
   
   
   # Завдання 1
   Генерація масиву складається з наступних кроків:

   1. Ініціалізація
      
        ```CompletableFuture<int[][]> GenerateArray = CompletableFuture.supplyAsync(() -> {
            long startTime = System.currentTimeMillis();
            int[][] integerArray = new int[3][3];
            System.out.println("GenerateArray: "+ (System.currentTimeMillis()-startTime)+" ms");
            return integerArray;
        });

   2. Заповнення у 3 потоки
        ```
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


   3. Поєднання стовпців масиву
         ```
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


Після цього стовпці беруться з масиву та виводяться у вигляді тексту

```
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

   

```

   # Завдання 2
   Генерація масиву складається з наступних кроків:   
   
   1. Ініціалізація
   ```
           CompletableFuture<int[]> GenerateArray = CompletableFuture.supplyAsync(() -> {
            long startTime = System.currentTimeMillis();
            int[] integerArray = new int[20];
            System.out.println("GenerateArray: "+ (System.currentTimeMillis()-startTime)+" ms");
            return integerArray;
        });
   ```

   2. Заповнення масиву
   ```
        CompletableFuture<int[]> FillArray= CreateMeasures.thenApplyAsync(integerArray -> {
            long startTime = System.currentTimeMillis();


            for (int i=0; i<20; i++){
                Random kaiserRandom = new Random();
                integerArray[i] = kaiserRandom.nextInt(Measure.high+1- Measure.low)+ Measure.low;
            }

            System.out.println(" FillArray: "+ (System.currentTimeMillis()-startTime)+" ms");
            return integerArray;
        });
```


Після цього відбувається знаходження мінімуму та максимуму

```    
         CompletableFuture<Integer> FindMin= CreateMeasures.thenApplyAsync(integerArray -> {
            long startTime = System.currentTimeMillis();
            int min = 1000;


            for (int i=0; i<20; i++){
                if (i%2!=0 && integerArray[i]<min){
                    min = integerArray[i];
                }

            }

            System.out.println("Min: "+ min);
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
            System.out.println("FindMax: "+ (System.currentTimeMillis()-startTime)+" ms");
            return max;
        });
        FindMax.join();
        FindMin.join();

```

В кінці додаються отримані значення?:
```
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


            System.out.println(" SumUp: "+ (System.currentTimeMillis()-startTime)+" ms");

        });
        
 ```
