package io.github.monthalcantara.estudokafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Aqui é seguida uma lógica parecida com a do producer só que para um consumer
 */
public class FraudeDetectorService {

    public static void main(String[] args) {
        /*
         * Crio um consumer
         * */
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties());
        /*
         * Aqui eu preciso dizer qual o tópico que meu consumer estará escutando.
         * o subscribe tem varias sobrecargas e uma delas recebe uma collection.
         * Nesse parametro eu poderia passar uma list, informando qual ou quais tópicos
         * estão sendo escutados. É possível escutar mais de um tópico num único consumer
         * mas não é o comum. Nesse caso foi usado o Collections.singletonList() que funciona
         * como o List.of(). E  passei o nome do tópico que estou ouvindo
         * */
        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER"));

        /*
         * Eu preciso ficar repetindo isso constantemente. Eu preciso saber, tem alguma coisa pra mim?
         * se não tem eu volto e fico perguntando, escutando
         * */
        while (true) {
            /*
             * Iae meu consumer vai ficar perguntando ao kafka de tempos em tempos se tem alguma
             * coisa la pra ele. E esse tempo entre uma chamada e outra eu seto aqui.
             * E isso me retorna os records que foram enviados pelo producer
             * */
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                System.out.println("Encontrei registro");

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Processing new order, checking for fraud");
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.partition());
                    System.out.println(record.offset());
                    System.out.println("Order Processed");
                }
            }
        }
    }

    /*
     * Declaro as propriedades desse consumer como fiz no producer
     * */
    private static Properties properties() {
        Properties properties = new Properties();
        //de onde irei consumir
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        /*
         * No producer eu precisei transformar de String pra Byte. Só que como aqui eu vou consumir,
         * eu preciso desserializar. Transformar de Byte, que o Kafka entende para String que o
         * Java entende
         * */
        //A Chave vai ser desserializada usando a classe...
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //O Value vai ser desserializada usando a classe...
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        /*
         * Num projeto real eu posso ter muita gente escutando um tópico. O serviço de email, de pagamento,
         * de qqr outra coisa. Então o meu serviço de detecção de fraude deve ser atrelado a um grupo até pra
         * eu poder monitorar depois. Iae aqui eu digo que esse service pertence a um grupo que dei o nome de
         * FraudeDetectorService o nome da propria classe
         * */
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudeDetectorService.class.getSimpleName());

        return properties;
    }
}
