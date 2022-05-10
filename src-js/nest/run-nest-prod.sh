cd "$(dirname "$0")" || exit

npm i -f
npm run build
npm run start:prod