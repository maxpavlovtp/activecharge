cd "$(dirname "$0")" || exit

npm i -f
pkill -f nest
npm run build
npm run start:prod