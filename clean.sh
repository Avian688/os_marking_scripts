#!/bin/bash

for i in `ls -1 | grep .zip$`;
    do 
    str=$(echo ${i%*_*_*_*});
    if [[ "$str" == *"_"* ]]; then
    str=$(echo ${str%*_*});
    fi
    candNo=$(echo ${str#"candidateno"});
    rm -f -r "${candNo}"; 
done
for i in `ls -1 | grep .rar$`;
    do 
    str=$(echo ${i%*_*_*_*});
    if [[ "$str" == *"_"* ]]; then
    str=$(echo ${str%*_*});
    fi
    candNo=$(echo ${str#"candidateno"});
    rm -f -r "${candNo}"; 
done

