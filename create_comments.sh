#!/bin/bash

# IMPORTANT NOTE: There shouldn't be any new lines (i.e. \n) in the text of comments for this script to work

INPUT_FILE=marks.csv;               # Input file which contains students marks
OLDIFS=$IFS;
IFS='$';
flag=1;
declare -a headers=('init');
no_col=32;                           # Number of columns in the input file, i.e. number of questions.
input="";
i=0;
cand_no=0;

if [ -f $INPUT_FILE ]; then
    while read input
    do
        for col in $input 
        do
            if [[ $flag == 1 ]]; then
                headers[$i]=$col;
                if [[ $i == $(($no_col-1)) ]]; then
                    flag=0;
                fi
            else
                if [[ $i == 0 ]]; then
                    cand_no=$col;
                    echo "Operating Systems Coursework 1 Feedback for: $cand_no" > "final_marks/${cand_no}-final.txt";
                    echo "========================================" >> "final_marks/${cand_no}-final.txt";
                elif [[ $i == 14 || $i == 23 ]]; then
                    echo "----------------------------------------" >> "final_marks/${cand_no}-final.txt";
                elif [[ $i == 6 || $i == 8 || $i == 10 || $i == 12 || $i == 15 || $i == 17 || $i == 19 || $i == 21 || $i == 24 || $i == 26 || $i == 28 || $i == 30 ]]; then
                    echo " " >> "final_marks/${cand_no}-final.txt";
                else
                    echo "${headers[$i]}: $col" >> "final_marks/${cand_no}-final.txt";
                fi
                if [[ $i == 1 ]]; then
                    echo "...................." >> "final_marks/${cand_no}-final.txt";
                fi
                if [[ $i == 4 ]]; then
                    echo "========================================" >> "final_marks/${cand_no}-final.txt";
                    echo "Report:" >> "final_marks/${cand_no}-final.txt";
                    echo " " >> "final_marks/${cand_no}-final.txt";
                fi
            fi
            i=$(((i+1)%$no_col));
            if [[ $i == 0 && $cand_no != 0 ]]; then
                echo "========================================" >> "final_marks/${cand_no}-final.txt";
                echo "Implementation:" >> "final_marks/${cand_no}-final.txt";
                echo " " >> "final_marks/${cand_no}-final.txt";
                echo $(cat marking/results/${cand_no}.txt) >> "final_marks/${cand_no}-final.txt";
            fi
        done
    done < $INPUT_FILE
fi
IFS=$OLDIFS;