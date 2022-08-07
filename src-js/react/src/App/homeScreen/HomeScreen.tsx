import axios from "axios";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import ErrorPage from "../../components/error-page/ErrorPage";
import StationCard from "../../components/stationCard/StationCard";
import styles from "./HomeScreen.module.css";

const urlV2StatusAll = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/statusAll`;

export default function HomeScreen() {
  const [statusALl, setStatusAll] = useState<any>();
  const { t } = useTranslation();
  useEffect(() => {
    try {
      axios.get(urlV2StatusAll).then(function (result: any) {
        setStatusAll(result.data);
        console.log(result.data);
      });
    } catch (e: any) {
      console.log(e.message);
    }
  }, []);
  return (
    <div className={styles.container}>
      <div className={styles.contentBox}>
        {statusALl?.map((data: any) => {
          if (data?.state) {
            return (
              <div key={Math.random()}>
                <StationCard
                  state={data?.state}
                  leftS={data?.leftS}
                  stationNumber={data?.stationNumber}
                  stationName={"Comfort town"}
                />
              </div>
            );
          } else {
            <ErrorPage
              errorHeader={t("errorDevHeader")}
              errorBody={t("errorDevBody")}
            />;
          }
        })}
      </div>
    </div>
  );
}
