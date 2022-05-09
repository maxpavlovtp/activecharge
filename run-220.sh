pkill -f react
pkill -f nest
pkill -f serve

nohup ./src-js/react/run-react-prod.sh &
nohup ./src-js/nest/run-nest-prod.sh &