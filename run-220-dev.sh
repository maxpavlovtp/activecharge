cd "$(dirname "$0")" || exit

pkill -f react
pkill -f nest

nohup ./src-js/react/run-react-dev.sh &
nohup ./src-js/nest/run-nest-dev.sh &