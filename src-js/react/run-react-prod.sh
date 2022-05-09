cd "$(dirname "$0")" || exit

npm i -f
npm install -g serve

pkill -f serve
npm run build
export PORT=80
serve -s build