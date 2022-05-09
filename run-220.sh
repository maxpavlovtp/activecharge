pkill -f react
pkill -f nest

nohup ./src-js/react/run-react-indevmode.sh &
nohup ./src-js/nest/run-nest-indevmode.sh &
#go to http://localhost/