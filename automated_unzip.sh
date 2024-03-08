#!/bin/bash

unzip -o submissions.zip
rm -f submissions.zip
for f in ./*; do
    mv "$f" "$(echo -e "${f}" | tr -d '[:space:]')"
done
for i in `ls -1 | grep .zip$`;                      # Loop over students' submission and unzip them
    do 
    str=$(echo ${i%*_*_*_*});
    if [[ "$str" == *"_"* ]]; then
    str=$(echo ${str%*_*});
    fi
    candNo=$(echo ${str#"candidateno"});
    unzip -o $i -d "${candNo}"; 
done
for i in `ls -1 | grep .rar$`;                      # Loop over students' submission and unrar them 
    do 
    str=$(echo ${i%*_*_*_*});
    if [[ "$str" == *"_"* ]]; then
    str=$(echo ${str%*_*});
    fi
    candNo=$(echo ${str#"candidateno"});
    unrar x -o+ $i "${candNo}/";  
done
