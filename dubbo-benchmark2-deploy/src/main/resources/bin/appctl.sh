#!/bin/sh

# -----------------------------------------------------------------------------
# Control Script for the CATALINA Server
#
# Environment Variable Prerequisites
#
#   Do not set the variables in this script. Instead put them into a script
#   setenv.sh in bin to keep your customizations separate.
#
#   directory structure
#   --- $app.tar.gz(root)
#   --- $app(root)
#       --- bin
#           --- start.sh
#           --- stop.sh
#           --- preload.sh
#           --- online.sh
#           --- offline.sh
#           --- setenv.sh
#       --- logs
#           --- $app.pid
#       --- $app-xxx.jar
#

APP_NAME=dubbo-benchmark2
APP_PORT=7001


ROOT_PATH=`dirname "$0"`
cd $ROOT_PATH
BIN_PATH=`pwd`
cd ${BIN_PATH}/..
APP_BASE=`pwd`
APP_TMP_DIR=$APP_BASE/tmp
PID_FILE=$APP_TMP_DIR/$APP_NAME.pid
APP_LOGS_DIR=/data/logs/$APP_NAME
#0:exist,1:notexist
JAR_FILE_EXIST_STATUS=0
JAR_FILE=`find $APP_BASE -maxdepth 1 -name "*jar" 2>>/dev/null`
JAR_NUM=`find $APP_BASE -maxdepth 1 -name "*jar" 2>>/dev/null|wc -l`
if [ $JAR_NUM -eq 0 ];then
	JAR_FILE_EXIST_STATUS=1
fi

mkdir -p $APP_TMP_DIR

if [ -r "$APP_BASE/bin/setenv.sh" ]; then
    . "$APP_BASE/bin/setenv.sh"
fi


grepAppPID(){
	if [ $JAR_FILE_EXIST_STATUS -eq 0 ];then
		ps -ef | grep "$JAR_FILE" | grep -v grep | awk '{print $2}'
	fi
}

checkApp(){
    result=`grepAppPID`
    echo "checkApp pid result: $result"
    if [ "${result}x" != "x" ];then
	    if [[ $result > 0 ]]
	    then
	        echo "success!"
	    else
        	echo "fail!"
	    fi
    else
	echo "Can't find APP's PID"
    fi
}



start(){
    PID=`grepAppPID`
    if [ ${PID}x != "x" ];then
        echo "$APP_NAME has already started, it's pid is $PID"
    else
        nohup java $CATALINA_OPTS -jar $JAR_FILE > /dev/null 2>&1 &
        PID=$!
        echo "$PID" > $PID_FILE
        sleep 5
        checkApp
    fi
}


stop(){
    if [ -e $PID_FILE ] ; then
        PID=`cat $PID_FILE`
        if [ "${PID}x" == "x" ] ; then
            echo "can not find process pid. exists"
            rm $PID_FILE
            exit
        fi

        if [ $PID > 0 ] ; then
            echo "`hostname`: stopping $APP_NAME $PID ... "
            kill  $PID
            i=0
            max_retry=3
            while [ 1 ]
            do
                if [ ! -d /proc/$PID  ];then
                    echo "`hostname`: $APP_NAME $PID Stop Success."
                    rm $PID_FILE
                    exit 0
                else
                    kill $PID
                fi
                sleep 5s
                i=`expr $i + 1`
                if [ $i -ge $max_retry ];then
                    echo "`hostname`: $APP_NAME $PID Stop Failed. Starting force kill"
                    kill -9 $PID
                     rm $PID_FILE
                fi
            done

        else
            echo "pid not found"
        fi
    else
	if [ $JAR_FILE_EXIST_STATUS -eq 0 ];then
        	echo "pid file not exists, use pkill"
	        pkill -f $JAR_FILE
        	if [ $? -eq 0 ]; then
	            echo "stop success"
        	fi
	else
		echo "JAR_FILE and  PID all not found."
	fi
    fi
}


