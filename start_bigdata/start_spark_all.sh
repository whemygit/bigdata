#!/bin/bash
# 启动Hadoop以及yarn
start-dfs.sh
start-yarn.sh
# 启动历史服务器
mr-jobhistory-daemon.sh start historyserver
# 启动Spark
/usr/local/spark/sbin/start-all.sh
