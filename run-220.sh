pkill -f react
pkill -f nest

nohup ./src-js/nest/run-nest-indevmode.sh &
nohup ./src-js/react/run-react-prod.sh &