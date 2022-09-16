import React, { useEffect, useState } from "react";
import styles from "./StationCard.module.css";
import { useTranslation } from "react-i18next";
import { CardLink, HomeCard, LinksColor } from "../globalStyles";
import { useAppSelector } from "../../hooks/reduxHooks";

export default function ({
  stationNumber,
  leftS,
  state,
}: {
  stationNumber: any;
  leftS: any;
  state: any;
}) {
  const [timer, setTimer] = useState<any>(null);

  const { t } = useTranslation();

  const { deviceStatus } = useAppSelector((state) => state.fetchReducer);
  useEffect(() => {
    setTimer(new Date(leftS * 1000).toISOString().slice(11, 19));
  }, []);

  return (
    <CardLink
      className={styles.linkToStation}
      to={
        deviceStatus?.lastJob?.state === "IN_PROGRESS"
          ? `/charging?station=${stationNumber}`
          : `/start?station=${stationNumber}`
      }
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
              <p className={styles.readyCharge}>{timer}</p>
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
