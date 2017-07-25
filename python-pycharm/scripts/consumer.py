from kafka import KafkaConsumer

consumer=KafkaConsumer('ss')
for msg in consumer:
    print msg.value