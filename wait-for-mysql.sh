#!/bin/bash

# Parameters
HOST=$1
PORT=$2
TIMEOUT=$3
CMD="${@:4}"

# Wait for MySQL to be ready
echo "Waiting for MySQL to be ready on $HOST:$PORT..."
for i in $(seq 1 $TIMEOUT); do
    if nc -z -v -w5 $HOST $PORT &> /dev/null; then
        echo "MySQL is up and running."
        exec $CMD
        break
    else
        echo "Waiting for MySQL... ($i/$TIMEOUT)"
        sleep 1
    fi
done

# Timeout reached, MySQL is still not available
echo "MySQL is not ready after $TIMEOUT seconds, exiting."
exit 1

