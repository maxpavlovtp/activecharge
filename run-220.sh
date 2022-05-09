cd "$(dirname "$0")" || exit

nohup ./src-js/react/run-react-prod.sh &
nohup ./src-js/nest/run-nest-prod.sh &