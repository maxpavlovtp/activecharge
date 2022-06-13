import { useEffect, useState } from "react";
import styles from "./ChargingSection.module.css";
import Timer from "../../../components/timer/Timer";
import axios from "axios";
import ErrorPage from "../../../components/error-page/ErrorPage";
import { useTranslation } from "react-i18next";
import Spinner from "../../../components/spinner/Spinner";
import { useAppSelector } from "../../../hooks/reduxHooks";

const MainSection: React.FC = () => {
  const [loading, setLoading] = useState<any>(true);
  const [secondsBackend, setSecondsBackend] = useState<any>();
  const [hoursTime, setHoursTime] = useState<any>();
  const [minuteTime, setMinuteTime] = useState<any>();
  const [secondsTime, setSecondsTime] = useState<any>();

  const secondsUrl = `${process.env.REACT_APP_LINK_SERVE}device/getChargingDurationLeftSecs`;

  const { t } = useTranslation();

  const { isLoadingCharging, error } = useAppSelector(
    (state) => state.fetchReducer
  );

  const start = () => {
    setLoading(true);
    setTimeout(() => {
      axios
        .get(secondsUrl)
        .then((response) => {
          setSecondsBackend(response.data.data);
          console.log(response);
        })
        .catch((err) => {
          console.log(err);
        })
        .finally(() => {
          setLoading(false);
        });
    }, 3000);
  };
  useEffect(() => {
    console.log(isLoadingCharging);
    if (isLoadingCharging === false) {
      start();
    }
  }, [isLoadingCharging]);

  useEffect(() => {
    if (secondsBackend) {
      setHoursTime(Math.floor(secondsBackend / 60 / 60));
    }
    if(secondsBackend && hoursTime){
      setMinuteTime(Math.floor(secondsBackend / 60) - hoursTime * 60);
      setSecondsTime(secondsBackend % 60);
    }
  });

  if (error)
    return (
      <ErrorPage errorHeader={t("errorHeader")} errorBody={t("errorBody")} />
    );

  if (loading === true) return <Spinner />;

  // todo fetch from BE
  // let seconds = 20;
  return (
    <>
      {loading === false && (
        <div className={styles.chargingBox}>
          <div className={styles.contTimer}>
            {hoursTime && minuteTime && secondsTime && (
              <Timer
                hours={hoursTime}
                minutes={minuteTime}
                seconds={secondsTime}
              />
            )}
          </div>
        </div>
      )}
    </>
  );
};

export default MainSection;
