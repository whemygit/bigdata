from kafka import KafkaClient,SimpleProducer,SimpleConsumer,KafkaConsumer
import kafka

client=kafka.SimpleClient("localhost:9092")
# kafka=KafkaClient("117.78.31.192:19092")
producer=SimpleProducer(client)
response=producer.send_messages("testt","hello word")
print response[0].offset


consumer=SimpleConsumer(client,"python","testt")
for msg in consumer:
    print msg

# kafka.close()
# exit()