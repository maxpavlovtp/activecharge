import { useEffect, useState } from "react";
import styles from "./ChargingSection.module.css";
import Timer from "../../../components/timer/Timer";
import axios from "axios";
import ErrorPage from "../../../components/error-page/ErrorPage";
import { useTranslation } from "react-i18next";
import Spinner from "../../../components/spinner/Spinner";
import { useAppDispatch, useAppSelector } from "../../../hooks/reduxHooks";
import { getDeviceIsOnStatus } from "../../../store/reducers/ActionCreators";

const MainSection: React.FC = () => {
  const [loading, setLoading] = useState<any>(true);
  const [secondsBackend, setSecondsBackend] = useState<any>();
  const [hoursTime, setHoursTime] = useState<any>();
  const [minuteTime, setMinuteTime] = useState<any>();
  const [secondsTime, setSecondsTime] = useState<any>();

  const secondsUrl = `${process.env.REACT_APP_LINK_SERVE}device/getChargingDurationLeftSecs`;
  const urlIsDeviceOn = `${process.env.REACT_APP_LINK_SERVE}device/isDeviceOn`;

  const dispatch = useAppDispatch();

  const { t } = useTranslation();

  const { isLoadingCharging, error } = useAppSelector(
    (state) => state.fetchReducer
  );

  const start = () => {
    setLoading(true);
    dispatch(getDeviceIsOnStatus());
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
    if (secondsBackend >= 3610) {
      setHoursTime(Math.floor(secondsBackend / 60 / 60));
      if (hoursTime) {
        setMinuteTime(Math.floor(secondsBackend / 60) - hoursTime * 60);
        setSecondsTime(secondsBackend % 60);
      }
    }
    if (secondsBackend < 3610) {
      setMinuteTime(Math.floor(secondsBackend / 60));
      setSecondsTime(secondsBackend % 60);
    }
    if (secondsBackend < 60) {
      setSecondsTime(secondsBackend);
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
            {secondsTime >= 0 && (
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
