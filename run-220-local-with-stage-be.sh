cd "$(dirname "$0")" || exit

pkill -f react
lsof -ti tcp:3000 | xargs kill -kill

#FE
./src-js/react/run-220-local-with-stage-be.sh
