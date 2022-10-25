cd "$(dirname "$0")" || exit

npm install --loglevel=error
npm run startLocalWithStageBE
