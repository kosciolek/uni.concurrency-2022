for i in {1..100}; do 
    echo "Running the race for the $i-th time."
    java Race >> counter-states.txt
done