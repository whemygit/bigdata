# bigdata
code for bigdata on linux
# 定时任务
20 13 * * * nohup /usr/bin/bash /home/auto_news.sh > /home/nohup.log 2>&1 &
#20 20 * * * nohup /usr/bin/bash /home/spider/collect/auto.sh > /home/spider/collect/auto.log 2>&1 &
