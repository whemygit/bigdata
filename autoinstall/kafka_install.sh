#kafka_2.11-0.10.1.0.tgz 

localhome=/usr/local
bigdatahome=/home/admin/bigdata

cd $biddatahome
tar -xvzf kafka_2.11-0.10.1.0.tgz -C $localhome
cd $localhome
mv kafka_2.11-0.10.1.0 ./kafka
chown -R root ./kafka

#kafkatest
#another zhongduan
cd /usr/local/kafka
bin/zookeeper-server-start.sh config/zookeeper.properties

#another zhongduan
cd /usr/local/kafka
bin/kafka-server-start.sh config/server.properties




