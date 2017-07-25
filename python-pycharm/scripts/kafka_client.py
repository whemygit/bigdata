from kafka import KafkaClient,SimpleProducer,SimpleConsumer

kafka=KafkaClient("192.168.0.120:9092")

# kafka=KafkaClient("117.78.31.192:19092")
producer=SimpleProducer(kafka)
response=producer.send_messages("test1","hello word")
print response[0].offset


consumer=SimpleConsumer(kafka,"python","test1")
for msg in consumer:
    print msg