localhome=/usr/local
swhome=/home/hadoop/sw

cd $swhome
tar -zxvf spark-1.6.2-bin-hadoop2.6.tgz -C /usr/local
cd $localhome
mv spark-1.6.2-bin-hadoop2.6 spark


echo '''export SPARK_HOME=/usr/local/spark
export PATH=$SPARK_HOME/bin:$SPARK_HOME/sbin:$PATH''' >> /etc/profile

chown -R root:root ./spark

cd /usr/local/spark
cp ./conf/spark-env.sh.template ./conf/spark-env.sh

echo '''export SPARK_DIST_CLASSPATH=$(/usr/local/hadoop/bin/hadoop classpath)
export JAVA_HOME=/usr/lib/jvm/jdk''' >> ./conf/spark-env.sh

source /etc/profile
