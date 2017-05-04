#centos 
localhome=/usr/local
swhome=/home/admin/bigdata
cd $swhome


#scala
cd $swhome
tar -zxvf scala-2.11.11.tgz -C $localhome
cd $localhome 
mv scala-2.11.11 scala

#echo 'export SCALA_HOME=/usr/local/scala' >> /etc/profile
#echo 'export PATH=$SCALA_HOME/bin:$PATH' >> /etc/profile

source /etc/profile
scala -version

