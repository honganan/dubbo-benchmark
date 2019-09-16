#!/bin/bash

SERVER_MANAGE_PORT=7001

i=0
max_retry=110
while [ 1 ]
do
    curl_request=`curl -Ss "http://127.0.0.1:$SERVER_MANAGE_PORT/health" 2>/dev/null`
    if [ "${curl_request}x" != "x" ];then
	check_status_str="status"
	echo "$curl_request"|grep "ref" >>/dev/null 2>&1
	if [ $? == 0 ] ;then
		check_status_str="code"
	fi
        status_up_num=`echo $curl_request  |python -m json.tool|grep "$check_status_str"|grep -v "UP"|wc -l`
        if [ ${status_up_num} -eq 0 ];then
            echo "healthcheck ok"
            exit 0
        fi
    fi
    sleep 5s
    i=`expr $i + 1`
    if [ $i -eq $max_retry ];then
        echo "healthCheck failed,exit"
        exit 1
    fi

done
