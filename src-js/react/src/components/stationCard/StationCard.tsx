import { useEffect, useState } from "react";
import styles from "./StationCard.module.css";
import { useTranslation } from "react-i18next";
import { CardLink, HomeCard } from "../globalStyles";
import { useAppSelector } from "../../hooks/reduxHooks";
import Timer from "../timer/Timer";

export const StationCard = ({
  stationNumber,
  leftS,
  state,
}: {
  stationNumber: any;
  leftS: any;
  state: any;
}) => {
  const [routeTo, setRouteTo] = useState<any>("");
  const [over, setOver] = useState(false);
  const [timer, setTimer] = useState<any>(null);
  // const timerNums = timer?.match(/\d+/g);
  // console.log(timer);

  const { t } = useTranslation();

  const { isGotDeviceStatus } = useAppSelector((state) => state.fetchReducer);

  useEffect(() => {
    setTimer(new Date(leftS * 1000).toISOString().slice(11, 19).match(/\d+/g));
  }, []);
  console.log(timer);

  useEffect(() => {
    if (state === "IN_PROGRESS") {
      setRouteTo(`/charging?station=${stationNumber}`);
    } else if (state === "DONE") {
      setRouteTo(`/start?station=${stationNumber}`);
    }
  }, [routeTo, isGotDeviceStatus]);

  return (
    <CardLink className={styles.linkToStation} to={routeTo}>
      <HomeCard className={styles.container}>
        <div className={styles.mainInfo}>
          <p className={styles.nameStation}>{t("station")}</p>
          <p className={styles.numberStation}>{stationNumber}</p>
        </div>

        <div className={styles.status}>
          {over === false && timer !== null ? (
            // <p className={styles.readyCharge}>{timer}</p>
            <Timer
              hours={Number(timer[0])}
              minutes={Number(timer[1])}
              seconds={Number(timer[2])}
              fontSize={"30px"}
              margin={"20px 0 0 0"}
              over={over}
              setOver={setOver}
            />
          ) : (
            <p className={styles.readyCharge}>{t("readyForUse")}</p>
          )}

          <div
            className={over === false ? styles.online : styles.offline}
          ></div>
        </div>
      </HomeCard>
    </CardLink>
  );
};
