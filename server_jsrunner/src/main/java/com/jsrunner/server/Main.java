package com.jsrunner.server;


import com.jsrunner.server.model.ScriptExecutionItem;
import com.jsrunner.server.services.JSExecutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
class MyScriptDto {
    private UUID id;
    private String script;
}


public class Main {

    private static UUID taskId = UUID.randomUUID();
    private static BlockingQueue<MyScriptDto> queueTasks = new ArrayBlockingQueue<>(10);
    private static ExecutorService executorService = Executors.newScheduledThreadPool(4);
    private static Map<UUID, Future<ScriptExecutionItem>> responseMap = new ConcurrentSkipListMap<>();
    //@Autowired
    private static JSExecutor executor = new JSExecutor();

    public static void main1(String[] args) throws ExecutionException, InterruptedException {
        //Creating BlockingQueue of size 10

        Producer producer1 = new Producer(queueTasks, 300);
        Producer producer2 = new Producer(queueTasks, 300);
        //starting producer to produce messages in queueTasks
        new Thread(producer1).start();
        new Thread(producer2).start();
//        new ResultChecker(responseMap).start();

        while (true) {
            MyScriptDto scriptDto = queueTasks.take();
            UUID id = scriptDto.getId();
            String script = scriptDto.getScript();
            System.out.println("LOOP sourceCode:" + script);
            Thread.sleep(2000);
//            Future<ScriptExecutionItem> f = executorService.submit(
//                    () -> executor.execute(script)
//            );
//            System.out.println("RESPONSE errors:" + f.get().getErrors());
//            System.out.println("RESPONSE result:" + f.get().getExecutionResult());
        }
    }

    @Data
    @AllArgsConstructor
    static class ResultChecker extends Thread {
        private Map<UUID, Future<ScriptExecutionItem>> responses;

        @Override
        public void run() {
            while (true) {
                System.out.println("Result checker# TRY TO CHECK");
                if (responses.size() == 0) {
                    System.out.println("Result checker# EMPTY");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        System.out.println("Result checker# CONTAINS:");
                        responses.forEach((k, v) -> {
                            System.out.println("\tKey:" + k);
                            try {
                                System.out.println("\tValue:" + v.get().getSourceCode());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        });
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static MyScriptDto getScript(UUID i) {
        String s = new String("print(\"Hello world :" + i + "\");");
        return new MyScriptDto(i, s);
    }

//    private static void executeScript(MyScriptDto s) throws InterruptedException {
//        Thread.sleep(2000);
//        System.out.println("Executed " + s.getSourceCode() + " . by" + Thread.currentThread().getName());
//    }

    /**
     * Add tasks with strings, that should be done!
     */
    public static class Producer implements Runnable {

        private BlockingQueue<MyScriptDto> queue;
        private int sleepTimeout;

        public Producer(BlockingQueue<MyScriptDto> q, int sleepTimeout) {
            this.queue = q;
            this.sleepTimeout = sleepTimeout;
        }

        @Override
        public void run() {
            //produce messages
            while (true) {
                try {
                    //тайм-аут положить сообщение в очередь обработки
                    Thread.sleep(sleepTimeout);
                    UUID id = UUID.randomUUID();
                    if (queue.offer(getScript(id))) {
                        System.out.println("ADD: " + id + " TASK");
                    } else {
                        System.out.println("SORYAN, Brat:(" + sleepTimeout + "), zanyato!!!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
