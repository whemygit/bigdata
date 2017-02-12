localhome=/usr/local
swhome=/home/hadoop/sw

cd $swhome
tar -zxvf hadoop-2.6.5.tar.gz -C $localhome

cd $localhome
mv hadoop-2.6.5 hadoop

chown -R root:root hadoop

echo 'export HADOOP_HOME=/usr/local/hadoop' >> /etc/profile
echo 'export PATH=$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH' >> /etc/profile

source /etc/profile
hadoop version

cd /usr/local/hadoop
echo '''<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
        <property>
             <name>hadoop.tmp.dir</name>
             <value>file:/usr/local/hadoop/tmp</value>
             <description>Abase for other temporary directories.</description>
        </property>
        <property>
             <name>fs.defaultFS</name>
             <value>hdfs://localhost:9000</value>
        </property>
</configuration>''' > ./etc/hadoop/core-site.xml


echo '''<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
        <property>
             <name>dfs.replication</name>
             <value>1</value>
        </property>
        <property>
             <name>dfs.namenode.name.dir</name>
             <value>file:/usr/local/hadoop/tmp/dfs/name</value>
        </property>
        <property>
             <name>dfs.datanode.data.dir</name>
             <value>file:/usr/local/hadoop/tmp/dfs/data</value>
        </property>
</configuration>''' > ./etc/hadoop/hdfs-site.xml

echo 'export HADOOP_HOME=/usr/local/hadoop' >> /etc/profile
echo 'export PATH=$HADOOP_HOME/bin:$PATH' >> /etc/profile

source /etc/profile

cd /usr/local/hadoop
cp ./etc/hadoop/mapred-site.xml.template ./etc/hadoop/mapred-site.xml


echo '''<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
        <property>
             <name>mapreduce.framework.name</name>
             <value>yarn</value>
        </property>
</configuration>''' > ./etc/hadoop/mapred-site.xml


echo '''<?xml version="1.0" encoding="UTF-8"?>
<configuration>
        <property>
             <name>yarn.nodemanager.aux-services</name>
             <value>mapreduce_shuffle</value>
            </property>
</configuration>''' > ./etc/hadoop/yarn-site.xml

sed -i 's/${JAVA_HOME}/\/usr\/lib\/jvm\/jdk/g' ./etc/hadoop/hadoop-env.sh
