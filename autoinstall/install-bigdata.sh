#http://blog.csdn.net/wonder4/article/details/52245636
#jdk
localhome=/usr/local
swhome=/home/hadoop/sw
cd $swhome
sudo mkdir /usr/lib/jvm
tar -zxvf jdk-8u91-linux-x64.tar.gz -C /usr/lib/jvm
cd /usr/lib/jvm
mv jdk1.8.0_91 jdk
echo 'export JAVA_HOME=/usr/lib/jvm/jdk' >> /etc/profile
echo 'export CLASSPATH=.:$JAVA_HOME/lib:$JAVA_HOME/jre/lib:$CLASSPATH' >> /etc/profile
echo 'export PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH' >> /etc/profile
source /etc/profile
java -version


#scala
cd $swhome
tar -zxvf scala-2.11.8.tgz -C $localhome
cd $localhome 
mv scala-2.11.8 scala

echo 'export SCALA_HOME=/usr/local/scala' >> /etc/profile
echo 'export PATH=$SCALA_HOME/bin:$PATH' >> /etc/profile

source /etc/profile
scala -version
