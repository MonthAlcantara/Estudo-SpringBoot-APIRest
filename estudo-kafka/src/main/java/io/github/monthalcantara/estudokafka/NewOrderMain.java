package io.github.monthalcantara.estudokafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Criar produtor se resume em 3 passos
 * 1 - Criar um produtor  new KafkaProducer<Tipo da Chave, Tipo do Valor>(propriedades());
 * 2 - Criar a mensagem a ser enviada new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", chave, valor);
 * 3 - Colocar um linester para saber o que está acontecendo
 */
public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        /*
         * Ao criar um produtor eu preciso informar qual conteudo da minha chave e do
         * conteudo dessa chave e passar por parametro do construtor desse produtor as
         * propriedades de configuração desse produtor. Foi usado o método propriedades()
         * para isso
         * */
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(propriedades());
        /*
         * Nesse exemplo em específico para facilitar foi usado chave e valor com o mesmo
         * conteúdo mas poderia ser ser diferente
         * o value abaixo está simulando = "id pedido, id usuario, id compra"
         * */
        String value = "132123,67523,7894589745";

        /*
         * O construtor do new ProducerRecord recebe como paramtro no construtor o tópico a
         * qual a mensagem será enviada, a chave e o valor da chave.
         * Nesse exemplo está sendo usado o mesmo conteudo para chave e valor
         * */
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", value, value);

        /*
         * Envia o record. Se entrarmos no método send podemos verque ele retorna um Future, ou seja
         * algo que ainda vai acontecer. então eu preciso dizer que eu quero esperar esse algo acontecer.
         * Pra isso eu uso o get(). Iae duas situações podem ocorrer. Quando eu decido esperar podem
         * ocorrer exceptions. Ou por que o send foi enterrompido ou pq ocorreu alguma falha mesmo durante
         * o processamento..então na assinatura do método já informo isso. Outra coisa também é
         * que o get em si não me retorna nada, apenas avisa que eu quero esperaro resultado prometido no future.
         *  Então para pegar esse valor eu preciso usar uma função callback onde eu recebo os dados de sucesso
         * ou a exception que foi lançada
         * */
        producer.send(record, (data, ex) -> {
            //se rolou alguma exception
            if (ex != null) {
                //imprima a stacktrace e retorne
                ex.printStackTrace();
               return;
            }
            //quando executar me avisa
            System.out.println("Sucesso enviando " + data.topic() + ":::partition" + data.partition() + "/ offset" + data.offset() + "/ timestamp" + data.timestamp());
        }).get();
    }

    /*
     * Configurando as propriedades de configuração do meu servidor
     * Onde ele está
     * Quem vai desserializar a chave e o value
     * Quando tempo a mensagem vai ficar armazenada (Default é 7 dias) e etc
     * */
    private static Properties propriedades() {
        Properties properties = new Properties();
        /*
         * Onde vou me conectar? Onde o Kafka esstá rodando?
         * */
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        /*
         * Quem vai converter a mensagem produzida para Byte, o formato que o kafka entende?
         * Como eu disse  no KafkaProducer<String, String> producer que a mensagem produzida
         * seria uma String, para fazer isso eu preciso usar um cara que saiba fazer isso com
         * Strings, nesse caso StringSerializer.class
         * */
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        /*
         * Quem vai converter a chavea para Byte, o formato que o kafka entende?
         * Como eu disse  no KafkaProducer<String, String> producer que a chave
         * seria uma String, para fazer isso eu preciso usar um cara que saiba fazer isso com
         * Strings, nesse caso StringSerializer.class
         * */
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
