#!/bin/bash
# 停止Spark
stop-dfs.sh
stop-yarn.sh
# 停止历史服务器
mr-jobhistory-daemon.sh stop historyserver
# 停止Hadoop以及yarn
/usr/local/hadoop/sbin/stop-all.sh
