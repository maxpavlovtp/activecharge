cd "$(dirname "$0")" || exit

npm i
node ./show-all-devices.js > show-all-devices.txt
grep deviceid show-all-devices.txt
