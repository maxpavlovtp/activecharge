import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import LoadingTime from "./LoadingTime";
import styles from "./StationCard.module.css";
import Timer from "../timer/Timer";
import { useBackTime } from "../../hooks/useBackTime";
import { t } from "i18next";
import { useTranslation } from "react-i18next";
import { CardLink, HomeCard, LinksColor } from "../globalStyles";

export default function ({
  stationNumber,
  leftS,
  state,
}: {
  stationNumber: any;
  leftS: any;
  state: any;
}) {
  const [loading, setLoading] = useState<any>(true);
  const [secondsBackend, setSecondsBackend] = useState<any>();
  const [hoursTime, setHoursTime] = useState<any>();
  const [minuteTime, setMinuteTime] = useState<any>();
  const [secondsTime, setSecondsTime] = useState<any>(0);

  useEffect(() => {
    setSecondsBackend(leftS);
  }, []);

  useBackTime(
    secondsBackend,
    hoursTime,
    setHoursTime,
    setMinuteTime,
    setSecondsTime,
    setLoading
  );

  const { t } = useTranslation();

  return (
    <CardLink
      reloadDocument={true}
      className={styles.linkToStation}
      to={`/start?station=${stationNumber}`}
    >
      <HomeCard className={styles.container}>
        <div className={styles.mainInfo}>
          <p className={styles.nameStation}>{t("station")}</p>
          <p className={styles.numberStation}>{stationNumber}</p>
        </div>
        {process.env.REACT_APP_LINK_SERVE === "http://220-km.com:8080/" ? (
          <></>
        ) : (
          <div className={styles.status}>
            {state === "IN_PROGRESS" ? (
              loading === true ? (
                <LoadingTime />
              ) : (
                <Timer
                  hours={1}
                  minutes={30}
                  seconds={0}
                  margin={"20px 0 0 0"}
                  fontSize={"30px"}
                />
              )
            ) : (
              <p className={styles.readyCharge}>Ready!</p>
            )}

            <div
              className={
                state === "IN_PROGRESS" ? styles.online : styles.offline
              }
            ></div>
          </div>
        )}
      </HomeCard>
    </CardLink>
  );
}
