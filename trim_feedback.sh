#!/bin/bash

for i in `ls -1 | grep .txt$`;                      # Loop over students' feedback and trim them
    do

    # Redundant parts
    head -3 $i > tmp.txt
    str=$(cat $i)
    tmp="${str#*"CATEGORY: RRScheduler"}"
    rem=$(sed -n '/CATEGORY: java/,/CATEGORY: RRScheduler/p' $i)
    rep=$(echo "$rem" | tail -n1)
    echo "$rep" >> tmp.txt
    echo "$tmp" > tmp2.txt
    tail -n +2 tmp2.txt >> tmp.txt && mv tmp.txt $i
    rm tmp2.txt

    # RRScheduler
    c=$(grep -zPo 'CATEGORY: RRScheduler\n\n' $i | grep -c 'CATEGORY: RRScheduler')
    sed -z '0,/CATEGORY: RRScheduler\n\n/s//CATEGORY: RRScheduler   OK \n\n/' $i > tmp.txt && mv tmp.txt $i
    if [[ $c > 1 ]]; then
        sed -z 's/CATEGORY: RRScheduler\n\n//g' $i > tmp.txt && mv tmp.txt $i
    fi

    # IdealSJFScheduler
    c=$(grep -zPo 'CATEGORY: IdealSJFScheduler\n\n' $i | grep -c 'CATEGORY: IdealSJFScheduler')
    sed -z '0,/CATEGORY: IdealSJFScheduler\n\n/s//CATEGORY: IdealSJFScheduler   OK \n\n/' $i > tmp.txt && mv tmp.txt $i
    if [[ $c > 1 ]]; then
        sed -z 's/CATEGORY: IdealSJFScheduler\n\n//g' $i > tmp.txt && mv tmp.txt $i
    fi

    # SJFScheduler
    c=$(grep -zPo 'CATEGORY: SJFScheduler\n\n' $i | grep -c 'CATEGORY: SJFScheduler')
    sed -z '0,/CATEGORY: SJFScheduler\n\n/s//CATEGORY: SJFScheduler   OK \n\n/' $i > tmp.txt && mv tmp.txt $i
    if [[ $c > 1 ]]; then
        sed -z 's/CATEGORY: SJFScheduler\n\n//g' $i > tmp.txt && mv tmp.txt $i
    fi

    # FeedbackRRScheduler
    c=$(grep -zPo 'CATEGORY: FeedbackRRScheduler\n\n' $i | grep -c 'CATEGORY: FeedbackRRScheduler')
    sed -z '0,/CATEGORY: FeedbackRRScheduler\n\n/s//CATEGORY: FeedbackRRScheduler   OK \n\n/' $i > tmp.txt && mv tmp.txt $i
    if [[ $c > 1 ]]; then
        sed -z 's/CATEGORY: FeedbackRRScheduler\n\n//g' $i > tmp.txt && mv tmp.txt $i
    fi

    # TurnaroundTime
    c=$(grep -zPo 'CATEGORY: TurnaroundTime\n\n' $i | grep -c 'CATEGORY: TurnaroundTime')
    sed -z '0,/CATEGORY: TurnaroundTime\n\n/s//CATEGORY: TurnaroundTime   OK \n\n/' $i > tmp.txt && mv tmp.txt $i
    if [[ $c > 1 ]]; then
        sed -z 's/CATEGORY: TurnaroundTime\n\n//g' $i > tmp.txt && mv tmp.txt $i
    fi

    # WaitingTime
    c=$(grep -zPo 'CATEGORY: WaitingTime\n\n' $i | grep -c 'CATEGORY: WaitingTime')
    sed -z '0,/CATEGORY: WaitingTime\n\n/s//CATEGORY: WaitingTime   OK \n\n/' $i > tmp.txt && mv tmp.txt $i
    if [[ $c > 1 ]]; then
        sed -z 's/CATEGORY: WaitingTime\n\n//g' $i > tmp.txt && mv tmp.txt $i
    fi

    # ResponseTime
    c=$(grep -zPo 'CATEGORY: ResponseTime\n\n' $i | grep -c 'CATEGORY: ResponseTime')
    sed -z '0,/CATEGORY: ResponseTime\n\n/s//CATEGORY: ResponseTime   OK \n\n/' $i > tmp.txt && mv tmp.txt $i
    if [[ $c > 1 ]]; then
        sed -z 's/CATEGORY: ResponseTime\n\n//g' $i > tmp.txt && mv tmp.txt $i
    fi

    head -n -2 $i > tmp.txt && mv tmp.txt $i

done
