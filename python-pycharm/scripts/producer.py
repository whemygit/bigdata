#coding:utf-8
import csv
import time
from kafka import KafkaProducer
# print ["localhost:9092"]


producer = KafkaProducer(bootstrap_servers="localhost:9092")

csvfile=open('../data/user_log.csv','r')
reader=csv.reader(csvfile)

for line in reader:
    gender=line[9]
    if gender=='gender':
        continue
    time.sleep(0.1)

    producer.send('ss',line[9].encode('utf-8'))

# producer = KafkaProducer(bootstrap_servers='117.78.41.235:19092')
# for _ in range(1):
#     producer.send('foobar', b'some_message_bytes')
