import { useEffect, useState } from "react";
import styles from "./ChargingSection.module.css";
import Timer from "../../../components/timer/Timer";
import axios from "axios";
import ErrorPage from "../../../components/error-page/ErrorPage";
import { useTranslation } from "react-i18next";
import Spinner from "../../../components/spinner/Spinner";

const MainSection: React.FC = () => {
  // const [msg, setMsg] = useState<any>();
  const [loading, setLoading] = useState<any>(false);
  const [error, setError] = useState<any>(null);

  const [secondsTime, setSecondsTime] = useState<any>();
  // const url = `http://localhost:8080/device/start`;
  const secondsUrl = 'http://localhost:8080/device/getChargingDurationLeftSecs';
  const { t } = useTranslation();

  const start = async () => {
    setLoading(true);
      await axios
        .get(secondsUrl)
        .then((response) => {
          setSecondsTime(response.data.data);
          console.log(response)
        })
        .catch((err) => {
          setError(err);
          console.log(err);
        })
        .finally(() => {
          setLoading(false);
        });
  };

  useEffect(() => {
    start();
  }, []);

  if (error)
    return (
      <ErrorPage errorHeader={t("errorHeader")} errorBody={t("errorBody")} />
    );

  if (loading)
    return (
      <Spinner />
    );

  // todo fetch from BE
  // let seconds = 20;
  return (
    <div className={styles.chargingBox}>
      <div className={styles.contTimer}>
        {secondsTime > 0 && <Timer seconds={secondsTime}/>}
        {/* {msg?.data?.message === "error" && (
          <ErrorPage
            errorHeader={t("errorHeader")}
            errorBody={t("errorBody")}
          />
        )} */}
      </div>
    </div>
  );
};

export default MainSection;
