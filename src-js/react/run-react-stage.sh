cd "$(dirname "$0")" || exit

npm i
npm install -g serve


npm run stage
export PORT=80
serve -s build